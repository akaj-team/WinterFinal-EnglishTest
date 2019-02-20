package vn.asiantech.englishtest.takingreadingtest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_questions.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ListQuestionItem

class ListQuestionFragment : Fragment() {
    private var mListQuestionItems: List<ListQuestionItem>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
    }

    private fun initRecycleView() {
        setData()
        recycleViewListQuestions.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 5)
            mListQuestionItems?.let {
                ListQuestionAdapter(it)
            }
        }
    }

    private fun setData() {
        //TODO
        val maxQuestionNumber = 40
        mListQuestionItems = ArrayList()
        for (i in 0 until maxQuestionNumber) {
            (mListQuestionItems as ArrayList<ListQuestionItem>).add(ListQuestionItem(101 + i))
        }
    }
}
