package vn.asiantech.englishtest.showlevelenglishviewpager


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import vn.asiantech.englishtest.ListReadingTestFragment
import vn.asiantech.englishtest.model.ListReadingTestItems

class LevelAdapter(fm: FragmentManager, private var questionList: ArrayList<ListReadingTestItems>) :
    FragmentStatePagerAdapter(fm) {
    companion object {
        const val TOTALQUESTION = 3
    }

    override fun getItem(position: Int): Fragment {
        val question = questionList[position]
        return ListReadingTestFragment.getInstance(position)
    }

    override fun getCount(): Int = TOTALQUESTION
}
