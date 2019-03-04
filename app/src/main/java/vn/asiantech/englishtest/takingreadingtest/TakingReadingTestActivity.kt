package vn.asiantech.englishtest.takingreadingtest

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ListQuestionDetailItem
import vn.asiantech.englishtest.questiondetailviewpager.QuestionAdapter

class TakingReadingTestActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dataQuestion: DatabaseReference
    var questionList = arrayListOf<ListQuestionDetailItem>()
    private var level = 0
    var progressDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_taking_reading_test)
        showProgressDialog()
        initData()
        btnBackToListTest.setOnClickListener(this)
        btnListQuestions.setOnClickListener(this)
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.findFragmentById(R.id.frListQuestions) is TestResultFragment -> finish()
            frListQuestions.visibility == View.VISIBLE -> with(frListQuestions) {
                animation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_out_bottom)
                visibility = View.GONE
            }
            else -> showAlertDialog()
        }
    }

    private fun initData() {
        progressDialog?.show()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frListQuestions, ListQuestionFragment())
            addToBackStack(null)
            commit()
        }
        val position: Int = intent.getIntExtra(getString(R.string.position), 0)
        level = intent.getIntExtra(getString(R.string.level), 0)
        when (level) {
            0 -> {
                tvLevel.text = getString(R.string.part5Basic)
                dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic0${position + 1}")
            }
            1 -> {
                tvLevel.text = getString(R.string.part5Intermediate)
                dataQuestion = FirebaseDatabase.getInstance().getReference("practiceintermediate0${position + 1}")
            }
            2 -> {
                tvLevel.text = getString(R.string.part5Advanced)
                dataQuestion = FirebaseDatabase.getInstance().getReference("practiceadvanced0${position + 1}")
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

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnListQuestions -> {
                if (frListQuestions.visibility == View.VISIBLE) {
                    with(frListQuestions) {
                        animation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_out_bottom)
                        visibility = View.GONE
                    }
                } else {
                    with(frListQuestions) {
                        animation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_in_bottom)
                        visibility = View.VISIBLE
                    }
                }
            }
            R.id.btnBackToListTest -> {
                onBackPressed()
            }
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
                finish()
                intent.putExtra(getString(R.string.level), level)

            }
        }.show()
    }

    private fun showProgressDialog() {
        val builder = AlertDialog.Builder(this@TakingReadingTestActivity)
        val dialogView = layoutInflater.inflate(R.layout.progress_dialog, null)
        dialogView.findViewById<TextView>(R.id.progressDialogMessage).text = getString(R.string.loadingData)
        builder.apply {
            setView(dialogView)
            setCancelable(false)
        }
        progressDialog = builder.create()
    }
}
