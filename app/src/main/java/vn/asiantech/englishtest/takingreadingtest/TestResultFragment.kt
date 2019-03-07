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
import android.content.Context
import android.util.Log

class TestResultFragment : Fragment(), View.OnClickListener {
    private var level : Int ? = null
    private var position : Int ? = null
    val objectTimeAndScore = arrayListOf<ListTimeAndScore>()

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
        level = activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_LEVEL, 0)
        position = activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_POSITION, 0)
        objectTimeAndScore.add(
            ListTimeAndScore(
                (activity as TakingReadingTestActivity).chronometer.text.toString(),
                StringBuilder().append((activity as TakingReadingTestActivity).score.toString()).append("/40").toString()
            )
        )
        val json = Gson().toJson(objectTimeAndScore)
        val preferences = activity?.getSharedPreferences("dinh", Context.MODE_PRIVATE)
        val editor = preferences?.edit()
        editor?.putString("keyjson$level$position", json)
        editor?.apply()
        val sadsa = preferences?.getString("keyjson$level$position","")
        Log.i("xxx", sadsa)
    }
}
