package vn.asiantech.englishtest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_welcome.view.*


class WelcomeFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_welcome, container, false)

        view.imgListeningTest.setOnClickListener(this)
        view.imgReadingTest.setOnClickListener(this)
        return view
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imgListeningTest -> {
                val transaction = fragmentManager?.beginTransaction()
                transaction?.setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left,
                    R.anim.slide_in_right,
                    R.anim.slide_out_right
                )
                transaction?.replace(R.id.welcomeFragment, ListListeningTestFragment())
                transaction?.addToBackStack(null)
                transaction?.commit()

            }
            R.id.imgReadingTest -> {
                val transaction = fragmentManager?.beginTransaction()
                transaction?.setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left,
                    R.anim.slide_in_right,
                    R.anim.slide_out_right
                )
                transaction?.replace(R.id.welcomeFragment, ListReadingTestFragment())
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
        }
    }
}
