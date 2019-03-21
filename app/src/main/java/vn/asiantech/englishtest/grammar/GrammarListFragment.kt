package vn.asiantech.englishtest.grammar

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_list_test.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.listreadingtest.ListReadingTestActivity
import vn.asiantech.englishtest.listreadingtest.ListReadingTestFragment
import vn.asiantech.englishtest.model.GrammarItem
import vn.asiantech.englishtest.takingreadingtest.TakingReadingTestActivity

class GrammarListFragment : Fragment(), GrammarAdapter.OnClickGrammarListener {

    private var grammarAdapter: GrammarAdapter? = null
    private var grammarItems = arrayListOf<GrammarItem>()
    private lateinit var reference: DatabaseReference

    companion object {

        fun getInstance(level: Int): GrammarListFragment =
            GrammarListFragment().apply {
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

    override fun onClickGrammarItem(position: Int) {
        startActivity(
            Intent(activity, TakingReadingTestActivity::class.java)
                .putExtra(ListReadingTestFragment.ARG_POSITION, position)
                .putExtra(ListReadingTestFragment.ARG_LEVEL, arguments?.getInt(ListReadingTestFragment.ARG_LEVEL))
        )
    }

    private fun initRecyclerView() {
        recycleViewListReadingTests.apply {
            layoutManager = LinearLayoutManager(activity)
            grammarAdapter = GrammarAdapter(grammarItems, this@GrammarListFragment)
            adapter = grammarAdapter
        }
    }

    private fun initData() {
        (activity as? ListReadingTestActivity)?.initProgressDialog()
        reference = FirebaseDatabase.getInstance().getReference("grammar")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented")
            }

            override fun onDataChange(grammarData: DataSnapshot) {
                (activity as? ListReadingTestActivity)?.dismissProgressDialog()
                for (i in grammarData.children) {
                    val grammar = i.getValue(GrammarItem::class.java)
                    grammar?.let {
                        grammarItems.add(it)
                    }
                }
                grammarAdapter?.notifyDataSetChanged()
            }

        })
    }
}
