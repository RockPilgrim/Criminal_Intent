package my.rockpilgrim.criminalintent.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

object PictureUtils {

    fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {

        // Read picture size
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)

        val srcWidth: Float = options.outWidth.toFloat()
        val srcHeight: Float = options.outHeight.toFloat()

        // Calculating scale
        var inSampleSize: Int = 1
        if (srcHeight > destHeight || srcWidth > destWidth) {
            val heightScale = srcHeight / destHeight
            val widthScale = srcWidth / destWidth
            inSampleSize = if (heightScale > widthScale)
                heightScale.roundToInt()
            else
                widthScale.roundToInt()
        }
        options = BitmapFactory.Options()
        options.inSampleSize = inSampleSize

        // Read data and create picture
        return BitmapFactory.decodeFile(path, options)
    }

    fun getScaledBitmap(path: String, activity: Activity): Bitmap {
        val size: Point = Point()
        activity.windowManager.defaultDisplay.getSize(size)

        return getScaledBitmap(path, size.y, size.x)
    }

}