package ru.appvelox.chat

import android.graphics.*
import com.squareup.picasso.Transformation
import android.R.attr.bitmap
import android.graphics.RectF
import android.graphics.Bitmap
import android.graphics.BitmapShader


class CircularAvatar : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)

        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }

        val bitmap = Bitmap.createBitmap(size, size, source.config)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(
            squaredBitmap,
            Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
        )
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)

        squaredBitmap.recycle()
        return bitmap
    }

    override fun key(): String {
        return "circle"
    }
}


class ImageTransformation(
    val radius: Float,
    val minWidth: Int,
    val minHeight: Int,
    val maxWidth: Int,
    val maxHeight: Int
) : Transformation {
    override fun transform(source: Bitmap): Bitmap {

        val bitmapWidth = source.width
        val bitmapHeight = source.height

        val resultWidth = if (bitmapWidth < minWidth)
            minWidth
        else if (bitmapWidth > maxWidth)
            maxWidth
        else
            bitmapWidth

        val resultHeight = if (bitmapHeight < minHeight)
            minHeight
        else if (bitmapHeight > maxHeight)
            maxHeight
        else
            bitmapHeight

        val croppedBitmap = Bitmap.createBitmap(source, 0, 0, resultWidth, resultHeight)

        val x = 0
        val y = 0
        val width = croppedBitmap.width
        val height = croppedBitmap.height

        val shapedBitmap = Bitmap.createBitmap(width, height, croppedBitmap.config)
        val shaderBitmap = Bitmap.createBitmap(croppedBitmap, x, y, width, height)

        val canvas = Canvas(shapedBitmap)
        val paint = Paint()
        val bitmapShader = BitmapShader(shaderBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = bitmapShader
        paint.isAntiAlias = true

        val frameThickness = 8

        canvas.drawRoundRect(RectF(0f+frameThickness, 0f+frameThickness, width.toFloat() - frameThickness, height.toFloat() - frameThickness), radius, radius, paint)

        source.recycle()
        shaderBitmap.recycle()
        return shapedBitmap // bitmap
    }

    override fun key() = "image rounded $minWidth $minHeight $maxWidth $maxHeight"
}

//class IncomingImageTransformation(radius: Float, minWidth: Int, minHeight: Int, maxWidth: Int, maxHeight: Int) : ImageTransformation(radius, minWidth, minHeight, maxWidth, maxHeight) {
//    override fun drawCorner(r: Float, size: Int, canvas: Canvas, paint: Paint) {
//        canvas.drawRect(0f, 0f, r, r, paint)
//    }
//
//    override fun key() = "incoming_rounded"
//}
//
//class OutgoingImageTransformation(radius: Float, minWidth: Int, minHeight: Int, maxWidth: Int, maxHeight: Int) : ImageTransformation(radius, minWidth, minHeight, maxWidth, maxHeight) {
//    override fun drawCorner(r: Float, size: Int, canvas: Canvas, paint: Paint) {
//        canvas.drawRect(size - r, 0f, size.toFloat(), r, paint)
//    }
//
//    override fun key() = "outgoing_rounded"
//}


class RoundedImage(val radius: Float) : Transformation {
    override fun transform(source: Bitmap): Bitmap {

        val size = Math.min(source.width, source.height)

        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }

        val bitmap = Bitmap.createBitmap(size, size, source.config)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true

        val r = 20f
        canvas.drawRoundRect(RectF(0f, 0f, source.width.toFloat(), source.height.toFloat()), r, r, paint)

        squaredBitmap.recycle()
        return bitmap

    }

    override fun key(): String {
        return "rounded"
    }
}