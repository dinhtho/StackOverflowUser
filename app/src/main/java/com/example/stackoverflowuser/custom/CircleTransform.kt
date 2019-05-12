package com.example.stackoverflowuser.custom

import android.graphics.*
import com.squareup.picasso.Transformation
import android.graphics.Bitmap


/**
 * Created by tho nguyen on 2019-05-12.
 */
class CircleTransform : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)

        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }

        val config = if (source.config != null) source.config else Bitmap.Config.ARGB_8888
        val bitmap = Bitmap.createBitmap(size, size, config)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(
            squaredBitmap,
            Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
        )

        paint.setShader(shader)
        paint.setAntiAlias(true)

        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)

        squaredBitmap.recycle()
        return bitmap
    }

    override fun key(): String {
        return "circle"
    }
}