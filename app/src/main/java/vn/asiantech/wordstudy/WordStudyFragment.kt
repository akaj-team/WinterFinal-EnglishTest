package vn.asiantech.wordstudy

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_grammar_detail.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.WordStudyItem

class WordStudyFragment : Fragment() {

    private var wordStudyItem = arrayListOf<WordStudyItem>()
    private var wordStudyAdapter: WordStudyAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_grammar_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initData()
    }

    private fun initRecyclerView() {
        recycleViewGrammarDetail.apply {
            layoutManager = LinearLayoutManager(activity)
            wordStudyAdapter = WordStudyAdapter(wordStudyItem)
            adapter = wordStudyAdapter
        }
    }

    private fun initData() {
        for (i in 0 until 10) {
            wordStudyItem.add(
                WordStudyItem(
                    "BAO DEP TRAI",
                    "Locate",
                    "/ləʊˈkeɪt/",
                    "to find or discover the exact position of something",
                    "Xác định vị trí",
                    "Police are still trying to locate the suspect.",
                    "Cảnh sát vẫn đang cố gắng xác định ví trí của nghi phạm."
                )
            )
        }
    }
}