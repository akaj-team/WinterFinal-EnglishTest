package vn.asiantech.englishtest.wordlist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_list_test.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.listtest.TestListActivity
import vn.asiantech.englishtest.listtest.TestListFragment
import vn.asiantech.englishtest.model.WordListItem
import vn.asiantech.englishtest.takingtest.TakingTestActivity

class WordListFragment : Fragment(), WordListAdapter.OnWordListClickListener {

    private var wordListItem = arrayListOf<WordListItem>()
    private var wordListAdapter: WordListAdapter? = null
    private var databaseReference: DatabaseReference? = null

    companion object {

        const val ARG_LIST_TEST_TITLE = "arg_list_test_title"

        fun getInstance(level: Int): WordListFragment =
            WordListFragment().apply {
                val bundle = Bundle().apply {
                    putInt(TestListFragment.ARG_LEVEL, level)
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

    override fun onClickTestTitle(position: Int) = startActivity(
        Intent(activity, TakingTestActivity::class.java)
            .putExtra(TestListFragment.ARG_POSITION, position)
            .putExtra(TestListFragment.ARG_LEVEL, arguments?.getInt(TestListFragment.ARG_LEVEL))
            .putParcelableArrayListExtra(ARG_LIST_TEST_TITLE, wordListItem)
    )

    private fun initRecyclerView() = recycleViewListReadingTests.apply {
        layoutManager = GridLayoutManager(activity, 2)
        wordListAdapter = WordListAdapter(wordListItem, this@WordListFragment)
        adapter = wordListAdapter
    }

    private fun initData() {
        (activity as TestListActivity).initProgressDialog()
        databaseReference = FirebaseDatabase.getInstance().getReference("testTitle")
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(wordListData: DataSnapshot) {
                (activity as TestListActivity).dismissProgressDialog()
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
