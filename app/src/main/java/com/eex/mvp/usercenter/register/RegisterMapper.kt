package com.eex.mvp.usercenter.register

import com.eex.common.base.Data
import com.eex.domin.entity.register.Register
import com.eex.home.bean.Graphics
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/29 17:29
 */
object RegisterMapper:Function<Data<Any>,Register>{
    override fun apply(t: Data<Any>): Register {
        return Register(
            msg = t.msg
        )
    }
}
object RegisterMapper1:Function<Data<Graphics>,Register>{
    override fun apply(t: Data<Graphics>): Register {
        return Register(
                imageKey = t.data.key,
                imageCode = t.data.image
        )
    }
}
object RegisterMapper2:Function<Data<Any>,Register>{
    override fun apply(t: Data<Any>): Register {
        return Register(
                msg = t.msg
        )
    }
}