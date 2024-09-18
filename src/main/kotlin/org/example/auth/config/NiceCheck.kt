package org.example.auth.config

class NiceCheck(
    obj: Any,
    var plainData: String = "",
    var cipherData: String = "",
) {
    fun fnParse(plainData: Any): String {
        return ""
    }

    fun getRequestNO(siteCode: String): String {
        return ""
    }

    fun fnEncode(
        siteCode: String,
        sitePassword: String,
        plainData: String,
    ): Int {
        return 0
    }

    fun fnDecode(
        siteCode: String,
        sitePassword: String,
        encodeData: String,
    ): Int {
        return 0
    }
}
