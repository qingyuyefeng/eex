package com.eex.mvp.mine.security.legalmethod.alipayorwechat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.compat.BuildConfig
import com.eex.domin.entity.legalmethod.LegalMethod
import com.eex.mvp.MVPBaseActivity
import kotlinx.android.synthetic.main.re_activity_alipayorwechat_setting.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/23 9:50
 */
class AlipayOrWechatActivity : MVPBaseActivity<LegalMethod, AlipayOrWechatContract.View, AlipayOrWechatPresenter>(), AlipayOrWechatContract.View {

    private var type = 0
    private var id = ""
    private var imgUrl = ""

    private val TAKE_PHOTO = 11
    private val CHOOSE_PIC = 22
    private val CUT_PHOTO = 33

    private var alert: AlertDialog? = null

    private var btn_takePhoto: Button? = null
    private var btn_picChoose: Button? = null
    private var btn_cancel: Button? = null

    /**
     * 调用照相机返回图片文件
     */
    private var tempFile: File? = null
    /**
     * 裁剪后的路径
     */
    private var uritempFile: Uri? = null


    override val layoutId: Int
        get() = R.layout.re_activity_alipayorwechat_setting

    override fun getCodeSuccess(legalMethod: LegalMethod) {
        presenter.openTimer(this, btn_get_code, getString(R.string.regain_code))
    }

    override fun uploadPicSuccess(legalMethod: LegalMethod) {
        imgUrl =legalMethod.picName
        iv_receive_code.setImageBitmap(legalMethod.bitmap)
    }

    override fun setMethodSuccess(legalMethod: LegalMethod) {
        showToast(legalMethod.msg)
        CommonUtil.hideKeyboard(this)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = intent.getStringExtra("id")
        type = intent.getIntExtra("type", 1)
        when (type) {
            1 -> {
                title_bar.setTitle(getString(R.string.alipay_setting))
                tv_account.text = getString(R.string.alipay_account)
                et_account.hint = getString(R.string.please_enter_alipay_account)
            }
            2 -> {
                title_bar.setTitle(getString(R.string.wechat_setting))
                tv_account.text = getString(R.string.wechat_account)
                et_account.hint = getString(R.string.please_enter_wechat_account)
            }
        }

        btn_get_code.setOnClickListener {
            if (presenter.phone.isNotEmpty()) {
                presenter.getPhoneCode()
            } else {
                presenter.getEmailCode()
            }
        }
        iv_receive_code.setOnClickListener {
            showAlert()
        }

        btn_save.setOnClickListener {
            val name = et_given_name.text.toString().trim()
            val accountNo = et_account.text.toString().trim()
            val code = et_code.text.toString().trim()

            if(name.isEmpty()){
                showToast(R.string.please_enter_your_name)
                return@setOnClickListener
            }
            if(accountNo.isEmpty()){
                showToast(if(type == 1) R.string.please_enter_alipay_account else R.string.please_enter_wechat_account)
                return@setOnClickListener
            }
            if(code.isEmpty()){
                showToast(R.string.please_input_code)
                return@setOnClickListener
            }
            if(imgUrl.isEmpty()){
                showToast(R.string.please_post_receive_money_code)
                return@setOnClickListener
            }
            val accountType = if (type == 1) "2" else "3"
            presenter.setAlipayOrWechat(accountType, name, id, code, accountNo, imgUrl)
        }

    }
    private fun showAlert() {
        if (alert == null) {
            val view = LayoutInflater.from(this).inflate(R.layout.pop_pictures_selector, null)
            btn_takePhoto = view.findViewById(R.id.btn_take_photo)
            btn_picChoose = view.findViewById(R.id.btn_pic_choose)
            btn_cancel = view.findViewById(R.id.btn_cancel)

            btn_takePhoto!!.setOnClickListener {
                alert!!.dismiss()
                checkTakePermission()

            }
            btn_picChoose!!.setOnClickListener {
                alert!!.dismiss()
                checkPicsPermission()
            }
            btn_cancel!!.setOnClickListener {
                alert!!.dismiss()
            }

            alert = AlertDialog.Builder(this)
                    .setView(view)
                    .setCancelable(false)
                    .show()
        } else {
            alert?.show()
        }
    }
    /**
     * 检查拍照权限
     */
    private fun checkTakePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    showToast(R.string.quanxian)
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1001)
                }
            } else {
                openCamera()
            }
        } else {
            openCamera()
        }

    }

    /**
     * 检查相册权限
     */
    private fun checkPicsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1002)
            } else {
                openAlbum()
            }
        } else {
            openAlbum()
        }
    }

    //打开相机
    private fun openCamera() {

        tempFile = File(Environment.getExternalStorageDirectory().path, System.currentTimeMillis().toString() + ".jpg")
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            val contentUri = FileProvider.getUriForFile(this, "com.exx.fileProvider", tempFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile))
        }
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        startActivityForResult(intent, TAKE_PHOTO)
    }

    //打开相册
    private fun openAlbum() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, CHOOSE_PIC)
    }
    /**
     * 裁剪图片
     *
     * @param uri
     */
    private fun cropPhoto(uri: Uri) {

        val intent = Intent("com.android.camera.action.CROP")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        intent.setDataAndType(uri, "image/*")
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true")
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200)
        intent.putExtra("outputY", 200)
        intent.putExtra("return-data", true)

        /**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故只保存图片Uri，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
        //intent.putExtra("return-data", true);

        //裁剪后的图片Uri路径，uritempFile为Uri类变量
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().path + "/" + "small.jpg")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        startActivityForResult(intent, CUT_PHOTO)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1001 -> openCamera()
            1002 -> openAlbum()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TAKE_PHOTO -> {
                var uri = Uri.fromFile(tempFile)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(this, BuildConfig.AUTHORITY, tempFile!!)
                }
                cropPhoto(uri)
            }
            CHOOSE_PIC -> {
//                try {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        val imgUri = File(GetImagePath.getPath(this, data!!.data))
//                        val uri = FileProvider.getUriForFile(this, BuildConfig.AUTHORITY, imgUri)
//                        cropPhoto(uri)
//                    } else
//                        cropPhoto(data!!.data)
//                } catch (e: NullPointerException) {
//                    e.printStackTrace() // 用户点击取消操作
//                }
                if(data == null){
                    return
                }
                try {
                    val uri = data.data
                    cropPhoto(uri!!)
                }catch (e: NullPointerException) {
                    e.printStackTrace() // 用户点击取消操作
                }
            }
            CUT_PHOTO -> {
                if(data == null){
                    return
                }
                try {
                    val image = BitmapFactory.decodeStream(contentResolver.openInputStream(uritempFile))
                    val path1 = saveImage(if(type == 1) "zhifubao" else "weixin", image)
                    presenter.uploadPic(path1, image)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 图片地址
     */
    private fun saveImage(name: String, bmp: Bitmap): String {
        val appDir = File(Environment.getExternalStorageDirectory().path)
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = "$name.jpg"
        val file = File(appDir, fileName)
        try {
            val fos = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ""
    }

}