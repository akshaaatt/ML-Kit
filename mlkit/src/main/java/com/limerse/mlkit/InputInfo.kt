package com.limerse.mlkit

import android.graphics.Bitmap
import com.limerse.mlkit.camera.FrameMetadata
import java.nio.ByteBuffer

interface InputInfo {
    fun getBitmap(): Bitmap
}

class CameraInputInfo(
    private val frameByteBuffer: ByteBuffer,
    private val frameMetadata: FrameMetadata
) : InputInfo {

    private var bitmap: Bitmap? = null

    @Synchronized
    override fun getBitmap(): Bitmap {
        return bitmap ?: let {
            bitmap = Utils.convertToBitmap(
                    frameByteBuffer, frameMetadata.width, frameMetadata.height, frameMetadata.rotation
            )
            bitmap!!
        }
    }
}

class BitmapInputInfo(private val bitmap: Bitmap) : InputInfo {
    override fun getBitmap(): Bitmap {
        return bitmap
    }
}
