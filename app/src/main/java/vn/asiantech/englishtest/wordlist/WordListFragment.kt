package vn.asiantech.englishtest.wordlist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_test.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.listreadingtest.ListReadingTestFragment
import vn.asiantech.englishtest.model.WordListItem

class WordListFragment : Fragment(), WordListAdapter.OnWordListClickListener{


    private var wordListItem = arrayListOf<WordListItem>()
    private var wordListAdapter: WordListAdapter? = null
    private var level: Int? = null

    companion object {

        const val ARG_WORD_LIST = "arg_word_list"

        fun getInstance(level: Int): WordListFragment =
            WordListFragment().apply {
                val bundle = Bundle().apply {
                    putInt(ListReadingTestFragment.ARG_LEVEL, level)
                }
                arguments = bundle
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initData()
    }

    override fun onClickWordList(position: Int) {

    }

    private fun initRecyclerView(){
        recycleViewListReadingTests.apply {
            layoutManager = GridLayoutManager(activity, 2)
            wordListAdapter = WordListAdapter(wordListItem, this@WordListFragment)
            adapter = wordListAdapter
        }
    }

    private fun initData(){
        for (i in 0 until 10){
            wordListItem.add(WordListItem("Test ${i + 1}"))
        }
    }
}