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
import vn.asiantech.englishtest.takingreadingtest.TestResultFragment

class ListReadingTestFragment : Fragment(), ListReadingTestAdapter.OnItemClickListener {
    private var listReadingTestItems: ArrayList<ListReadingTestItem> = arrayListOf()
    private var testAdapter: ListReadingTestAdapter? = null
    private var level: Int? = null

    companion object {
        const val ARG_LEVEL = "arg_level"
        const val ARG_POSITION = "position"
        const val REQUEST_CODE = 1001

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
        setData()
    }

    override fun onClick(position: Int) {
        startActivityForResult(
            Intent(activity, TakingReadingTestActivity::class.java)
                .putExtra(ARG_POSITION, position)
                .putExtra(ARG_LEVEL, arguments?.getInt(ARG_LEVEL)), REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == TestResultFragment.RESULT_OK) {
                data?.apply {
                    listReadingTestItems[getIntExtra(ListReadingTestFragment.ARG_POSITION, -1)].timeDisplay =
                        getStringExtra(TestResultFragment.KEY_TIME)
                    listReadingTestItems[getIntExtra(ListReadingTestFragment.ARG_POSITION, -1)].scoreDisplay =
                        getStringExtra(TestResultFragment.KEY_SCORE).plus(getString(R.string.totalScore))
                    testAdapter?.notifyDataSetChanged()
                }
            }
        }
    }

    private fun initRecycleView() {
        recycleViewListReadingTests.apply {
            layoutManager = LinearLayoutManager(activity)
            testAdapter = ListReadingTestAdapter(listReadingTestItems, this@ListReadingTestFragment)
            adapter = testAdapter
        }
    }

    private fun setData() {
        val maxTestNumber = 10
        for (i in 0 until maxTestNumber) {
            listReadingTestItems.add(
                ListReadingTestItem(
                    getString(R.string.practice).plus(" ").plus(i + 1),
                    getString(R.string.timeDefault), getString(R.string.scoreDefault)
                )
            )
        }
        val preferences = activity?.getSharedPreferences(getString(R.string.fileName), Context.MODE_PRIVATE)
        val json = preferences?.getString("$level", null)
        if (json != null) {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val listTimeandScore = gson.fromJson(json, Array<ListReadingTestItem>::class.java).toList()
            for (testPosition in listReadingTestItems) {
                for (timeAndScorePosition in listTimeandScore) {
                    if (testPosition.testNumber == timeAndScorePosition.testNumber) {
                        testPosition.scoreDisplay = timeAndScorePosition.scoreDisplay
                        testPosition.timeDisplay = timeAndScorePosition.timeDisplay
                    }
                }
            }
        }
    }
}
