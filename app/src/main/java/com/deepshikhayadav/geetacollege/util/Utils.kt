package com.deepshikhayadav.geetacollege.util

import android.graphics.*
import java.text.SimpleDateFormat
import java.util.*

class Utils {
  companion object{
      fun decodeFile(
          path: String?, dstWidth: Int, dstHeight: Int,
          scalingLogic: ScalingLogic
      ): Bitmap {
          val options = BitmapFactory.Options()
          options.inJustDecodeBounds = true
          BitmapFactory.decodeFile(path, options)
          options.inJustDecodeBounds = false
          options.inSampleSize = calculateSampleSize(
              options.outWidth, options.outHeight, dstWidth,
              dstHeight, scalingLogic
          )
          return BitmapFactory.decodeFile(path, options)
      }


      fun createScaledBitmap(
          unscaledBitmap: Bitmap, dstWidth: Int, dstHeight: Int,
          scalingLogic: ScalingLogic
      ): Bitmap? {
          val srcRect: Rect = calculateSrcRect(
              unscaledBitmap.width, unscaledBitmap.height,
              dstWidth, dstHeight, scalingLogic
          )
          val dstRect: Rect = calculateDstRect(
              unscaledBitmap.width, unscaledBitmap.height,
              dstWidth, dstHeight, scalingLogic
          )
          val scaledBitmap = Bitmap.createBitmap(
              dstRect.width(), dstRect.height(),
              Bitmap.Config.ARGB_8888
          )
          val canvas = Canvas(scaledBitmap)
          canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, Paint(Paint.FILTER_BITMAP_FLAG))
          return scaledBitmap
      }
      enum class ScalingLogic {
          CROP, FIT
      }

      fun calculateSrcRect(
          srcWidth: Int, srcHeight: Int, dstWidth: Int, dstHeight: Int,
          scalingLogic: ScalingLogic
      ): Rect {
          return if (scalingLogic == ScalingLogic.CROP) {
              val srcAspect = srcWidth.toFloat() / srcHeight.toFloat()
              val dstAspect = dstWidth.toFloat() / dstHeight.toFloat()
              if (srcAspect > dstAspect) {
                  val srcRectWidth = (srcHeight * dstAspect).toInt()
                  val srcRectLeft = (srcWidth - srcRectWidth) / 2
                  Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight)
              } else {
                  val srcRectHeight = (srcWidth / dstAspect).toInt()
                  val scrRectTop = (srcHeight - srcRectHeight) / 2
                  Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight)
              }
          } else {
              Rect(0, 0, srcWidth, srcHeight)
          }
      }


      fun calculateDstRect(
          srcWidth: Int, srcHeight: Int, dstWidth: Int, dstHeight: Int,
          scalingLogic: ScalingLogic
      ): Rect {
          return if (scalingLogic == ScalingLogic.FIT) {
              val srcAspect = srcWidth.toFloat() / srcHeight.toFloat()
              val dstAspect = dstWidth.toFloat() / dstHeight.toFloat()
              if (srcAspect > dstAspect) {
                  Rect(0, 0, dstWidth, (dstWidth / srcAspect).toInt())
              } else {
                  Rect(0, 0, (dstHeight * srcAspect).toInt(), dstHeight)
              }
          } else {
              Rect(0, 0, dstWidth, dstHeight)
          }
      }
      fun calculateSampleSize(
          srcWidth: Int, srcHeight: Int, dstWidth: Int, dstHeight: Int,
          scalingLogic: ScalingLogic
      ): Int {
          return if (scalingLogic == ScalingLogic.FIT) {
              val srcAspect = srcWidth.toFloat() / srcHeight.toFloat()
              val dstAspect = dstWidth.toFloat() / dstHeight.toFloat()
              if (srcAspect > dstAspect) {
                  srcWidth / dstWidth
              } else {
                  srcHeight / dstHeight
              }
          } else {
              val srcAspect = srcWidth.toFloat() / srcHeight.toFloat()
              val dstAspect = dstWidth.toFloat() / dstHeight.toFloat()
              if (srcAspect > dstAspect) {
                  srcHeight / dstHeight
              } else {
                  srcWidth / dstWidth
              }
          }
      }

      fun calculateIntake(weight: Int, workTime: Int): Double {

          return ((weight * 100 / 3.0) + (workTime / 6 * 7))

      }

      fun getCurrentDate(): String? {
          val c = Calendar.getInstance().time
          val df = SimpleDateFormat("dd-MM-yyyy")
          return df.format(c)
      }

      val USERS_SHARED_PREF = "user_pref"
      val PRIVATE_MODE = 0
      val WEIGHT_KEY = "weight"
      val WORK_TIME_KEY = "worktime"
      val TOTAL_INTAKE = "totalintake"
      val DAILY_INTAKE = "dailyintake"
      val NOTIFICATION_STATUS_KEY = "notificationstatus"
      val NOTIFICATION_FREQUENCY_KEY = "notificationfrequency"
      val NOTIFICATION_MSG_KEY = "notificationmsg"
      val SLEEPING_TIME_KEY = "sleepingtime"
      val WAKEUP_TIME = "wakeuptime"
      val NOTIFICATION_TONE_URI_KEY = "notificationtone"
      val FIRST_RUN_KEY = "firstrun"
      val CURRENT_DAY ="01-05-2022"
  }
}