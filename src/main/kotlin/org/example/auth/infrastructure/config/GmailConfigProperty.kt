package org.example.auth.infrastructure.config

import jakarta.annotation.PostConstruct
import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.*

@ConfigurationProperties(GmailConfigProperty.PREFIX)
class GmailConfigProperty {
    companion object {
        const val PREFIX: String = "gmail"
    }

    var username = "" // Gmail 계정 이메일
    var password = "" // Gmail 계정 비밀번호 또는 앱 비밀번호
    var smtpAuth = ""
    var starttlsEnable = ""
    var smtpHost = ""
    var smtpPort = ""
    val props: Properties = Properties()

    @PostConstruct
    fun post() {
        props["mail.smtp.auth"] = this.smtpAuth
        props["mail.smtp.starttls.enable"] = this.starttlsEnable
        props["mail.smtp.host"] = this.smtpHost
        props["mail.smtp.port"] = this.smtpPort
    }
}
