package au.sj.owl.restimagegallery.home.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import au.sj.owl.restimagegallery.R
import au.sj.owl.restimagegallery.home.GalleryAdapter
import au.sj.owl.restimagegallery.home.HomeViewModel
import au.sj.owl.restimagegallery.home.MainActivity
import au.sj.owl.restimagegallery.home.link.Link
import kotlinx.android.synthetic.main.fragment_grid.rvImgs

class GridFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_grid, container, false)
    }

    override fun onViewCreated(view: View,
                               savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var links: List<Link> = listOf()
        rvImgs.adapter = GalleryAdapter(activity!! as MainActivity,
                                                                        links)
        rvImgs.layoutManager = GridLayoutManager(activity!! as MainActivity, 2)

        ViewModelProviders.of(activity!!).get(HomeViewModel::class.java).getLinks()
                .observe(this,
                         Observer<List<Link>> { links ->
                             rvImgs.adapter = GalleryAdapter(activity!! as MainActivity,
                                                                                             links!!)
                         })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    companion object {
        fun newInstance(): GridFragment {
            return GridFragment()
        }
    }
}
