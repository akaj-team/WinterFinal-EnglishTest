package vn.asiantech.englishtest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.listening_test_list_items.view.*
import vn.asiantech.englishtest.model.ListListeningTestItems

class ListListeningTestAdapter(private val listTests: List<ListListeningTestItems>) :
    RecyclerView.Adapter<ListListeningTestAdapter.ListListeningTestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ListListeningTestViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.listening_test_list_items, parent, false)
        return ListListeningTestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTests.size
    }

    override fun onBindViewHolder(holder: ListListeningTestViewHolder, position: Int) {
        holder.bindView(listTests[position])
    }

    inner class ListListeningTestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(listItems: ListListeningTestItems) {
            itemView.tvTestName.text = listItems.testNumber
            itemView.tvTime.text = listItems.timeText
            itemView.tvTimeDisplay.text = listItems.timeDisplay
            itemView.tvScore.text = listItems.scoreText
            itemView.tvScoreDisplay.text = listItems.scoreDisplay.toString()
        }
    }
}
