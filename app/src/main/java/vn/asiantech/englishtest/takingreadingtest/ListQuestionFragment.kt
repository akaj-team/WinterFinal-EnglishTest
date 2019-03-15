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
import vn.asiantech.englishtest.listreadingtest.ListReadingTestFragment
import vn.asiantech.englishtest.model.ListQuestionItem

class ListQuestionFragment : Fragment(), ListQuestionAdapter.OnItemClickQuestionNumber {
    private var listQuestionItems: MutableList<ListQuestionItem> = arrayListOf()
    private var level: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        level = activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_LEVEL, 0)
        return inflater.inflate(R.layout.fragment_list_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        onClickSubmit()
    }

    override fun onClickQuestionNumber(position: Int) {
        activity?.apply {
            frListQuestions?.visibility = View.GONE
            questionDetailPager?.currentItem = position
        }
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
        for (i in 0 until (activity as TakingReadingTestActivity).questionList.size) {
            (listQuestionItems as ArrayList<ListQuestionItem>).add(
                ListQuestionItem(
                    if (level == R.id.itemPart6) 141 + i
                    else if (level == R.id.itemPart7) 153 + i
                    else 101 + i
                )
            )
        }
    }

    private fun onClickSubmit() {
        btnSubmit.setOnClickListener {
            activity?.chronometer?.stop()
            fragmentManager?.beginTransaction()?.apply {
                setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                replace(R.id.frListQuestions, TestResultFragment())
                commit()
            }
            activity?.apply {
                chronometer?.visibility = View.GONE
                btnListQuestions?.visibility = View.GONE
            }
            (activity as TakingReadingTestActivity).questionList.forEach { listQuestionDetailItem ->
                if (listQuestionDetailItem.correctAnswer == listQuestionDetailItem.myAnswer) {
                    (activity as TakingReadingTestActivity).score += 1
                }
            }
        }
    }
}
