package vn.asiantech.englishtest.takingreadingtest

import android.text.TextUtils.replace

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import kotlinx.android.synthetic.main.fragment_test_result.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.listreadingtest.ListReadingTestFragment
import vn.asiantech.englishtest.model.ListQuestionDetailItem
import vn.asiantech.englishtest.questiondetailviewpager.QuestionAdapter

class TakingReadingTestActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dataQuestion: DatabaseReference
    var questionList = arrayListOf<ListQuestionDetailItem>()
    var progressDialog: AlertDialog? = null
    var score = 0
    var review = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_taking_reading_test)
        initProgressDialog()
        initData()
        btnBackToListTest.setOnClickListener(this)
        btnListQuestions.setOnClickListener(this)
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.findFragmentById(R.id.frListQuestions) is TestResultFragment -> setResult()
            frListQuestions.visibility == View.VISIBLE -> with(frListQuestions) {
                animation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_out_bottom)
                visibility = View.GONE
            }
            else -> initAlertDialog()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnListQuestions -> {
                if (frListQuestions.visibility == View.GONE) {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frListQuestions, ListQuestionFragment())
                        addToBackStack(null)
                        commit()
                    }
                    with(frListQuestions) {
                        animation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_in_bottom)
                        visibility = View.VISIBLE
                    }
                } else {
                    with(frListQuestions) {
                        animation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_out_bottom)
                        visibility = View.GONE
                    }
                }
            }
            R.id.btnBackToListTest -> {
                setResult()
            }
        }
    }

    private fun setResult() {
        setResult(
            Activity.RESULT_OK, Intent()
                .putExtra(TestResultFragment.KEY_TIME, tvDurationTime.text.toString())
                .putExtra(TestResultFragment.KEY_SCORE, score.toString())
                .putExtra(
                    ListReadingTestFragment.ARG_POSITION,
                    intent.getIntExtra(ListReadingTestFragment.ARG_POSITION, 0)
                )
        )
        finish()
    }

    private fun initData() {
        progressDialog?.show()
        val position: Int = intent.getIntExtra(ListReadingTestFragment.ARG_POSITION, 0)
        when (intent.getIntExtra(ListReadingTestFragment.ARG_LEVEL, 0)) {
            R.id.itemPart5Basic -> {
                tvLevel.text = getString(R.string.part5Basic)
                dataQuestion = FirebaseDatabase.getInstance().getReference("part5basic0${position + 1}")
            }
            R.id.itemPart5Intermediate -> {
                tvLevel.text = getString(R.string.part5Intermediate)
                dataQuestion = FirebaseDatabase.getInstance().getReference("part5intermediate0${position + 1}")
            }
            R.id.itemPart5Advanced -> {
                tvLevel.text = getString(R.string.part5Advanced)
                dataQuestion = FirebaseDatabase.getInstance().getReference("part5advanced0${position + 1}")
            }
            R.id.itemPart6 -> {
                tvLevel.text = getString(R.string.part6)
                dataQuestion = FirebaseDatabase.getInstance().getReference("part6-0${position + 1}")
            }
            R.id.itemPart7 -> {
                tvLevel.text = getString(R.string.part7)
                dataQuestion = FirebaseDatabase.getInstance().getReference("part7-0${position + 1}")
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
                questionDetailPager?.adapter = QuestionAdapter(supportFragmentManager, questionList)
            }
        })
    }

    private fun initAlertDialog() {
        AlertDialog.Builder(this).create().apply {
            setTitle(getString(R.string.confirmExit))
            setMessage(getString(R.string.doYouWantToExit))
            setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no))
            { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes))
            { _, _ ->
                finish()
            }
        }.show()
    }

    private fun initProgressDialog() {
        val builder = AlertDialog.Builder(this@TakingReadingTestActivity)
        val dialogView = layoutInflater.inflate(R.layout.progress_dialog, null)
        dialogView.findViewById<TextView>(R.id.progressDialogMessage).text = getString(R.string.loadingData)
        builder.setView(dialogView)
        progressDialog = builder.create()
    }
}
