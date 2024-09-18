package org.example.auth.infrastructure.notify

import org.example.auth.application.auth.service.NotifyService
import org.springframework.stereotype.Component

@Component
class NotifyServiceFactory(
    private val serviceMap: MutableMap<NotiServiceType, NotifyService> = mutableMapOf(),
    val notifyServiceList: List<NotifyService>,
) {
    init {
        notifyServiceList.forEach {
            serviceMap[it.notiServiceType] = it
        }
    }

    fun getUpdateStateService(notiServiceType: NotiServiceType): NotifyService? {
        return serviceMap[notiServiceType]
    }
}
