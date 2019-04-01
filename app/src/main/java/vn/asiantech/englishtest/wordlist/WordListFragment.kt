package vn.asiantech.englishtest.wordlist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_list_test.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.listreadingtest.ListReadingTestActivity
import vn.asiantech.englishtest.listreadingtest.ListReadingTestFragment
import vn.asiantech.englishtest.model.WordListItem

class WordListFragment : Fragment(), WordListAdapter.OnWordListClickListener {

    private var wordListItem = arrayListOf<WordListItem>()
    private var wordListAdapter: WordListAdapter? = null
    private var databaseReference: DatabaseReference? = null

    companion object {


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
        TODO("not implemented")
    }

    private fun initRecyclerView() {
        recycleViewListReadingTests.apply {
            layoutManager = GridLayoutManager(activity, 2)
            wordListAdapter = WordListAdapter(wordListItem, this@WordListFragment)
            adapter = wordListAdapter
        }
    }

    private fun initData() {
        (activity as ListReadingTestActivity).initProgressDialog()
        databaseReference = FirebaseDatabase.getInstance().getReference("testTitle")
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented")
            }

            override fun onDataChange(wordListData: DataSnapshot) {
                (activity as ListReadingTestActivity).dismissProgressDialog()
                for (i in wordListData.children) {
                    val wordList = i.getValue(WordListItem::class.java)
                    wordList?.let {
                        wordListItem.add(it)
                    }
                }
                wordListAdapter?.notifyDataSetChanged()
            }
        })
    }
}
