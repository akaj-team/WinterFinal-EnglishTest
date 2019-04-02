package vn.asiantech.englishtest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import vn.asiantech.englishtest.listtest.TestListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        screenDelay()
    }

    private fun screenDelay() {
        Handler().postDelayed({
            startActivity(Intent(this, TestListActivity::class.java))
            finish()
        }, 3000)
    }
}
