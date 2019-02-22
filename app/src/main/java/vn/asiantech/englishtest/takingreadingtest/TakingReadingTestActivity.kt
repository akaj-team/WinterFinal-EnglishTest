package vn.asiantech.englishtest.takingreadingtest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.listreadingtest.ListReadingTestActivity
import vn.asiantech.englishtest.model.ListQuestionDetailItem
import vn.asiantech.englishtest.showquestionviewpager.QuestionAdapter

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
        val level: Int = intent.getIntExtra("level", 0)
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
                val positionQuestion: Int = intent.getIntExtra("positionQuestion", 0)
                questionDetailPager.adapter = QuestionAdapter(supportFragmentManager, questionList)
                questionDetailPager.currentItem = positionQuestion
            }
        })

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frListQuestions, ListQuestionFragment())
            addToBackStack(null)
            commit()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnListQuestions -> {
                if (frListQuestions.visibility == View.VISIBLE) {
                    with(frListQuestions){
                        animation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_out_top)
                        visibility = View.GONE
                    }
                } else {
                    with(frListQuestions){
                        animation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_in_top)
                        visibility = View.VISIBLE
                    }
                }
            }
            R.id.btnBackToListTest -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        if (frListQuestions.visibility == View.GONE) showAlertDialog()
        else frListQuestions.visibility = View.GONE
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
                startActivity(Intent(applicationContext, ListReadingTestActivity::class.java))
            }
        }.show()
    }
}
