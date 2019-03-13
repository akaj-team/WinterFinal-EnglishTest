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

class TestResultFragment : Fragment(), View.OnClickListener {

    private var level: Int? = null
    private var questionNumber: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        (activity as TakingReadingTestActivity).apply {
            level = intent.getIntExtra(ListReadingTestFragment.ARG_LEVEL, 0)
            questionNumber = questionList.size
        }
        return inflater.inflate(R.layout.fragment_test_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnReview.setOnClickListener(this)
        btnExit.setOnClickListener(this)
        (activity as TakingReadingTestActivity).apply {
            tvDurationTime.text = chronometer.text.toString()
            tvCorrectAnswer.text = StringBuilder().append(score.toString())
                .append(
                    if (level == R.id.itemPart6) "/$questionNumber"
                    else if (level == R.id.itemPart7) "/$questionNumber"
                    else "/$questionNumber"
                )
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnReview -> {
                (activity as TakingReadingTestActivity).review = true
                activity?.apply {
                    frListQuestions?.visibility = View.GONE
                    questionDetailPager.apply {
                        adapter?.notifyDataSetChanged()
                        currentItem = 0
                    }
                }
            }
            R.id.btnExit -> {
                activity?.finish()
            }
        }
    }
}
