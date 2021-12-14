package com.aemerse.mlkit_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aemerse.mlkit.R
import com.aemerse.mlkit.translate.TranslateFragment

class LiveTranslateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.translate_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TranslateFragment.newInstance())
                .commitNow()
        }
    }

}
