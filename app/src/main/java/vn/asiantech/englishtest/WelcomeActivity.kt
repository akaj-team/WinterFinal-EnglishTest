package vn.asiantech.englishtest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        initWelcomeFragment()
    }

    private fun initWelcomeFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.welcomeFragment, WelcomeFragment())
        transaction.commit()
    }
}
