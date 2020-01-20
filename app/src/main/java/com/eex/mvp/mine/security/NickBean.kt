package com.eex.mvp.mine.security

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/19 10:01
 */
data class NickBean(
        val ctcDealState: Int = 0,
        val examState: Int = 0,
        val id: String = "",
        val merchantStatus: Int = 0,
        val nickName: String = "",
        val updateTime: String? = null,
        val userId: String = "",
        val userName: String = ""
)