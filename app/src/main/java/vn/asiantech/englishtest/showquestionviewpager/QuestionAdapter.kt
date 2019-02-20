package vn.asiantech.englishtest.showquestionviewpager


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import vn.asiantech.englishtest.model.ListQuestionDetailItem

class QuestionAdapter(fm: FragmentManager, private var questionList: ArrayList<ListQuestionDetailItem>) :
    FragmentStatePagerAdapter(fm) {
    companion object {
        const val TOTALQUESTION = 40
    }

    override fun getItem(position: Int): Fragment {
        val question = questionList[position]
        return QuestionDetailFragment.getInstance(position, question)
    }

    override fun getCount(): Int = TOTALQUESTION
}