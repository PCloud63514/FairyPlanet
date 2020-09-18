package com.pcloud.fairyplanet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pcloud.fairyplanet.views.game.gl10.BaseGL10View
import com.pcloud.fairyplanet.views.game.ui.FairyView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var baseGLView = FairyView(this, "Hyena")

        setContentView(baseGLView)
    }
}