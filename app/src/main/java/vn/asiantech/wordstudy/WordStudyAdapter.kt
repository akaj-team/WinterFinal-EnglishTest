package vn.asiantech.wordstudy

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_word_study.view.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.WordStudyItem

class WordStudyAdapter(private val wordStudy: MutableList<WordStudyItem>) :
    RecyclerView.Adapter<WordStudyAdapter.WordStudyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): WordStudyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_word_study, parent, false)
        return WordStudyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wordStudy.size
    }

    override fun onBindViewHolder(holder: WordStudyViewHolder, position: Int) {
        holder.bindView(wordStudy[position])
    }

    inner class WordStudyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(wordStudy: WordStudyItem) {
            with(itemView) {
                with(wordStudy) {
                    //Glide.with(context).load(imageWordStudy).into(imgWordStudy)
                    tvWord.text = word
                    tvSpelling.text = spelling
                    tvDefinition.text = definition
                    tvMeaning.text = meaning
                    tvExample.text = example
                    tvTranslation.text = translation
                }
            }
        }
    }
}
