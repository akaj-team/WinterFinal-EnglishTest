package vn.asiantech.englishtest.listreadingtest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_list_test.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ListReadingTestItem
import vn.asiantech.englishtest.takingreadingtest.TakingReadingTestActivity


class ListReadingTestFragment : Fragment(), ListReadingTestAdapter.OnItemClickListener {
    private var listReadingTestItems: ArrayList<ListReadingTestItem> = arrayListOf()
    private var level: Int? = null
    private var position: Int? = null

    companion object {
        const val ARG_LEVEL = "arg_level"
        const val ARG_POSITION = "position"
        fun getInstance(level: Int): ListReadingTestFragment =
            ListReadingTestFragment().apply {
                val bundle = Bundle().apply {
                    putInt(ARG_LEVEL, level)
                }
                arguments = bundle
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            level = it.getInt(ARG_LEVEL)
        }
        return inflater.inflate(R.layout.fragment_list_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
    }

    override fun onClick(position: Int) {
        startActivity(
            Intent(activity, TakingReadingTestActivity::class.java)
                .putExtra(ARG_POSITION, position)
                .putExtra(ARG_LEVEL, arguments?.getInt(ARG_LEVEL))
        )
    }

    private fun initRecycleView() {
        setData()
        recycleViewListReadingTests.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = ListReadingTestAdapter(listReadingTestItems, this@ListReadingTestFragment)
        }
    }

    private fun setData() {
        val maxTestNumber = 10
        for (i in 0 until maxTestNumber) {
            listReadingTestItems.add(
                ListReadingTestItem(
                    getString(vn.asiantech.englishtest.R.string.practice).plus(" ").plus(i + 1),
                    "00:00", "0/40"
                )
            )
        }
        position = activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_POSITION, 0)
        val preferences = activity?.getSharedPreferences("timescore", Context.MODE_PRIVATE)
        val json = preferences?.getString("keyjson$level", null)
        if (json != null) {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val listTimeandScore = gson.fromJson(json, Array<ListReadingTestItem>::class.java).toList()
            for (i in 0..(listReadingTestItems.size - 1)) {
                for (a in 0..(listTimeandScore.size - 1)) {
                    if (listReadingTestItems[i].testNumber == listTimeandScore[a].testNumber) {
                        listReadingTestItems[i].scoreDisplay = listTimeandScore[a].scoreDisplay
                        listReadingTestItems[i].timeDisplay = listTimeandScore[a].timeDisplay
                    }
                }
            }
        }
    }
}
