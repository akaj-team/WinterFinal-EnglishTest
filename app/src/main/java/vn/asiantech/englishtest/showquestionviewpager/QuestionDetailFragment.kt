package vn.asiantech.englishtest.showquestionviewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_question_detail.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ListQuestionDetailItem

class QuestionDetailFragment : Fragment() {
    private var data : ListQuestionDetailItem ?= null
    private var position = 0
    companion object {
        private const val ARG_POSITION = "arg_position"
        private const val ARG_DATA = "arg_data"
        fun getInstance(position: Int, question: ListQuestionDetailItem): QuestionDetailFragment = QuestionDetailFragment().apply {
            val bundle = Bundle().apply {
                putInt(ARG_POSITION, position)
                putSerializable(ARG_DATA,question)
            }
            arguments = bundle
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            position = it.getInt(ARG_POSITION)
             data = it.getSerializable(ARG_DATA) as ListQuestionDetailItem
        }

        return inflater.inflate(R.layout.fragment_question_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionDetail.text = data?.question
        answerA.text = data?.answerA
        answerB.text = data?.answerB
        answerC.text = data?.answerC
        answerD.text = data?.answerD
    }
}
