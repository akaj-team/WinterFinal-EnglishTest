package vn.asiantech.englishtest.listreadingtest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_reading_test.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ListReadingTestItem

class ListReadingTestFragment : Fragment() {

    private var listReadingTestItems: List<ListReadingTestItem> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_reading_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
    }

    private fun initRecycleView() {
        setData()
        recycleViewListReadingTests.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = ListReadingTestAdapter(listReadingTestItems)
        }
    }

    private fun setData() {
        //TODO
        val maxTestNumber = 20
        for (i in 0 until maxTestNumber) {
            (listReadingTestItems as ArrayList<ListReadingTestItem>).add(
                ListReadingTestItem(
                    getString(R.string.practice) + " ${i + 1}",
                    getString(R.string.time),
                    40
                )
            )
        }
    }
}
