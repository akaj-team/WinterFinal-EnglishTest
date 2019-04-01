@file:Suppress("DEPRECATION")

package vn.asiantech.englishtest.takingreadingtest

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import kotlinx.android.synthetic.main.fragment_test_result.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.grammardetail.GrammarDetailFragment
import vn.asiantech.englishtest.grammarlist.GrammarListFragment
import vn.asiantech.englishtest.listreadingtest.ListReadingTestFragment
import vn.asiantech.englishtest.model.GrammarItem
import vn.asiantech.englishtest.model.ListQuestionDetailItem
import vn.asiantech.englishtest.questiondetailviewpager.QuestionAdapter

@Suppress("DEPRECATION")
class TakingReadingTestActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dataQuestion: DatabaseReference
    var questionList = arrayListOf<ListQuestionDetailItem>()
    private var grammarList = arrayListOf<GrammarItem>()
    var progressDialog: ProgressDialog? = null
    var mediaPlayer: MediaPlayer? = null
    var score = 0
    var review = false

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_taking_reading_test)
        window.statusBarColor = resources.getColor(R.color.colorBlue)
        progressDialog = ProgressDialog(this)
        initData()
        btnBackToListTest.setOnClickListener(this)
        btnListQuestions.setOnClickListener(this)
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.findFragmentById(R.id.frListQuestions) is TestResultFragment -> setResult()
            supportFragmentManager.findFragmentById(R.id.frListQuestions) is GrammarDetailFragment -> finish()
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
                onBackPressed()
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
        initProgressDialog()
        val level: Int = intent.getIntExtra(ListReadingTestFragment.ARG_LEVEL, 0)
        val position: Int = intent.getIntExtra(ListReadingTestFragment.ARG_POSITION, 0)
        FirebaseDatabase.getInstance().apply {
            when (level) {
                R.id.itemPart1 -> {
                    tvLevel.text = getString(R.string.part1)
                    dataQuestion = getReference("part1-0${position + 1}")
                }
                R.id.itemPart2 -> {
                    tvLevel.text = getString(R.string.part2)
                    dataQuestion = getReference("part2-0${position + 1}")
                }
                R.id.itemPart5Basic -> {
                    tvLevel.text = getString(R.string.part5Basic)
                    dataQuestion = getReference("part5basic0${position + 1}")
                }
                R.id.itemPart5Intermediate -> {
                    tvLevel.text = getString(R.string.part5Intermediate)
                    dataQuestion = getReference("part5intermediate0${position + 1}")
                }
                R.id.itemPart5Advanced -> {
                    tvLevel.text = getString(R.string.part5Advanced)
                    dataQuestion = getReference("part5advanced0${position + 1}")
                }
                R.id.itemPart6 -> {
                    tvLevel.text = getString(R.string.part6)
                    dataQuestion = getReference("part6-0${position + 1}")
                }
                R.id.itemPart7 -> {
                    tvLevel.text = getString(R.string.part7)
                    dataQuestion = getReference("part7-0${position + 1}")
                }
                R.id.itemGrammar -> {
                    grammarList = intent.getParcelableArrayListExtra(GrammarListFragment.ARG_GRAMMAR_LIST)
                    tvLevel.text = grammarList[position].grammarTitle
                    initGrammarDetailFragment()
                }
            }
        }

        if (level != R.id.itemGrammar) {
            dataQuestion.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(dataPractice: DatabaseError) {
                    dismissProgressDialog()
                }

                override fun onDataChange(dataPractice: DataSnapshot) {
                    dismissProgressDialog()
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
    }

    private fun initGrammarDetailFragment() {
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
            replace(R.id.frListQuestions, GrammarDetailFragment())
            commit()
        }
        frListQuestions.visibility = View.VISIBLE
        chronometer.visibility = View.GONE
        btnListQuestions.visibility = View.GONE
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
        progressDialog?.apply {
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            setMessage(getString(R.string.loadingData))
            show()
        }
    }

    fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }
}
