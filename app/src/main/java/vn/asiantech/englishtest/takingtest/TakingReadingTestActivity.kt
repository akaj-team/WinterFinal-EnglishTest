@file:Suppress("DEPRECATION")

package vn.asiantech.englishtest.takingtest

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import kotlinx.android.synthetic.main.fragment_test_result.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.grammardetail.GrammarDetailFragment
import vn.asiantech.englishtest.grammarlist.GrammarListFragment
import vn.asiantech.englishtest.listtest.TestListFragment
import vn.asiantech.englishtest.model.GrammarListItem
import vn.asiantech.englishtest.model.QuestionDetailItem
import vn.asiantech.englishtest.model.WordListItem
import vn.asiantech.englishtest.questiondetailviewpager.QuestionDetailAdapter
import vn.asiantech.englishtest.wordlist.WordListFragment
import vn.asiantech.englishtest.wordstudy.WordStudyFragment

@Suppress("DEPRECATION")
class TakingReadingTestActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dataQuestion: DatabaseReference
    var questionList = arrayListOf<QuestionDetailItem>()
    private var grammarList = arrayListOf<GrammarListItem>()
    private var testTitleList = arrayListOf<WordListItem>()
    var progressDialog: ProgressDialog? = null
    var mediaPlayer: MediaPlayer? = null
    var score = 0
    var review = false
    var level: Int = -1
    var position: Int = -1

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        level = intent.getIntExtra(TestListFragment.ARG_LEVEL, 0)
        position = intent.getIntExtra(TestListFragment.ARG_POSITION, 0)

        setContentView(R.layout.activity_taking_reading_test)
        window.statusBarColor = resources.getColor(R.color.colorBlue)
        progressDialog = ProgressDialog(this)
        notifyNetworkStatus()
        initData()
        btnBackToListTest.setOnClickListener(this)
        btnListQuestions.setOnClickListener(this)
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.findFragmentById(R.id.frListQuestions) is TestResultFragment -> setResult()
            supportFragmentManager.findFragmentById(R.id.frListQuestions) is GrammarDetailFragment -> finish()
            supportFragmentManager.findFragmentById(R.id.frListQuestions) is WordStudyFragment -> finish()
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
                    TestListFragment.ARG_POSITION,
                    intent.getIntExtra(TestListFragment.ARG_POSITION, 0)
                )
        )
        finish()
    }

    private fun initData() {
        initProgressDialog()
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
                R.id.itemPart3 -> {
                    tvLevel.text = getString(R.string.part3)
                    dataQuestion = getReference("part3-0${position + 1}")
                }
                R.id.itemPart4 -> {
                    tvLevel.text = getString(R.string.part4)
                    dataQuestion = getReference("part4-0${position + 1}")
                }
                R.id.itemPart5Basic -> {
                    tvLevel.text = getString(R.string.part5Basic)
                    dataQuestion = getReference("part5Basic0${position + 1}")
                }
                R.id.itemPart5Intermediate -> {
                    tvLevel.text = getString(R.string.part5Intermediate)
                    dataQuestion = getReference("part5Intermediate0${position + 1}")
                }
                R.id.itemPart5Advanced -> {
                    tvLevel.text = getString(R.string.part5Advanced)
                    dataQuestion = getReference("part5Advanced0${position + 1}")
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
                R.id.itemWordStudy -> {
                    testTitleList = intent.getParcelableArrayListExtra(WordListFragment.ARG_LIST_TEST_TITLE)
                    tvLevel.text = testTitleList[position].testTitle
                    initGrammarDetailFragment()
                }
            }
        }

        if (level != R.id.itemGrammar && level != R.id.itemWordStudy) {
            dataQuestion.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(dataPractice: DatabaseError) {
                    dismissProgressDialog()
                }

                override fun onDataChange(dataPractice: DataSnapshot) {
                    dismissProgressDialog()
                    for (i in dataPractice.children) {
                        val question = i.getValue(QuestionDetailItem::class.java)
                        question?.let {
                            questionList.add(it)
                        }
                    }
                    questionDetailPager?.adapter = QuestionDetailAdapter(supportFragmentManager, questionList)
                }
            })
        }
    }

    private fun initGrammarDetailFragment() {
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
            replace(
                R.id.frListQuestions,
                if (level == R.id.itemGrammar) GrammarDetailFragment() else WordStudyFragment()
            )
            commit()
        }
        frListQuestions.visibility = View.VISIBLE
        with(View.GONE) {
            chronometer.visibility = this
            btnListQuestions.visibility = this
        }
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
            setCancelable(false)
        }
    }

    fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun notifyNetworkStatus() {
        if (!isNetworkAvailable()) {
            Handler().postDelayed({
                dismissProgressDialog()
                Toast.makeText(this, getString(R.string.networkNotification), Toast.LENGTH_LONG).show()
            }, 5000)
        }
    }
}
