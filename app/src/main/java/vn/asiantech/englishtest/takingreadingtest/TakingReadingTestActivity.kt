package vn.asiantech.englishtest.takingreadingtest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.TestResultFragment
import vn.asiantech.englishtest.listreadingtest.ListReadingTestActivity
import vn.asiantech.englishtest.model.ListQuestionDetailItem
import vn.asiantech.englishtest.showquestionviewpager.QuestionAdapter
import vn.asiantech.englishtest.showquestionviewpager.QuestionDetailFragment

class TakingReadingTestActivity : AppCompatActivity(), View.OnClickListener, ListQuestionFragment.OnClick {

    private lateinit var dataQuestion: DatabaseReference
    private var questionList = arrayListOf<ListQuestionDetailItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taking_reading_test)
        initData()
        btnBackToListTest.setOnClickListener(this)
        btnListQuestions.setOnClickListener(this)
        chronometer.start()
    }

    private fun initData() {
        val position: Int = intent.getIntExtra("position", 0)
        val level: Int = intent.getIntExtra("level", 1)
        when (level) {
            1 -> {
                tvLevel.text = getString(R.string.part5Basic)
                when (position) {
                    in 0..9 -> dataQuestion =
                        FirebaseDatabase.getInstance().getReference("practicebasic0${position + 1}")
                }
            }
            2 -> {
                tvLevel.text = getString(R.string.part5Intermediate)
                when (position) {
                    in 0..9 -> dataQuestion =
                        FirebaseDatabase.getInstance().getReference("practiceintermediate0${position + 1}")
                }
            }
            3 -> {
                tvLevel.text = getString(R.string.part5Advanced)
                when (position) {
                    in 0..9 -> dataQuestion =
                        FirebaseDatabase.getInstance().getReference("practiceadvanced0${position + 1}")
                }
            }
        }
        dataQuestion.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(dataPractice: DatabaseError) {
                TODO("not implemented")
            }

            override fun onDataChange(dataPractice: DataSnapshot) {
                for (i in dataPractice.children) {
                    val question = i.getValue(ListQuestionDetailItem::class.java)
                    question?.let {
                        questionList.add(it)
                    }
                }
                questionDetailPager.adapter = QuestionAdapter(supportFragmentManager, questionList)
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnListQuestions -> {
                if (supportFragmentManager.findFragmentById(R.id.frListQuestions) is ListQuestionFragment) {
                    super.onBackPressed()
                } else {
                    supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top)
                        .replace(R.id.frListQuestions, ListQuestionFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
            R.id.btnBackToListTest -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.findFragmentById(R.id.frListQuestions) is ListQuestionFragment -> super.onBackPressed()
            supportFragmentManager.findFragmentById(R.id.frListQuestions) is TestResultFragment -> startActivity(
                Intent(
                    this,
                    ListReadingTestActivity::class.java
                )
            )
            supportFragmentManager.findFragmentById(R.id.questionDetailPager) is QuestionDetailFragment -> showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this).create().apply {
            setTitle(getString(R.string.confirmExit))
            setMessage(getString(R.string.doYouWantToExit))
            setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no))
            { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes))
            { _, _ ->
                super.onBackPressed()
            }
        }.show()
    }

    override fun onClickSubmit() {
        Log.i("xxxx", "xxx")
        chronometer.stop()
    }
}
