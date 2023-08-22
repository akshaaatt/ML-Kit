package com.limurse.mlkit.objectdetection

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.PointF
import com.limurse.mlkit.R
import com.limurse.mlkit.camera.GraphicOverlay
import com.limurse.mlkit.camera.GraphicOverlay.Graphic

/** A dot to indicate a detected object used by multiple objects detection mode.  */
internal class ObjectDotGraphic(
        overlay: GraphicOverlay,
        detectedObject: DetectedObjectInfo,
        private val animator: ObjectDotAnimator
) : Graphic(overlay) {
    private val paint: Paint
    private val center: PointF
    private val dotRadius: Int
    private val dotAlpha: Int

    init {

        val box = detectedObject.boundingBox
        center = PointF(
            overlay.translateX((box.left + box.right) / 2f),
            overlay.translateY((box.top + box.bottom) / 2f)
        )

        paint = Paint().apply {
            style = Style.FILL
            color = Color.WHITE
        }

        dotRadius = context.resources.getDimensionPixelOffset(R.dimen.object_dot_radius)
        dotAlpha = paint.alpha
    }

    override fun draw(canvas: Canvas) {
        paint.alpha = (dotAlpha * animator.alphaScale).toInt()
        canvas.drawCircle(center.x, center.y, dotRadius * animator.radiusScale, paint)
    }
}
