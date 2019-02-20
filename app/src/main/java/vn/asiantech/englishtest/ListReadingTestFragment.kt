package vn.asiantech.englishtest

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_reading_test.*
import vn.asiantech.englishtest.model.ListReadingTestItems


class ListReadingTestFragment : Fragment(), ListReadingTestAdapter.OnItemClickListener {

    private var mListReadingTestItems: List<ListReadingTestItems>? = null
    private var mAdapter: ListReadingTestAdapter? = null
    private var position = 0

    companion object {
        private const val ARG_POSITION = "arg_position"
        fun getInstance(position: Int): ListReadingTestFragment =
            ListReadingTestFragment().apply {
                val bundle = Bundle().apply {
                    putInt(ARG_POSITION, position)
                }
                arguments = bundle
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            position = it.getInt(ListReadingTestFragment.ARG_POSITION)
        }
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
        mAdapter = mListReadingTestItems?.let { ListReadingTestAdapter(it, this) }
        recycleViewListReadingTests.adapter = mAdapter
    }

    private fun setData() {
        //TODO
        val maxTestNumber = 10
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

    override fun onClick(position: Int) {
        when (position) {
            0 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)

            }
            1 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)

            }
            2 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)
            }
            3 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)
            }
            4 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)
            }
            5 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)
            }
            6 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)

            }
            7 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)

            }
            8 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)
            }
            9 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)
            }
            10 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)
            }
        }
    }
}
