package com.limurse.mlkit_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.limurse.mlkit.*
import com.limurse.mlkit.R

/** Entry activity to select the detection mode.  */
class MainActivity : AppCompatActivity() {

    private enum class DetectionMode(val titleResId: Int, val subtitleResId: Int) {
        ODT_LIVE(
            R.string.mode_odt_live_title,
            R.string.mode_odt_live_subtitle
        ),
        ODT_STATIC(
            R.string.mode_odt_static_title,
            R.string.mode_odt_static_subtitle
        ),
        BARCODE_LIVE(
            R.string.mode_barcode_live_title,
            R.string.mode_barcode_live_subtitle
        ),
        CUSTOM_MODEL_LIVE(
            R.string.custom_model_live_title,
            R.string.custom_model_live_subtitle
        ),
        TRANSLATE_LIVE(
            R.string.mode_translate_live_title,
            R.string.mode_translate_live_subtitle
        )
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.mode_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ModeItemAdapter(DetectionMode.values())
        }
    }

    override fun onResume() {
        super.onResume()
        if (!Utils.allPermissionsGranted(this)) {
            Utils.requestRuntimePermissions(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Utils.REQUEST_CODE_PHOTO_LIBRARY &&
            resultCode == Activity.RESULT_OK &&
            data != null
        ) {
            val intent = Intent(this, StaticObjectDetectionActivity::class.java)
            intent.data = data.data
            startActivity(intent)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private inner class ModeItemAdapter(private val detectionModes: Array<DetectionMode>) :
        RecyclerView.Adapter<ModeItemAdapter.ModeItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModeItemViewHolder {
            return ModeItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.detection_mode_item, parent, false
                    )
            )
        }

        override fun onBindViewHolder(modeItemViewHolder: ModeItemViewHolder, position: Int) =
            modeItemViewHolder.bindDetectionMode(detectionModes[position])

        override fun getItemCount(): Int = detectionModes.size

        private inner class ModeItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            private val titleView: TextView = view.findViewById(R.id.mode_title)
            private val subtitleView: TextView = view.findViewById(R.id.mode_subtitle)

            fun bindDetectionMode(detectionMode: DetectionMode) {
                titleView.setText(detectionMode.titleResId)
                subtitleView.setText(detectionMode.subtitleResId)
                itemView.setOnClickListener {
                    val activity = this@MainActivity
                    when (detectionMode) {
                        DetectionMode.ODT_LIVE ->
                            activity.startActivity(Intent(activity, LiveObjectDetectionActivity::class.java))
                        DetectionMode.ODT_STATIC -> Utils.openImagePicker(
                            activity
                        )
                        DetectionMode.BARCODE_LIVE ->
                            activity.startActivity(Intent(activity, LiveBarcodeScanningActivity::class.java))
                        DetectionMode.CUSTOM_MODEL_LIVE ->
                            activity.startActivity(Intent(activity, CustomModelObjectDetectionActivity::class.java))
                        DetectionMode.TRANSLATE_LIVE ->
                            activity.startActivity(Intent(activity, LiveTranslateActivity::class.java))
                    }
                }
            }
        }
    }
}
