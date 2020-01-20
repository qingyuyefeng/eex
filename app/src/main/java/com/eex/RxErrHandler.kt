package com.eex

import android.content.Context
import android.widget.Toast
import com.eex.exceptions.InvalidAuthException
import com.eex.exceptions.ServerException
import com.eex.navigation.Navigator
import com.google.gson.JsonSyntaxException
import com.squareup.moshi.JsonDataException
import com.tencent.mmkv.MMKV
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import javax.inject.Inject

class RxErrHandler @Inject
constructor(
  internal var context: Context,
  val activityStack: ActivityStack,
  val mmkv: MMKV
) : Consumer<Throwable> {

  fun init() {
    RxJavaPlugins.reset()
    RxJavaPlugins.setErrorHandler(this)
  }

  @Throws(Exception::class)
  override fun accept(throwable: Throwable) {
    var throwable = throwable
    when (throwable) {
      is UndeliverableException -> {
        throwable = throwable.cause!!
        when (throwable) {
          is InvalidAuthException -> {
            Navigator.toLogin(context)
            mmkv.clearAll()
            Toast.makeText(context, throwable.message, Toast.LENGTH_LONG)
                .show()
            Timber.e(throwable)
          }
          is ServerException -> {
            Toast.makeText(context, throwable.message, Toast.LENGTH_LONG)
                .show()
            Timber.e(throwable)
          }
          is JsonDataException -> {
            Toast.makeText(context, R.string.json_data_error, Toast.LENGTH_LONG)
                .show()
            Timber.e(throwable)
          }
          is JsonSyntaxException->{ Timber.e(throwable)}
          else -> {
            Toast.makeText(context, throwable.message, Toast.LENGTH_LONG)
                .show()
            Timber.e(throwable)
          }
        }
      }
      /* is ServerException->{
           Toast.makeText(context, throwable.message, Toast.LENGTH_LONG)
                   .show()
           if(context is MVPBaseActivity<*,*,*>){
               val activity = context as MVPBaseActivity<*,*,*>
               activity.logout()
           }
           Timber.e(throwable)
       }*/
      else -> {
        Toast.makeText(context, throwable.message, Toast.LENGTH_LONG)
            .show()
        Timber.e(throwable)
      }
    }
  }
}
