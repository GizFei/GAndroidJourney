package coder.giz.android.architecture.components.uilayer.viewbinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coder.giz.android.architecture.databinding.ActivityViewBindingBinding

/**
 * Created By GizFei on 2021/10/7
 */
class ViewBindingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewBindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}