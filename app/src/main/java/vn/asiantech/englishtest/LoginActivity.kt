package vn.asiantech.englishtest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val validationPattern = "^[A-Za-z0-9]{4,}$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnLogin.setOnClickListener(this)
        tvToSignUpFragment.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnLogin -> {
                loginValidation()
            }
            R.id.tvToSignUpFragment -> {
                initSignUpActivity()
            }
        }
    }

    private fun initSignUpActivity() {
        val intent = Intent(application, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun loginValidation() {
        val minLength = 4
        if (edtUsernameLogin.text.length < minLength) {
            Toast.makeText(application, getString(R.string.ErrorUsername4Characters), Toast.LENGTH_LONG).show()
        } else if (edtPasswordLogin.text.length < minLength) {
            Toast.makeText(application, getString(R.string.ErrorPassword4Characters), Toast.LENGTH_LONG).show()
        } else if (!validateUsername()) {
            Toast.makeText(application, getString(R.string.ErrorUsernameOnlyCharOrDigits), Toast.LENGTH_LONG).show()
        } else if (!validatePassword()) {
            Toast.makeText(application, getString(R.string.ErrorPasswordOnlyCharOrDigits), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(application, getString(R.string.loginSuccessfully), Toast.LENGTH_LONG).show()
            //Init Welcome Activity here
        }
    }

    private fun validateUsername(): Boolean {
        val usernameChecker = Regex(pattern = validationPattern)
        return usernameChecker.containsMatchIn(edtUsernameLogin.text)
    }

    private fun validatePassword(): Boolean {
        val passwordChecker = Regex(pattern = validationPattern)
        return passwordChecker.containsMatchIn(edtPasswordLogin.text)
    }
}
