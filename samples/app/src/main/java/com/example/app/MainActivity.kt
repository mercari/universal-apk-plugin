package com.example.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.goToFeature

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToFeature.setOnClickListener {
            Intent(Intent.ACTION_VIEW)
                .setClassName(this@MainActivity, "com.example.feature.FeatureActivity")
                .let(::startActivity)
        }
    }
}
