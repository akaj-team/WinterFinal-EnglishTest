package vn.asiantech.englishtest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        imgListeningTest.setOnClickListener(this)
        imgReadingTest.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imgListeningTest -> {
                startActivity(Intent(this, ListListeningTestsActivity::class.java))
            }
            R.id.imgReadingTest -> {
                startActivity(Intent(this, ListReadingTestsActivity::class.java))
            }
        }
    }
}
