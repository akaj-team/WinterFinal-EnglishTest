package vn.asiantech.englishtest.takingreadingtest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import kotlinx.android.synthetic.main.fragment_list_questions.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.TestResultFragment
import vn.asiantech.englishtest.model.ListQuestionItem

class ListQuestionFragment : Fragment(), ListQuestionAdapter.OnItemClickQuestionNumber {

    private var listQuestionItems: List<ListQuestionItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_list_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        onClickSubmit()
    }

    private fun initRecycleView() {
        setListQuestionNumber()
        recycleViewListQuestions.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 5)
            adapter = ListQuestionAdapter(listQuestionItems, this@ListQuestionFragment)
        }
    }

    private fun setListQuestionNumber() {
        val maxQuestionNumber = 40
        for (i in 0 until maxQuestionNumber) {
            (listQuestionItems as ArrayList<ListQuestionItem>).add(ListQuestionItem(101 + i))
        }
    }

    private fun onClickSubmit() {
        btnSubmit.setOnClickListener {
            activity?.chronometer?.stop()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frListQuestions, TestResultFragment())
                commit()
            }
        }
    }

    override fun onClickQuestionNumber(position: Int) {
        activity?.frListQuestions?.visibility = View.GONE
        (activity as? TakingReadingTestActivity)?.questionDetailPager?.currentItem = position
    }
}
