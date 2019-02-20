package vn.asiantech.englishtest.listreadingtest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.reading_test_list_items.view.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ListReadingTestItems

class ListReadingTestAdapter(private val listTests: List<ListReadingTestItems>) :
    RecyclerView.Adapter<ListReadingTestAdapter.ListReadingTestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ListReadingTestViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.reading_test_list_items, parent, false)
        return ListReadingTestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTests.size
    }

    override fun onBindViewHolder(holder: ListReadingTestViewHolder, position: Int) {
        holder.bindView(listTests[position])
    }

    inner class ListReadingTestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(listItems: ListReadingTestItems) {
            with(itemView){
                with(listItems) {
                    tvTestName.text = testNumber
                    tvTimeDisplay.text = timeDisplay
                    tvScoreDisplay.text = scoreDisplay.toString()
                }
            }
        }
    }
}
