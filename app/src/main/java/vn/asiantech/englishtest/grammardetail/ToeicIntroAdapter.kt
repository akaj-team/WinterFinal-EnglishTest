package vn.asiantech.englishtest.grammardetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_grammar_detail.view.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ToeicIntroItem

class ToeicIntroAdapter(private val toeicIntroItem: List<ToeicIntroItem>) :
    RecyclerView.Adapter<ToeicIntroAdapter.ToeicIntroViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ToeicIntroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grammar_detail, parent, false)
        return ToeicIntroViewHolder(view)
    }

    override fun getItemCount(): Int {
        return toeicIntroItem.size
    }

    override fun onBindViewHolder(holder: ToeicIntroViewHolder, position: Int) {
        holder.bindView(toeicIntroItem[position])
    }

    inner class ToeicIntroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(toeicIntroItem: ToeicIntroItem) {
            with(itemView) {
                with(toeicIntroItem) {
                    tvGrammarDetailTitle.text = introTitle
                    tvGrammarDetailDescription.text = introContent
                }
            }
        }
    }
}
