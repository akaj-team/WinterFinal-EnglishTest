package vn.asiantech.englishtest.takingreadingtest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import kotlinx.android.synthetic.main.fragment_test_result.*
import vn.asiantech.englishtest.R


class TestResultFragment : Fragment(), View.OnClickListener {

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
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnReview -> {
                activity?.apply {
                    frListQuestions?.visibility = View.GONE
                    questionDetailPager?.currentItem = 0
                }
            }
            R.id.btnExit -> {
                activity?.finish()
            }
        }
    }
}
