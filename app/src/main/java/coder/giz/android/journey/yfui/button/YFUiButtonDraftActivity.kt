package coder.giz.android.journey.yfui.button

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coder.giz.android.journey.R
import coder.giz.android.journey.databinding.ActivityYfuiButtonDraftBinding
import coder.giz.android.yfui.base.DataBindingBaseActivity

class YFUiButtonDraftActivity : DataBindingBaseActivity<ActivityYfuiButtonDraftBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_yfui_button_draft

    override fun initView() {
        supportActionBar?.title = "YFUi Button Draft"
    }

}