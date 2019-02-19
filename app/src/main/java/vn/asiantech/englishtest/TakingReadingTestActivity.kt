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
        initData()
        btnBackToListTest.setOnClickListener(this)
        btnListQuestions.setOnClickListener(this)
    }

    private fun initData() {
        val position : Int =  intent.getIntExtra("position", 0)
        when(position){
            0->{
                dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic01")
            }
            1->{
                dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic02")
            }
            2->{
                dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic03")
            }
            3->{
                dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic04")
            }
            4->{
                dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic05")
            }
            5->{
                dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic06")
            }
            6->{
                dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic07")
            }
            7->{
                dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic08")
            }
            8->{
                dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic09")
            }
            9->{
                dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic10")
            }
        }
        //dataQuestion = FirebaseDatabase.getInstance().getReference("practicebasic01")
        dataQuestion.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    val question = i.getValue(ListQuestionDetailItem::class.java)
                    questionList.add(question!!)
                }
                questionDetailPager.adapter = QuestionAdapter(supportFragmentManager, questionList)
            }
        })
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
