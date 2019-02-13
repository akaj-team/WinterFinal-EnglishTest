package vn.asiantech.englishtest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_reading_test.*
import vn.asiantech.englishtest.model.ListReadingTestItems

class ListReadingTestFragment : Fragment() {

    private lateinit var mListReadingTestItems: List<ListReadingTestItems>
    private lateinit var mAdapter: ListReadingTestAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_reading_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
    }

    fun initRecycleView() {
        recycleViewListReadingTests.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recycleViewListReadingTests.layoutManager = linearLayoutManager
        setData()
        mAdapter = ListReadingTestAdapter(mListReadingTestItems)
        recycleViewListReadingTests.adapter = mAdapter
    }

    private fun setData() {
        val maxTestNumber = 20
        mListReadingTestItems = ArrayList()
        for (i in 0 until maxTestNumber) {
            (mListReadingTestItems as ArrayList<ListReadingTestItems>).add(
                ListReadingTestItems(
                    getString(R.string.practice) + " ${i + 1}",
                    getString(R.string.time),
                    getString(R.string.timeDisplay),
                    getString(R.string.score),
                    40
                )
            )
        }
    }
}
