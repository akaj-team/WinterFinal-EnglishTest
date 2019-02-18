package vn.asiantech.englishtest

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import vn.asiantech.englishtest.model.ListQuestionDetailItem
import vn.asiantech.englishtest.showquestionviewpager.QuestionAdapter
import vn.asiantech.englishtest.showquestionviewpager.QuestionDetailFragment

class TakingReadingTestActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dataQuestion: DatabaseReference
    private var questionList = arrayListOf<ListQuestionDetailItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taking_reading_test)
        dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic01")
        dataQuestion.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (h in p0.children) {
                    val question = h.getValue(ListQuestionDetailItem::class.java)
                    questionList.add(question!!)
                }
                questionDetailPager.adapter = QuestionAdapter(supportFragmentManager, questionList)
            }
        })
        btnBackToListTest.setOnClickListener(this)
        btnListQuestions.setOnClickListener(this)

        initQuestionDetailFragment()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnListQuestions -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.questionDetailPager, ListQuestionFragment())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.btnBackToListTest -> {
                onBackPressed()
            }
        }
    }
    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.questionDetailPager) is QuestionDetailFragment) {
            alertDialog()
        } else {
            super.onBackPressed()
        }
    }

    private fun initQuestionDetailFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.questionDetailPager, QuestionDetailFragment())
            .commit()
    }

    private fun alertDialog() {
        val alert = AlertDialog.Builder(this).create()
        with(alert) {
            setTitle(getString(R.string.confirmExit))
            setMessage(getString(R.string.doYouWantToExit))
            setButton(
                AlertDialog.BUTTON_NEGATIVE,
                getString(R.string.no)
            ) { dialogInterface, _ -> dialogInterface.dismiss() }
            setButton(
                AlertDialog.BUTTON_POSITIVE,
                getString(R.string.yes)
            ) { _, _ -> super.onBackPressed() }
            show()
        }
    }
}
