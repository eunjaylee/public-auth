package org.example.auth.config

// import org.example.auth.application.auth.oauth2.CustomOAuth2UserService
import org.example.auth.application.auth.oauth2.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig(
    val customOAuth2UserService: CustomOAuth2UserService,
    val passwordEncoder: PasswordEncoder,
    val userService: UserDetailsService,
    val customFailHandler: CustomFailHandler,
    val customSuccessHandler: CustomSuccessHandler,
//    val oauth2SuccessHandler: Oauth2SuccessHandler,
//    val jpaClientRegisterationRepository: JpaClientRegisterationRepository,
//    val roleHierarchyJpaDao: RoleHierarchyJpaDao,
    val authorizedClientService: OAuth2AuthorizedClientService,
    val jdbcTemplate: JdbcTemplate,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { csrf -> csrf.disable() } // csrf.csrfTokenRepository(cookieCsrfTokenRepository())}
            .exceptionHandling { exceptionHandling ->
                exceptionHandling
                    .accessDeniedPage("/errors/access-denied")
            }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/api/my/**").hasRole("USER")
                    .anyRequest().permitAll()
            }
            .formLogin { form ->
                form.usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/", true)
                    .permitAll()
                    .failureHandler(customFailHandler)
                    .successHandler(customSuccessHandler)
            }
            .oauth2Login {
                    oauth2 ->
                oauth2
//                .clientRegistrationRepository(jpaClientRegisterationRepository)
//                .successHandler(oauth2SuccessHandler)
                    .userInfoEndpoint { userInfoEndpoint ->
                        userInfoEndpoint.userService(customOAuth2UserService)
                    }
//                    .authenticationSuccessHandler(loginSuccessHandler(redirectUrl))
//                    .authenticationFailureHandler(RedirectServerAuthenticationFailureHandler(redirectUrl))
//                  oauth2 -> oauth2
//                      .authorizedClientService(authorizedClientService)  ==> 이것이 없어도 되는가?
//                oauth2 -> oauth2.userInfoEndpoint { userInfo -> userInfo.userService {  } }
            }
            .logout { logout ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
            }
            .sessionManagement {
                    sessionManager ->
                sessionManager.maximumSessions(1)
                    .maxSessionsPreventsLogin(false) // 기존 세션 만료, 새로운 로그인 허용
                    .expiredUrl("/session-expired")
            }
            .addFilterBefore(OAuthRoleFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .authenticationProvider(customAuthenticationManager())
        return http.build()
    }

    @Bean
    fun customAuthenticationManager(): AuthenticationProvider {
        val provider = DaoAuthenticationProvider(passwordEncoder)
        val authorityMapper = SimpleAuthorityMapper()
        return provider.apply {
            setUserDetailsService(userService)
        }

//            .setUserDetailsService(userService)

//        provider.setUserDetailsPasswordService()
//        provider.setPasswordEncoder(passwordEncoder())
//        return provider
    }

    @Bean
    fun cookieCsrfTokenRepository(): CookieCsrfTokenRepository {
        return CookieCsrfTokenRepository.withHttpOnlyFalse() // 쿠키의 HttpOnly 속성 설정
    }

//    @Bean
//    fun roleHierarchy(): RoleHierarchy {
//        val roleHierarchy = RoleHierarchyImpl()
//        roleHierarchy.setHierarchy(buildRoleHierarchy())
//        return roleHierarchy
//    }
//
//    private fun buildRoleHierarchy(): String {
//        val hierarchy = StringBuilder()
//
//        // 데이터베이스에서 역할 계층 구조를 로드
//        val roleHierarchies: List<org.example.auth.domain.auth.entity.auth.RoleHierarchy> = roleHierarchyJpaDao.findAll()
//        for (roleHierarchy in roleHierarchies) {
//            hierarchy.append(roleHierarchy.parentRole)
//                .append(" > ")
//                .append(roleHierarchy.childRole)
//                .append("\n")
//        }
//
//        return hierarchy.toString()
//    }
}
