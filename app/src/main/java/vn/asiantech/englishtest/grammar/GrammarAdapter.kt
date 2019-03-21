package vn.asiantech.englishtest.grammar

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.grammar_items.view.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.GrammarItem

class GrammarAdapter(
    private val grammarItem: List<GrammarItem>,
    private val grammarListener: OnClickGrammarListener
) : RecyclerView.Adapter<GrammarAdapter.GrammarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): GrammarViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.grammar_items, parent, false)
        return GrammarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return grammarItem.size
    }

    override fun onBindViewHolder(holder: GrammarViewHolder, position: Int) {
        holder.bindView(grammarItem[position])
    }

    interface OnClickGrammarListener {
        fun onClickGrammarItem(position: Int)
    }

    inner class GrammarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
            grammarListener.onClickGrammarItem(layoutPosition)
        }

        fun bindView(listGrammar: GrammarItem) {
            with(itemView) {
                with(listGrammar) {
                    tvGrammarTitle.text = grammarTitle
                    tvGrammarExample.text = grammarExample
                }
            }
            itemView.llGrammar.setOnClickListener(this)
        }
    }
}
