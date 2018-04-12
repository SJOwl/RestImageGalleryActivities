package au.sj.owl.restimagegallery.home.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.transition.Fade
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import au.sj.owl.restimagegallery.R
import au.sj.owl.restimagegallery.home.DetailsTransition
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_film_details.detailImg

class FilmDetailsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move)

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_film_details, container, false)
    }

    override fun onViewCreated(view: View,
                               savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var link = arguments!!.getString(EXTRA_LINK)
        var transition = arguments!!.getString(EXTRA_TRANSITION)

        detailImg.transitionName = transition

        Glide.with(this.activity!!)
                .load(link)
                .into(detailImg)
    }

    companion object {

        private val EXTRA_LINK = "LINKl_item"
        private val EXTRA_TRANSITION = "transition_name"

        fun newInstance(link: String,
                        transitionName: String): FilmDetailsFragment {
            val fragment = FilmDetailsFragment()
            val args = Bundle()
            args.putString(EXTRA_LINK, link)
            args.putString(EXTRA_TRANSITION, transitionName)
            fragment.arguments = args
            fragment.sharedElementEnterTransition = DetailsTransition()
            fragment.sharedElementReturnTransition = DetailsTransition()
            fragment.enterTransition = Fade()
            fragment.exitTransition = Fade()
            return fragment
        }
    }
}
