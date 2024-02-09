package kg.edu.yjut.litenote.miuiStringToast
/*
  * This file is part of HyperCeiler.

  * HyperCeiler is free software: you can redistribute it and/or modify
  * it under the terms of the GNU Affero General Public License as
  * published by the Free Software Foundation, either version 3 of the
  * License.

  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU Affero General Public License for more details.

  * You should have received a copy of the GNU Affero General Public License
  * along with this program.  If not, see <https://www.gnu.org/licenses/>.

  * Copyright (C) 2023-2024 HyperCeiler Contributions
*/

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import kg.edu.yjut.hyperos.miuistrongtoast.bean.StringToastBean
import kg.edu.yjut.litenote.miuiStringToast.devicesSDK.isMoreHyperOSVersion

import kg.edu.yjut.hyperos.miuistrongtoast.bean.StringToastBundle
import java.lang.reflect.InvocationTargetException

object MiuiStrongToast {


    @SuppressLint("WrongConstant")
    fun showStringToast(context: Context,
                        config: ToastConfig
    ) {
        try {

            val textParams = StringToastBean.TextParams(
                config.text,
                colorToInt(config.textColor),
            )
            var iconParams = StringToastBean.IconParams(
                Category.DRAWABLE,
                FileType.SVG,
                config.image,
                1
            )

            var left = StringToastBean.Left(
                null,
                textParams
            )

            Log.d("STRONGToast", left.toString())



            val right = StringToastBean.Right(
                iconParams,
                null
            )

            val stringToastBean = StringToastBean(
                left,
                right
            )

            val gson = Gson()
            val str = gson.toJson(stringToastBean)

            val bundle: Bundle = StringToastBundle.Builder()
                .setPackageName(context.packageName)
                .setStrongToastCategory(StrongToastCategory.TEXT_BITMAP_INTENT.value)
                .setTarget(config.intent)
                .setDuration(config.duration)
                .setLevel(1.0f)
                .setRapidRate(0.2f)
                .setCharge(null as String?)
                .setStringToastChargeFlag(0)
                .setParam(str)
                .setStatusBarStrongToast("show_custom_strong_toast")
                .onCreate()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && isMoreHyperOSVersion(1f)) {
                val service = context.getSystemService(Context.STATUS_BAR_SERVICE)
                service.javaClass.getMethod(
                    "setStatus",
                    Int::class.javaPrimitiveType,
                    String::class.java,
                    Bundle::class.java
                )
                    .invoke(service, 1, "strong_toast_action", bundle)
            } else {
                Toast.makeText(context, config.text, Toast.LENGTH_SHORT).show()
            }
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException(e)
        }
    }

    fun colorToInt(color: String?): Int {
        val color1 = Color.parseColor(color)
        val color2 = Color.parseColor("#FFFFFF")
        val color3 = color1 xor color2
        return color3.inv()
    }

    object Category {
        const val RAW = "raw"
        const val DRAWABLE = "drawable"
        const val FILE = "file"
        const val MIPMAP = "mipmap"
    }

    object FileType {
        const val MP4 = "mp4"
        const val PNG = "png"
        const val SVG = "svg"
        const val WEBP = "webp"
    }

    enum class StrongToastCategory(var value: String) {
        VIDEO_TEXT("video_text"),
        VIDEO_BITMAP_INTENT("video_bitmap_intent"),
        TEXT_BITMAP("text_bitmap"),
        TEXT_BITMAP_INTENT("text_bitmap_intent"),
        VIDEO_TEXT_TEXT_VIDEO("video_text_text_video"),
        TEXT_TEXT("text_text")


    }
}