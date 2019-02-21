package vn.asiantech.englishtest.listreadingtest

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_level.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ListReadingTestItem
import vn.asiantech.englishtest.takingreadingtest.TakingReadingTestActivity

class ListAdvancedLevelFragment : Fragment(), ListReadingTestAdapter.OnItemClickListener {

    private var listReadingTestItems: List<ListReadingTestItem>? = null
    private val level = 3

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_level, container, false)
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
            adapter = listReadingTestItems?.let { ListReadingTestAdapter(it, this@ListAdvancedLevelFragment) }
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
                    getString(R.string.time),
                    40
                )
            )
        }
    }

    private fun sendIntent(position: Int) {
        val intent = Intent(activity, TakingReadingTestActivity::class.java)
        intent.putExtra("position", position)
        intent.putExtra("level", level)
        startActivity(intent)
    }

    override fun onClick(position: Int) {
        when (position) {
            in 0..9 -> {
                sendIntent(position)
            }
        }
    }
}
