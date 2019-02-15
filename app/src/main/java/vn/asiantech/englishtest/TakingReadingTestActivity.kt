package vn.asiantech.englishtest

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_taking_reading_test.*

class TakingReadingTestActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taking_reading_test)

        btnBackToListTest.setOnClickListener(this)
        btnListQuestions.setOnClickListener(this)

        initQuestionDetailFragment()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnListQuestions -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentQuestionsDisplay, ListQuestionFragment())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.btnBackToListTest -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.fragmentQuestionsDisplay) is QuestionDetailFragment) {
            alertDialog()
        } else {
            super.onBackPressed()
        }
    }

    private fun initQuestionDetailFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentQuestionsDisplay, QuestionDetailFragment())
            .commit()
    }

    private fun alertDialog() {
        val alert = AlertDialog.Builder(this).create()
        with(alert) {
            setTitle(getString(R.string.confirmExit))
            setMessage(getString(R.string.doYouWantToExit))
            setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no)) { dialogInterface, _ -> dialogInterface.dismiss() }
            setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes)) { _, _ -> /*Forward to List Reading Test Screen*/ }
            show()
        }
    }
}
