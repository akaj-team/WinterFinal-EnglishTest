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
         const val ARG_POSITION = "arg_position"
         const val ARG_DATA = "arg_data"
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
            with((activity as TakingReadingTestActivity).questionList[position]) {
                myAnswer = answerA
            }
            tvAnswerA.setBackgroundColor(Color.CYAN)
            tvAnswerB.setBackgroundColor(Color.WHITE)
            tvAnswerC.setBackgroundColor(Color.WHITE)
            tvAnswerD.setBackgroundColor(Color.WHITE)
        }
        tvAnswerB.setOnClickListener {
            data?.myAnswer = (activity as TakingReadingTestActivity).questionList[position].answerB
            with((activity as TakingReadingTestActivity).questionList[position]) {
                myAnswer = answerB
            }
            tvAnswerA.setBackgroundColor(Color.WHITE)
            tvAnswerB.setBackgroundColor(Color.CYAN)
            tvAnswerC.setBackgroundColor(Color.WHITE)
            tvAnswerD.setBackgroundColor(Color.WHITE)
        }
        tvAnswerC.setOnClickListener {
            data?.myAnswer = (activity as TakingReadingTestActivity).questionList[position].answerC
            with((activity as TakingReadingTestActivity).questionList[position]) {
                myAnswer = answerC
            }
            tvAnswerA.setBackgroundColor(Color.WHITE)
            tvAnswerB.setBackgroundColor(Color.WHITE)
            tvAnswerC.setBackgroundColor(Color.CYAN)
            tvAnswerD.setBackgroundColor(Color.WHITE)
        }
        tvAnswerD.setOnClickListener {
            data?.myAnswer = (activity as TakingReadingTestActivity).questionList[position].answerD
            with((activity as TakingReadingTestActivity).questionList[position]) {
                myAnswer = answerD
            }
            tvAnswerA.setBackgroundColor(Color.WHITE)
            tvAnswerB.setBackgroundColor(Color.WHITE)
            tvAnswerC.setBackgroundColor(Color.WHITE)
            tvAnswerD.setBackgroundColor(Color.CYAN)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedAnswer()
        data?.let {
            with(it) {
                tvQuestion.text = question
                tvAnswerA.text = answerA
                tvAnswerB.text = answerB
                tvAnswerC.text = answerC
                tvAnswerD.text = answerD
                when {
                    it.myAnswer == answerA -> {
                        tvAnswerA.setBackgroundColor(Color.CYAN)
                    }
                    it.myAnswer == answerB -> {
                        tvAnswerB.setBackgroundColor(Color.CYAN)
                    }
                    it.myAnswer == answerC -> {
                        tvAnswerC.setBackgroundColor(Color.CYAN)
                    }
                    it.myAnswer == answerD -> {
                        tvAnswerD.setBackgroundColor(Color.CYAN)
                    }
                }
            }
        }

    }
}
