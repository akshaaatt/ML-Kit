package com.limerse.mlkit.productsearch

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import com.limerse.mlkit.Utils
import com.limerse.mlkit.objectdetection.DetectedObjectInfo

/** Hosts the detected object info and its search result.  */
class SearchedObject(
        resources: Resources,
        private val detectedObject: DetectedObjectInfo,
        val productList: List<Product>
) {

    private val objectThumbnailCornerRadius: Int = resources.getDimensionPixelOffset(com.limerse.mlkit.R.dimen.bounding_box_corner_radius)
    private var objectThumbnail: Bitmap? = null

    val objectIndex: Int
        get() = detectedObject.objectIndex

    val boundingBox: Rect
        get() = detectedObject.boundingBox

    @Synchronized
    fun getObjectThumbnail(): Bitmap = objectThumbnail ?: let {
        Utils.getCornerRoundedBitmap(detectedObject.getBitmap(), objectThumbnailCornerRadius)
            .also { objectThumbnail = it }
    }
}
