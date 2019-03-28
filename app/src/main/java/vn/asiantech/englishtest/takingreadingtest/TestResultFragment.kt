package vn.asiantech.englishtest.takingreadingtest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import kotlinx.android.synthetic.main.fragment_test_result.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.listreadingtest.ListReadingTestFragment
import vn.asiantech.englishtest.model.ListReadingTestItem

class TestResultFragment : Fragment(), View.OnClickListener {
    private var level: Int? = null
    private var position: Int? = null

    companion object {
        const val KEY_TIME = "key_time"
        const val KEY_SCORE = "key_score"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnReview.setOnClickListener(this)
        btnExit.setOnClickListener(this)
        (activity as TakingReadingTestActivity).apply {
            tvDurationTime.text = chronometer.text.toString()
            tvCorrectAnswer.text = StringBuilder().append(score).append(getString(R.string.totalScore))
        }

        addTimeAndScore()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnReview -> {
                (activity as TakingReadingTestActivity).review = true
                activity?.apply {
                    frListQuestions?.visibility = View.GONE
                    questionDetailPager?.apply {
                        adapter?.notifyDataSetChanged()
                        currentItem = 0
                    }
                }
            }
            R.id.btnExit -> {
                activity?.apply {
                    setResult(
                        Activity.RESULT_OK, Intent()
                            .putExtra(KEY_TIME, tvDurationTime.text.toString())
                            .putExtra(KEY_SCORE, (activity as TakingReadingTestActivity).score.toString())
                            .putExtra(
                                ListReadingTestFragment.ARG_POSITION,
                                activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_POSITION, 0)
                            )
                    )
                    finish()
                }
            }
        }
    }

    private fun addTimeAndScore() {
        val preferences = activity?.getSharedPreferences(getString(R.string.fileName), Context.MODE_PRIVATE)
        activity?.intent?.apply {
            level = getIntExtra(ListReadingTestFragment.ARG_LEVEL, -1)
            position = getIntExtra(ListReadingTestFragment.ARG_POSITION, -1)
        }
        val dataTimeAndScore = preferences?.getString("$level", "")
        val gson = GsonBuilder().setPrettyPrinting().create()
        val listTimeandScore =
            gson.fromJson(dataTimeAndScore, Array<ListReadingTestItem>::class.java)?.toList()?.toMutableList()
                ?: arrayListOf()
        listTimeandScore.add(
            ListReadingTestItem(
                "${getString(R.string.practice)} ${position?.let { it + 1 }}",
                (activity as TakingReadingTestActivity).chronometer.text.toString(),
                (activity as TakingReadingTestActivity).score.toString()
            )
        )
        val json = Gson().toJson(listTimeandScore)
        preferences?.edit()?.apply {
            putString("$level", json)
            apply()
        }
    }
}
