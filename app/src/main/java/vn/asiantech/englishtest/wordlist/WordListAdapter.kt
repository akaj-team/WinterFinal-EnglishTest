package vn.asiantech.englishtest.wordlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_test_title.view.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.WordListItem

class WordListAdapter(
    private val wordListItem: List<WordListItem>,
    private val wordListListener: OnWordListClickListener
) :
    RecyclerView.Adapter<WordListAdapter.WordListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): WordListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_test_title, parent, false)
        return WordListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wordListItem.size
    }

    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        holder.bindView(wordListItem[position])
    }

    interface OnWordListClickListener {
        fun onClickWordList(position: Int)
    }

    inner class WordListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
            wordListListener.onClickWordList(layoutPosition)
        }

        fun bindView(wordListItem: WordListItem) {
            itemView.tvTestTitle.text = wordListItem.testTitle
            itemView.tvTestTitle.setOnClickListener(this)
        }
    }
}
