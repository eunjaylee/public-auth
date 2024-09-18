package org.example.auth.application.cms.service.cms

import org.junit.Test
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class EmailSender {
    @Test
    fun sendTest() {
        val username = "eunjay.ri@gmail.com" // Gmail 계정 이메일
        val password = "yjlosxgnzhtucfrx" // Gmail 계정 비밀번호 또는 앱 비밀번호

        // SMTP 서버 설정
        val props: Properties = Properties()
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.port"] = "587"

        // 인증
        val session: Session =
            Session.getInstance(
                props,
                object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(username, password)
                    }
                },
            )

        try {
            // 이메일 구성
            val message: Message = MimeMessage(session)
            message.setFrom(InternetAddress("eunjay.ri@gmail.com")) // 발신자 이메일
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("rimars@naver.com")) // 수신자 이메일
            message.subject = "Test Subject"
            message.setText("Hello, this is a test email sent from Java!")

            // 이메일 발송
            Transport.send(message)
            println("Email sent successfully")
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }

    @Test
    fun passwordTest() {
        val newPwd = "testtest"

        val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

        val encPwd = passwordEncoder.encode(newPwd)

        val encPwd2 = passwordEncoder.encode(newPwd)

        println(passwordEncoder.matches(newPwd, encPwd))

        println(passwordEncoder.matches(encPwd2, encPwd))
    }
}

fun main(args: Array<String>) {
    val username = "eunjay.ri@gmail.com" // Gmail 계정 이메일
    val password = "yjlosxgnzhtucfrx" // Gmail 계정 비밀번호 또는 앱 비밀번호

    // SMTP 서버 설정
    val props: Properties = Properties()
    props["mail.smtp.auth"] = "true"
    props["mail.smtp.starttls.enable"] = "true"
    props["mail.smtp.host"] = "smtp.gmail.com"
    props["mail.smtp.port"] = "587"

    // 인증
    val session: Session =
        Session.getInstance(
            props,
            object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(username, password)
                }
            },
        )

    try {
        // 이메일 구성
        val message: Message = MimeMessage(session)
        message.setFrom(InternetAddress("eunjay.ri@gmail.com")) // 발신자 이메일
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("rimars@naver.com")) // 수신자 이메일
        message.subject = "Test Subject"
        message.setText("Hello, this is a test email sent from Java!")

        // 이메일 발송
        Transport.send(message)
        println("Email sent successfully")
    } catch (e: MessagingException) {
        e.printStackTrace()
    }
}
