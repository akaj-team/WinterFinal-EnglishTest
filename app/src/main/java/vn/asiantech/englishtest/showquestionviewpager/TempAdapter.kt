package vn.asiantech.englishtest.showquestionviewpager


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import vn.asiantech.englishtest.model.ListQuestionDetailItem

class TempAdapter(fm:FragmentManager, private var data:ArrayList<ListQuestionDetailItem>):FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        val question = data[position]
        return TempFragment.getInstance(position, question)
    }

    override fun getCount(): Int  = 4

}
