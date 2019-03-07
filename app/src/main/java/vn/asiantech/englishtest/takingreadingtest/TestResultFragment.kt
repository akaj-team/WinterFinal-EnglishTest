package vn.asiantech.englishtest.takingreadingtest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import kotlinx.android.synthetic.main.fragment_test_result.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.listreadingtest.ListReadingTestFragment
import vn.asiantech.englishtest.model.ListTimeAndScore
import com.google.gson.Gson
import android.content.SharedPreferences
import android.content.Context


class TestResultFragment : Fragment(), View.OnClickListener {
    var sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
    private var level : Int ? = null
    private var position : Int ? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnReview.setOnClickListener(this)
        btnExit.setOnClickListener(this)
        tvDurationTime.text = (activity as TakingReadingTestActivity).chronometer.text.toString()
        tvCorrectAnswer.text =
            StringBuilder().append((activity as TakingReadingTestActivity).score.toString()).append("/40")
        addTimeAndScore()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnReview -> {
                (activity as TakingReadingTestActivity).review = true
                activity?.apply {
                    frListQuestions?.visibility = View.GONE
                    questionDetailPager.adapter?.notifyDataSetChanged()
                    questionDetailPager?.currentItem = 0
                }
            }
            R.id.btnExit -> {
                activity?.finish()
            }
        }
    }

    private fun addTimeAndScore() {
        val objectTimeAndScore = arrayListOf<ListTimeAndScore>()
        level = activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_LEVEL, 0)
        position = activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_POSITION, 0)
        objectTimeAndScore.add(
            ListTimeAndScore(
                (activity as TakingReadingTestActivity).chronometer.text.toString(),
                StringBuilder().append((activity as TakingReadingTestActivity).score.toString()).append("/40").toString()
            )
        )
        val json = Gson().toJson(objectTimeAndScore)
        val editor : SharedPreferences.Editor ?= sharedPref?.edit()
            editor?.putString(json, "keyjson$level$position")
            editor?.apply()
    }
}
