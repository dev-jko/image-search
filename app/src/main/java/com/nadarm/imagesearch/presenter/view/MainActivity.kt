package com.nadarm.imagesearch.presenter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nadarm.imagesearch.AndroidApplication
import com.nadarm.imagesearch.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as AndroidApplication).getAppComponent()


    }
}
