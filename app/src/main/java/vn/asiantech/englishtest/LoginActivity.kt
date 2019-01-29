package vn.asiantech.englishtest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnLogin.setOnClickListener(this)
        tvToSignUpFragment.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        initSignUpActivity()
    }

    private fun initSignUpActivity() {
        val intent = Intent(application, SignUpActivity::class.java)
        startActivity(intent)
    }
}
