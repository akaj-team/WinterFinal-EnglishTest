package vn.asiantech.englishtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        initWelcomeFragment()
    }

    private fun initWelcomeFragment(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.welcomeFragment, WelcomeFragment())
        transaction.commit()
    }
}
