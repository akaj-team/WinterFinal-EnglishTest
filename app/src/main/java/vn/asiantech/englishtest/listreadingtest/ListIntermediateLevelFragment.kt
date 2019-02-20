package vn.asiantech.englishtest.listreadingtest

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_basic_level.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ListReadingTestItems
import vn.asiantech.englishtest.takingreadingtest.TakingReadingTestActivity

class ListIntermediateLevelFragment : Fragment(), ListReadingTestAdapter.OnItemClickListener {

    private var mListReadingTestItems: List<ListReadingTestItems>? = null
    private val level = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_basic_level, container, false)
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
            adapter = mListReadingTestItems?.let { ListReadingTestAdapter(it, this@ListIntermediateLevelFragment) }
        }
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
                intent.putExtra("level", level)
                startActivity(intent)

            }
            1 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("level", level)
                startActivity(intent)

            }
            2 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("level", level)
                startActivity(intent)
            }
            3 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("level", level)
                startActivity(intent)
            }
            4 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("level", level)
                startActivity(intent)
            }
            5 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("level", level)
                startActivity(intent)
            }
            6 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("level", level)
                startActivity(intent)

            }
            7 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("level", level)
                startActivity(intent)

            }
            8 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("level", level)
                startActivity(intent)
            }
            9 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("level", level)
                startActivity(intent)
            }
            10 -> {
                val intent = Intent(activity, TakingReadingTestActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("level", level)
                startActivity(intent)
            }
        }
    }
}
