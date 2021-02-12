package com.ikmr.banbara23.yaeyama_liner_checker.ui.portstatusdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.ikmr.banbara23.yaeyama_liner_checker.R

/**
 * 運行ステータス詳細 Activity
 */
class PortStatusDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.port_status_detail_activity)
        supportFragmentManager.commit {
            replace(R.id.container, PortStatusDetailFragment.newInstance(intent?.extras!!))
        }
    }
}
