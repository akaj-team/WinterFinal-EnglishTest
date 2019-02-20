package vn.asiantech.englishtest.listreadingtest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_reading_test.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ListReadingTestItems

class ListReadingTestFragment : Fragment() {

    private var mListReadingTestItems: List<ListReadingTestItems>? = null
    private var mAdapter: ListReadingTestAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_reading_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
    }

    private fun initRecycleView() {
        recycleViewListReadingTests.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity)
        recycleViewListReadingTests.layoutManager = linearLayoutManager
        setData()
        mAdapter = mListReadingTestItems?.let { ListReadingTestAdapter(it) }
        recycleViewListReadingTests.adapter = mAdapter
    }

    private fun setData() {
        //TODO
        val maxTestNumber = 20
        mListReadingTestItems = ArrayList()
        for (i in 0 until maxTestNumber) {
            (mListReadingTestItems as ArrayList<ListReadingTestItems>).add(
                ListReadingTestItems(
                    getString(R.string.practice) + " ${i + 1}",
                    getString(R.string.time),
                    40
                )
            )
        }
    }
}
