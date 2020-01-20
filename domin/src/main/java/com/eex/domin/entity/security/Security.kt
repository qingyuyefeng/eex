package com.eex.domin.entity.security

data class Security(
        val number: Int = -1,
        val level: Int = -1,
        val authStatus:Int = -1,
        val isDialogShown: Boolean = false,
        val tradingType: String = "",
        val tradingNick: String = ""
)