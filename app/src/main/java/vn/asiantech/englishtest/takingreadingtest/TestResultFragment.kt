package vn.asiantech.englishtest.takingreadingtest

import android.content.Context
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
        val preferences = activity?.getSharedPreferences(resources.getString(R.string.fileName), Context.MODE_PRIVATE)
        level = activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_LEVEL, 0)
        position = activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_POSITION, 0)
        val a = preferences?.getString("$level", "")
        val gson = GsonBuilder().setPrettyPrinting().create()
        val listTimeandScore =
            gson.fromJson(a, Array<ListReadingTestItem>::class.java)?.toList()?.toMutableList() ?: arrayListOf()

        listTimeandScore.add(
            ListReadingTestItem(
                getString(R.string.practice).plus(" ").plus(position?.let { it + 1 }),
                (activity as TakingReadingTestActivity).chronometer.text.toString(),
                (activity as TakingReadingTestActivity).score.toString().plus(resources.getString(R.string.totalScore))
            )
        )
        val json = Gson().toJson(listTimeandScore)
        preferences?.edit()?.apply {
            putString("$level", json)
            apply()
        }
    }
}
