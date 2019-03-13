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
import vn.asiantech.englishtest.listreadingtest.ListReadingTestFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_LEVEL, 0)) {
            R.id.itemPart6, R.id.itemPart7 -> {
                tvQuestionContent.visibility = View.VISIBLE
                tvQuestionTitle.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }
        selectedAnswer()
        data?.let {
            with(it) {
                tvQuestionTitle.text = questionTitle
                rbAnswerA.text = answerA
                rbAnswerB.text = answerB
                rbAnswerC.text = answerC
                rbAnswerD.text = answerD
                tvQuestionContent.text = questionContent
            }

            if ((activity as TakingReadingTestActivity).review) {
                with(it) {
                    if (myAnswer != correctAnswer) {
                        when (correctAnswer) {
                            answerA -> rbAnswerA.setBackgroundColor(if (myAnswer.isBlank()) Color.YELLOW else Color.GREEN)
                            answerB -> rbAnswerB.setBackgroundColor(if (myAnswer.isBlank()) Color.YELLOW else Color.GREEN)
                            answerC -> rbAnswerC.setBackgroundColor(if (myAnswer.isBlank()) Color.YELLOW else Color.GREEN)
                            answerD -> rbAnswerD.setBackgroundColor(if (myAnswer.isBlank()) Color.YELLOW else Color.GREEN)
                        }
                        when (myAnswer) {
                            answerA -> rbAnswerA.setBackgroundColor(Color.RED)
                            answerB -> rbAnswerB.setBackgroundColor(Color.RED)
                            answerC -> rbAnswerC.setBackgroundColor(Color.RED)
                            answerD -> rbAnswerD.setBackgroundColor(Color.RED)
                        }
                    }
                    rbAnswerA.isClickable = false
                    rbAnswerB.isClickable = false
                    rbAnswerC.isClickable = false
                    rbAnswerD.isClickable = false
                }
            }
        }
    }

    private fun selectedAnswer() {
        rgAnswer.setOnCheckedChangeListener { _, _ ->
            when {
                rbAnswerA.isChecked -> {
                    data?.apply {
                        myAnswer = answerA
                    }
                }
                rbAnswerB.isChecked -> {
                    data?.apply {
                        myAnswer = answerB
                    }
                }
                rbAnswerC.isChecked -> {
                    data?.apply {
                        myAnswer = answerC
                    }
                }
                rbAnswerD.isChecked -> {
                    data?.apply {
                        myAnswer = answerD
                    }
                }
            }
        }
    }
}
