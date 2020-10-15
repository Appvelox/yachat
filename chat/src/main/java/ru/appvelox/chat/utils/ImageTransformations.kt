package ru.appvelox.chat.utils

import android.graphics.*
import com.squareup.picasso.Transformation

/**
 * Transformation for message author avatar
 */
class CircularAvatar : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val size = source.width.coerceAtMost(source.height)

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

/**
 * Transformation for image messages
 */
class RoundedRectImage(
    private val radius: Float,
    private val minWidth: Int,
    private val minHeight: Int,
    private val maxWidth: Int,
    private val maxHeight: Int
) : Transformation {
    override fun transform(source: Bitmap): Bitmap {

        val bitmapWidth = source.width
        val bitmapHeight = source.height

        val resultWidth = when {
            bitmapWidth < minWidth -> minWidth
            bitmapWidth > maxWidth -> maxWidth
            else -> bitmapWidth
        }

        val resultHeight = when {
            bitmapHeight < minHeight -> minHeight
            bitmapHeight > maxHeight -> maxHeight
            else -> bitmapHeight
        }

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

        canvas.drawRoundRect(
            RectF(
                0f + frameThickness,
                0f + frameThickness,
                width.toFloat() - frameThickness,
                height.toFloat() - frameThickness
            ), radius, radius, paint
        )

        source.recycle()
        shaderBitmap.recycle()
        return shapedBitmap
    }

    override fun key() = "image rounded $minWidth $minHeight $maxWidth $maxHeight"
}