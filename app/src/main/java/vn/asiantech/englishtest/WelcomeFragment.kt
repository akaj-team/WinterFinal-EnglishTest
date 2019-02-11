package vn.asiantech.englishtest

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_welcome.view.*


class WelcomeFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_welcome, container, false)
        view.imgListeningTest.setOnClickListener(this)
        view.imgReadingTest.setOnClickListener(this)
        return view
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imgListeningTest -> {
                Handler().postDelayed({
                    val transaction = fragmentManager?.beginTransaction()
                    transaction?.replace(R.id.welcomeFragment, ListListeningTestFragment())
                    transaction?.commit()
                    transaction?.addToBackStack(null)
                }, 500)
            }
            R.id.imgReadingTest -> {
                Handler().postDelayed({
                    val transaction = fragmentManager?.beginTransaction()
                    transaction?.replace(R.id.welcomeFragment, ListReadingTestFragment())
                    transaction?.commit()
                    transaction?.addToBackStack(null)
                }, 500)
            }
        }
    }
}
