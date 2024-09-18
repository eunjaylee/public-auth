plugins {
    java
    id("jacoco")
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"

    id("io.gitlab.arturbosch.detekt") version "1.23.6"

    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
    kotlin("kapt") version "1.9.23"
}

java.sourceCompatibility = JavaVersion.VERSION_17
extra["springCloudVersion"] = "2023.0.0"
val kotestVersion = "5.7.2"

group = "org.example"
version = "1.0-SNAPSHOT"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

jacoco {
    toolVersion = "0.8.11"
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

dependencies {
    // spring bean creation
    // EnableJpaRepositoriesConfiguration
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // monitoring
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // logging
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.security:spring-security-acl")

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // session : implementation 'org.springframework.session:spring-session-jdbc'
    implementation("org.springframework.session:spring-session-jdbc")

    // front
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.3.0")

    // email
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // api doc
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // excel : https://mvnrepository.com/artifact/org.apache.poi/poi
    implementation("org.apache.poi:poi:5.3.0")
    implementation("org.apache.poi:poi-ooxml:5.3.0")

    // mail
    implementation("com.sun.mail:javax.mail:1.6.2")

    // streamBridge
//    implementation("org.springframework.cloud:spring-cloud-stream")

    // Dto mapper
//    implementation("com.googlecode.jmapper-framework:jmapper-core:1.6.1.CR1")
    implementation("org.modelmapper:modelmapper:3.1.1")
    implementation("eu.bitwalker:UserAgentUtils:1.21")

    // db
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.data:spring-data-envers")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    // jdbc
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation(kotlin("test"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
//    testImplementation("org.springframework.cloud:spring-cloud-stream-test-support")

    // container test
    implementation("org.apache.commons:commons-lang3:3.17.0")

    testImplementation("org.testcontainers:testcontainers:1.20.1")
    testImplementation("org.testcontainers:junit-jupiter:1.20.1")
    testImplementation("org.testcontainers:mysql:1.20.1")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.3.0")
//    testImplementation("org.testcontainers:postgresql:1.20.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    extensions.configure(JacocoTaskExtension::class) {
        setDestinationFile(file("$rootDir/build/jacoco/jacoco.exec"))
    }
    finalizedBy("jacocoTestReport")
}

tasks.jacocoTestReport {
    reports {
        // 원하는 리포트를 켜고 끌 수 있습니다.
        html.required.set(true)
        xml.required.set(false)
        csv.required.set(false)
    }

//    afterEvaluate {
//        classDirectories.setFrom(files(classDirectories.files.map {
//            fileTree(it) {
//                exclude("**/config/**", "**/security/**", "**/*Test.kt")
//            }
//        }))
//    }

    finalizedBy("jacocoTestCoverageVerification")
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "CLASS"

            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
//                minimum = "0.10".toBigDecimal()
            }

            excludes =
                listOf(
                    "**/*Test.kt",
                    "**/config/**",
                    "**/security/**",
                    "**/test/**",
                    "org.example.auth.config",
                    "org.example.auth.security",
                )
        }
    }
}

val testCoverage by tasks.registering {
    group = "verification"
    description = "Runs the unit tests with coverage"

    dependsOn(
        ":test",
        ":jacocoTestReport",
        ":jacocoTestCoverageVerification",
    )

    tasks["jacocoTestReport"].mustRunAfter(tasks["test"])
    tasks["jacocoTestCoverageVerification"].mustRunAfter(tasks["jacocoTestReport"])
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(file("$rootDir/config/detekt-config.yml"))
}
