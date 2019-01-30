package vn.asiantech.englishtest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private val validationPattern = "^[A-Za-z0-9]{4,}$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btnSignup.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        signUpValidation()
    }

    private fun signUpValidation() {
        val minLength = 4
        if (edtUsernameSignup.text.length < minLength) {
            Toast.makeText(application, getString(R.string.ErrorUsername4Characters), Toast.LENGTH_LONG).show()
        } else if (edtPasswordSignup.text.length < minLength) {
            Toast.makeText(application, getString(R.string.ErrorPassword4Characters), Toast.LENGTH_LONG).show()
        } else if (!validateUsername()) {
            Toast.makeText(application, getString(R.string.ErrorUsernameOnlyCharOrDigits), Toast.LENGTH_LONG).show()
        } else if (!validatePassword()) {
            Toast.makeText(application, getString(R.string.ErrorPasswordOnlyCharOrDigits), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(application, getString(R.string.signUpSuccessfully), Toast.LENGTH_LONG).show()
        }
    }

    private fun validateUsername(): Boolean {
        val usernameChecker = Regex(pattern = validationPattern)
        return usernameChecker.containsMatchIn(edtUsernameSignup.text)
    }

    private fun validatePassword(): Boolean {
        val passwordChecker = Regex(pattern = validationPattern)
        return passwordChecker.containsMatchIn(edtPasswordSignup.text)
    }
}
