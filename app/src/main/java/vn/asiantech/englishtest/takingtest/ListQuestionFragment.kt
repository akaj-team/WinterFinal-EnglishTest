package vn.asiantech.englishtest.takingtest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import kotlinx.android.synthetic.main.fragment_list_questions.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.listtest.TestListFragment
import vn.asiantech.englishtest.model.QuestionNumberItem

class ListQuestionFragment : Fragment(), ListQuestionAdapter.OnClickQuestionNumber {
    private var listQuestionItems: MutableList<QuestionNumberItem> = arrayListOf()
    private var level: Int? = null
    private var listAdapter: ListQuestionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        level = activity?.intent?.getIntExtra(TestListFragment.ARG_LEVEL, 0)
        return inflater.inflate(R.layout.fragment_list_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        setListQuestionNumber()
        onClickSubmit()
    }

    override fun onClickQuestionNumber(position: Int) {
        (activity as TakingTestActivity).apply {
            frListQuestions?.visibility = View.GONE
            questionDetailPager?.currentItem = position
        }
    }

    private fun initRecycleView() = recycleViewListQuestions.apply {
        setHasFixedSize(true)
        layoutManager = GridLayoutManager(activity, 5)
        listAdapter = ListQuestionAdapter(listQuestionItems, this@ListQuestionFragment)
        adapter = listAdapter
    }

    private fun setListQuestionNumber() {
        for (i in 0 until (activity as TakingTestActivity).questionList.size) {
            (listQuestionItems as ArrayList<QuestionNumberItem>).add(
                QuestionNumberItem(
                    when (level) {
                        R.id.itemPart1 -> 1 + i
                        R.id.itemPart2 -> 11 + i
                        R.id.itemPart3 -> 41 + i
                        R.id.itemPart4 -> 71 + i
                        R.id.itemPart6 -> 141 + i
                        R.id.itemPart7 -> 147 + i
                        else -> 101 + i
                    }
                )
            )
        }
    }

    private fun onClickSubmit() = btnSubmit.setOnClickListener {
        fragmentManager?.beginTransaction()?.apply {
            setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
            replace(R.id.frListQuestions, TestResultFragment())
            commit()
        }
        (activity as TakingTestActivity).apply {
            chronometer?.stop()
            with(View.GONE) {
                chronometer?.visibility = this
                btnListQuestions?.visibility = this
            }
            questionList.forEach { listQuestionDetailItem ->
                if (listQuestionDetailItem.correctAnswer == listQuestionDetailItem.myAnswer) {
                    score += 1
                }
            }
            mediaPlayer?.apply {
                stop()
                release()
            }
            mediaPlayer = null
        }
    }
}
