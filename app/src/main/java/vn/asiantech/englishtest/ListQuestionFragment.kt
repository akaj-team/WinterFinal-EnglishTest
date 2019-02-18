package vn.asiantech.englishtest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_questions.*
import kotlinx.android.synthetic.main.fragment_list_questions.view.*
import vn.asiantech.englishtest.model.ListQuestionItems

class ListQuestionFragment : Fragment() {
    private var mListQuestionItems: List<ListQuestionItems>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_list_questions, container, false)
        onClickSubmit(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
    }

    private fun initRecycleView() {
        recycleViewListQuestions.setHasFixedSize(true)
        recycleViewListQuestions.layoutManager = GridLayoutManager(activity, 5)
        setData()
        recycleViewListQuestions.adapter = mListQuestionItems?.let { ListQuestionAdapter(it) }
    }

    private fun setData() {
        val maxQuestionNumber = 40
        mListQuestionItems = ArrayList()
        for (i in 0 until maxQuestionNumber) {
            (mListQuestionItems as ArrayList<ListQuestionItems>).add(ListQuestionItems(101 + i))
        }
    }

    private fun onClickSubmit(view: View) {
        view.btnSubmit.setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                ?.replace(R.id.fragmentQuestionsDisplay, TestResultFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}
