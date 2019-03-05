package vn.asiantech.englishtest.takingreadingtest

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import kotlinx.android.synthetic.main.fragment_question_detail.*
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
        tvCorrectAnswer.text = StringBuilder().append((activity as TakingReadingTestActivity).score.toString()).append("/40")
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnReview -> {
                activity?.apply {
                    frListQuestions?.visibility = View.GONE
                    (activity as TakingReadingTestActivity).questionList.forEach { listQuestionDetailItem ->
                        when {
                            listQuestionDetailItem.correctanswer == tvAnswerA.text -> tvAnswerA.setBackgroundColor(Color.GREEN)
                            listQuestionDetailItem.correctanswer == tvAnswerB.text -> tvAnswerB.setBackgroundColor(Color.GREEN)
                            listQuestionDetailItem.correctanswer == tvAnswerC.text -> tvAnswerC.setBackgroundColor(Color.GREEN)
                            listQuestionDetailItem.correctanswer == tvAnswerD.text -> tvAnswerD.setBackgroundColor(Color.GREEN)
                        }
                        if(listQuestionDetailItem.myAnswer != listQuestionDetailItem.correctanswer) {
                            when {
                                listQuestionDetailItem.myAnswer == listQuestionDetailItem.answerA -> tvAnswerA.setBackgroundColor(Color.CYAN)
                                listQuestionDetailItem.myAnswer == listQuestionDetailItem.answerB -> tvAnswerB.setBackgroundColor(Color.CYAN)
                                listQuestionDetailItem.myAnswer == listQuestionDetailItem.answerC -> tvAnswerC.setBackgroundColor(Color.CYAN)
                                listQuestionDetailItem.myAnswer == listQuestionDetailItem.answerD -> tvAnswerD.setBackgroundColor(Color.CYAN)
                            }
                        }
                    }
                    questionDetailPager?.currentItem = 0
                }
            }
            R.id.btnExit -> {
                activity?.finish()
            }
        }
    }
}
