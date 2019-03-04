package vn.asiantech.englishtest.questiondetailviewpager

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import kotlinx.android.synthetic.main.fragment_question_detail.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ListQuestionDetailItem
import vn.asiantech.englishtest.takingreadingtest.TakingReadingTestActivity

class QuestionDetailFragment : Fragment() {
    private var data: ListQuestionDetailItem? = null
    private var position = 0

    companion object {
        private const val ARG_POSITION = "arg_position"
        private const val ARG_DATA = "arg_data"
        fun getInstance(position: Int, question: ListQuestionDetailItem): QuestionDetailFragment =
            QuestionDetailFragment().apply {
                val bundle = Bundle().apply {
                    putInt(ARG_POSITION, position)
                    putParcelable(ARG_DATA, question)
                }
                arguments = bundle
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            data = it.getParcelable(ARG_DATA) as ListQuestionDetailItem
        }
        (activity as TakingReadingTestActivity).apply {
            progressDialog?.dismiss()
            chronometer.start()
        }
        return inflater.inflate(R.layout.fragment_question_detail, container, false)
    }

    private fun selectedAnswer() {
        tvAnswerA.setOnClickListener {
            data?.myAnswer = (activity as TakingReadingTestActivity).questionList[position].answerA
            (activity as TakingReadingTestActivity).questionList[position].myAnswer =
                (activity as TakingReadingTestActivity).questionList[position].answerA
            tvAnswerA.setBackgroundColor(Color.GREEN)
            tvAnswerB.setBackgroundColor(Color.WHITE)
            tvAnswerC.setBackgroundColor(Color.WHITE)
            tvAnswerD.setBackgroundColor(Color.WHITE)
        }
        tvAnswerB.setOnClickListener {
            data?.myAnswer = (activity as TakingReadingTestActivity).questionList[position].answerB
            (activity as TakingReadingTestActivity).questionList[position].myAnswer =
                (activity as TakingReadingTestActivity).questionList[position].answerB
            tvAnswerA.setBackgroundColor(Color.WHITE)
            tvAnswerB.setBackgroundColor(Color.GREEN)
            tvAnswerC.setBackgroundColor(Color.WHITE)
            tvAnswerD.setBackgroundColor(Color.WHITE)
        }
        tvAnswerC.setOnClickListener {
            data?.myAnswer = (activity as TakingReadingTestActivity).questionList[position].answerC
            (activity as TakingReadingTestActivity).questionList[position].myAnswer =
                (activity as TakingReadingTestActivity).questionList[position].answerC
            tvAnswerA.setBackgroundColor(Color.WHITE)
            tvAnswerB.setBackgroundColor(Color.WHITE)
            tvAnswerC.setBackgroundColor(Color.GREEN)
            tvAnswerD.setBackgroundColor(Color.WHITE)
        }
        tvAnswerD.setOnClickListener {
            data?.myAnswer = (activity as TakingReadingTestActivity).questionList[position].answerD
            (activity as TakingReadingTestActivity).questionList[position].myAnswer =
                (activity as TakingReadingTestActivity).questionList[position].answerD
            tvAnswerA.setBackgroundColor(Color.WHITE)
            tvAnswerB.setBackgroundColor(Color.WHITE)
            tvAnswerC.setBackgroundColor(Color.WHITE)
            tvAnswerD.setBackgroundColor(Color.GREEN)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data?.let {
            with(it) {
                tvQuestion.text = question
                tvAnswerA.text = answerA
                tvAnswerB.text = answerB
                tvAnswerC.text = answerC
                tvAnswerD.text = answerD
                when {
                    it.myAnswer == answerA -> {
                        tvAnswerA.setBackgroundColor(Color.GREEN)
                        tvAnswerB.setBackgroundColor(Color.WHITE)
                        tvAnswerC.setBackgroundColor(Color.WHITE)
                        tvAnswerD.setBackgroundColor(Color.WHITE)
                    }
                    it.myAnswer == answerB -> {
                        tvAnswerA.setBackgroundColor(Color.WHITE)
                        tvAnswerB.setBackgroundColor(Color.GREEN)
                        tvAnswerC.setBackgroundColor(Color.WHITE)
                        tvAnswerD.setBackgroundColor(Color.WHITE)
                    }
                    it.myAnswer == answerC -> {
                        tvAnswerA.setBackgroundColor(Color.WHITE)
                        tvAnswerB.setBackgroundColor(Color.WHITE)
                        tvAnswerC.setBackgroundColor(Color.GREEN)
                        tvAnswerD.setBackgroundColor(Color.WHITE)
                    }
                    it.myAnswer == answerD -> {
                        tvAnswerA.setBackgroundColor(Color.WHITE)
                        tvAnswerB.setBackgroundColor(Color.WHITE)
                        tvAnswerC.setBackgroundColor(Color.WHITE)
                        tvAnswerD.setBackgroundColor(Color.GREEN)
                    }
                }
            }
        }
        selectedAnswer()
    }
}
