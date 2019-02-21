package vn.asiantech.englishtest.takingreadingtest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.TestResultFragment
import vn.asiantech.englishtest.listreadingtest.ListReadingTestActivity
import vn.asiantech.englishtest.model.ListQuestionDetailItem
import vn.asiantech.englishtest.showquestionviewpager.QuestionAdapter
import vn.asiantech.englishtest.showquestionviewpager.QuestionDetailFragment

class TakingReadingTestActivity : AppCompatActivity(), View.OnClickListener {
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
                    0 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic01")
                    }
                    1 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic02")
                    }
                    2 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic03")
                    }
                    3 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic04")
                    }
                    4 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic05")
                    }
                    5 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic06")
                    }
                    6 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic07")
                    }
                    7 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic08")
                    }
                    8 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic09")
                    }
                    9 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic10")
                    }
                }
            }
            2 -> {
                tvLevel.text = getString(R.string.part5Intermediate)
                when (position) {
                    0 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceintermediate01")
                    }
                    1 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceintermediate02")
                    }
                    2 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceintermediate03")
                    }
                    3 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceintermediate04")
                    }
                    4 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceintermediate05")
                    }
                    5 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceintermediate06")
                    }
                    6 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceintermediate07")
                    }
                    7 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceintermediate08")
                    }
                    8 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceintermediate09")
                    }
                    9 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceintermediate10")
                    }
                }
            }
            3 -> {
                tvLevel.text = getString(R.string.part5Advanced)
                when (position) {
                    0 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceadvanced01")
                    }
                    1 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceadvanced02")
                    }
                    2 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceadvanced03")
                    }
                    3 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceadvanced04")
                    }
                    4 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceadvanced05")
                    }
                    5 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceadvanced06")
                    }
                    6 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceadvanced07")
                    }
                    7 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceadvanced08")
                    }
                    8 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceadvanced09")
                    }
                    9 -> {
                        dataQuestion = FirebaseDatabase.getInstance().getReference("practiceadvanced10")
                    }
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
        if (supportFragmentManager.findFragmentById(R.id.frListQuestions) is ListQuestionFragment) {
            super.onBackPressed()
        } else if (supportFragmentManager.findFragmentById(R.id.frListQuestions) is TestResultFragment) {
            startActivity(Intent(this, ListReadingTestActivity::class.java))
        } else if (supportFragmentManager.findFragmentById(R.id.questionDetailPager) is QuestionDetailFragment) {
            showAlertDialog()
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
}
