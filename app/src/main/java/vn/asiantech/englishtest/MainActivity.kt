package vn.asiantech.englishtest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenDelay()
    }

    private fun screenDelay() {
        Handler().postDelayed({
            val intentToLoginScreen = Intent(this, LoginActivity::class.java)
            startActivity(intentToLoginScreen)
            finish()
        }, 5000)
    }
}
