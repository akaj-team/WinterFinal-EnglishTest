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

    private var mListReadingTestItems: List<ListReadingTestItems>? = null
    private var mAdapter: ListReadingTestAdapter? = null

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
        mAdapter = mListReadingTestItems?.let { ListReadingTestAdapter(it) }
        recycleViewListReadingTests.adapter = mAdapter
    }

    private fun setData() {
        //TODO
    }
}
