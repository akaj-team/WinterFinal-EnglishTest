package vn.asiantech.englishtest.listreadingtest

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_test.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ListReadingTestItem
import vn.asiantech.englishtest.takingreadingtest.TakingReadingTestActivity

class ListReadingTestFragment : Fragment(), ListReadingTestAdapter.OnItemClickListener {
    private var listReadingTestItems: List<ListReadingTestItem>? = null

    companion object {
        private const val ARG_LEVEL = "arg_level"
        fun getInstance(level: Int): ListReadingTestFragment =
            ListReadingTestFragment().apply {
                val bundle = Bundle().apply {
                    putInt(ARG_LEVEL, level)
                }
                arguments = bundle
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_list_test, container, false)
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
            adapter = listReadingTestItems?.let { ListReadingTestAdapter(it, this@ListReadingTestFragment) }
        }
    }

    private fun setData() {
        //TODO
        val maxTestNumber = 10
        listReadingTestItems = ArrayList()
        for (i in 0 until maxTestNumber) {
            (listReadingTestItems as ArrayList<ListReadingTestItem>).add(
                ListReadingTestItem(
                    getString(R.string.practice) + " ${i + 1}",
                    getString(R.string.timeDisplay),
                    40
                )
            )
        }
    }

    override fun onClick(position: Int) {
        when (position) {
            in 0..9 -> {
                startActivity(
                    Intent(activity, TakingReadingTestActivity::class.java)
                        .putExtra(getString(R.string.position), position)
                        .putExtra(getString(R.string.level), arguments?.getInt(ARG_LEVEL))
                )
            }
        }
    }
}
