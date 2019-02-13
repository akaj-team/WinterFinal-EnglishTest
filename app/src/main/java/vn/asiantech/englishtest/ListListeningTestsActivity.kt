package vn.asiantech.englishtest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_listening_tests2.*
import vn.asiantech.englishtest.model.ListListeningTestItems

class ListListeningTestsActivity : AppCompatActivity() {

    private lateinit var mListListeningTestItems: List<ListListeningTestItems>
    private lateinit var mAdapter: ListListeningTestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_listening_tests2)
        initView()
    }

    fun initView() {
        recycleViewListListeningTests.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(application, LinearLayoutManager.VERTICAL, false)
        recycleViewListListeningTests.layoutManager = linearLayoutManager
        setData()
        mAdapter = ListListeningTestAdapter(mListListeningTestItems)
        recycleViewListListeningTests.adapter = mAdapter
    }

    private fun setData() {
        val maxTestNumber = 20
        mListListeningTestItems = ArrayList()
        for (i in 0 until maxTestNumber) {
            (mListListeningTestItems as ArrayList<ListListeningTestItems>).add(
                ListListeningTestItems(
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
