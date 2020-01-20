package com.eex.mvp.mine.security.googleverify.bindgoogle1

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import com.eex.R
import com.eex.assets.weight.ZXingUtils
import com.eex.domin.entity.google.Google
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_goole1.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 16:05
 */
class GoogleVerificationActivity : MVPBaseActivity<Google, GoogleContract.View, GooglePresenter>(), GoogleContract.View {
    override val layoutId: Int
        get() = R.layout.re_activity_goole1

    override fun getKeySuccess(google: Google) {
        tv_secret_key.text = google.googleKey
        if (google.userName.contains(" ")) {
            val string = google.userName[1]
            val bitmap = ZXingUtils.createQRImage("otpauth://totp/" + google.company + ":" + string + "?secret=" + google.googleKey, iv_zxing.width, iv_zxing.height)
            iv_zxing.setImageBitmap(bitmap)
        } else {
            val bitmap = ZXingUtils.createQRImage("otpauth://totp/" + google.company + ":" + google.userName + "?secret=" + google.googleKey, iv_zxing.width, iv_zxing.height)
            iv_zxing.setImageBitmap(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.getKey()

        tv_copy_secret.setOnClickListener {
            if (!TextUtils.isEmpty(tv_secret_key.text)) {
                val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val text = tv_secret_key.text.toString()

//                val clip = ClipData.newPlainText("text", text)
//                clipBoard.primaryClip = clip
                clipBoard.text = text
                showToast(R.string.has_copied)
            }
        }

        btn_next_step.setOnClickListener {
            Navigator.toGooleActivity2(this@GoogleVerificationActivity,extra = tv_secret_key.text.toString())

        }

    }
}