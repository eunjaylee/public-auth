package org.example.auth.infrastructure.notify

import org.example.auth.application.auth.service.NotifyService
import org.example.auth.infrastructure.config.GmailConfigProperty
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class GmailNotifyServiceImpl(val property: GmailConfigProperty, override val notiServiceType: NotiServiceType) :
    NotifyService {
    private val session: Session =
        Session.getInstance(
            property.props,
            object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(property.username, property.password)
                }
            },
        )

    override fun sendNotify(
        to: String,
        subject: String,
        content: String,
    ) {
        println(property.props.map { println("${it.key} : ${it.value}") })

        try {
            // 이메일 구성
            val message: Message = MimeMessage(session)
            message.setFrom(InternetAddress("eunjay.ri@gmail.com")) // 발신자 이메일
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)) // 수신자 이메일
            message.subject = subject
            message.setText(content)
            // 이메일 발송
            Transport.send(message)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }
}
