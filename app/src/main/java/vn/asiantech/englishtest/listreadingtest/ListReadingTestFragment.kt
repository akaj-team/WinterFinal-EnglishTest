package vn.asiantech.englishtest.listreadingtest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_list_test.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ListReadingTestItem
import vn.asiantech.englishtest.takingreadingtest.TakingReadingTestActivity
import vn.asiantech.englishtest.model.ListTimeAndScore




class ListReadingTestFragment : Fragment(), ListReadingTestAdapter.OnItemClickListener {
    private var listReadingTestItems: MutableList<ListReadingTestItem>? = null
    private var listTimeandScore = arrayListOf<ListTimeAndScore>()
    private var sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
    private var level : Int  ?= null
    private var position : Int  ?= null
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
            adapter = listReadingTestItems?.let { ListReadingTestAdapter(it, this@ListReadingTestFragment) }
        }
    }

    private fun setData() {
        //TODO("Not implemented")
        /*level = activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_LEVEL, 0)
        position = activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_POSITION, 0)
        val json : String = sharedPref?.getInt("keyjson$level$position", 0).toString()
        val gson = Gson()
        listTimeandScore = gson.fromJson(json , MutableList()<ListTimeAndScore>::class.java)*/
        val maxTestNumber = 10
        listReadingTestItems = ArrayList()
        for (i in 0 until maxTestNumber) {
            (listReadingTestItems as ArrayList<ListReadingTestItem>).add(
                ListReadingTestItem(
                    getString(vn.asiantech.englishtest.R.string.practice) + " ${i + 1}",
                   /* listTimeandScore[position!!].time,
                    listTimeandScore[position!!].score*/
                "dsadsa","dsa"
                )
            )
        }
    }
}
