package org.example.auth.application.auth.service

import org.example.auth.infrastructure.notify.NotiServiceType
import org.springframework.scheduling.annotation.Async

interface NotifyService {
    /**
     * 제목, 내용, 받는사람
     */
    @Async
    fun sendNotify(
        to: String,
        subject: String,
        content: String,
    )

    val notiServiceType: NotiServiceType
}
