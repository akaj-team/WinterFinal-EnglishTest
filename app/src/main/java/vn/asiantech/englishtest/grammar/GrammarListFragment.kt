package vn.asiantech.englishtest.grammar

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.grammar_items.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.GrammarItem

class GrammarListFragment : Fragment() {

    private lateinit var reference: DatabaseReference
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reference = FirebaseDatabase.getInstance().getReference("grammar")
        return inflater.inflate(R.layout.fragment_list_test, container, false)
    }

    class GrammarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView?.layoutManager = LinearLayoutManager(activity)

        val option = FirebaseRecyclerOptions.Builder<GrammarItem>().setQuery(reference, GrammarItem::class.java).build()

        val firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<GrammarItem, GrammarViewHolder>(option) {
            override fun onCreateViewHolder(parent: ViewGroup, p1: Int): GrammarViewHolder {
                val view2: View = LayoutInflater.from(parent.context).inflate(R.layout.grammar_items, parent, false)
                return GrammarViewHolder(view2)
            }

            override fun onBindViewHolder(holder: GrammarViewHolder, position: Int, model: GrammarItem) {
                val refid: String = getRef(position).key.toString()
                reference.child(refid).addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        tvGrammarTitle.text = model.key1
                        tvGrammarExample.text = model.key2
                    }
                })
            }
        }

        recyclerView?.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()
    }
}

