package org.example.auth.infrastructure.notify

import org.example.auth.application.auth.service.NotifyService

class SmsNotifyService : NotifyService {
    override fun sendNotify(
        to: String,
        subject: String,
        content: String,
    ) {
        TODO("Not yet implemented")
    }

    override val notiServiceType: NotiServiceType
        get() = NotiServiceType.SMS
}
