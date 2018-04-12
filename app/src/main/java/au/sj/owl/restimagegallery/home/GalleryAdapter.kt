package au.sj.owl.restimagegallery.home

import android.graphics.drawable.Drawable
import android.support.v7.widget.DrawableUtils
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import au.sj.owl.restimagegallery.R
import au.sj.owl.restimagegallery.home.link.Link
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.rv_icon.view.rv_iconPlaceholder
import kotlinx.android.synthetic.main.rv_icon.view.rv_progress
import timber.log.Timber


class GalleryAdapter(var activity: MainActivity,
                     var items: List<Link>) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var options = RequestOptions()
    //            .placeholder(R.mipmap.ic_launcher_round)
    //            .error(R.mipmap.ic_launcher)

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        holder.progress.visibility = View.VISIBLE
        holder.icon.transitionName = items[position].link
        Glide.with(activity)
                .load(items[position].link)
                .apply(options)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?,
                                              model: Any?,
                                              target: Target<Drawable>?,
                                              isFirstResource: Boolean): Boolean {
                        Timber.e("jsp load of ${items[position].link} failed")
                        holder.progress.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?,
                                                 model: Any?,
                                                 target: Target<Drawable>?,
                                                 dataSource: DataSource?,
                                                 isFirstResource: Boolean): Boolean {
                        holder.progress.visibility = View.GONE

                        holder.icon.setOnClickListener {
                            activity.onItemClicked(items[position].link, holder.icon)
                        }
                        return false
                    }

                }).into(holder.icon)


        Timber.d("jsp loaded ${items[position].link}")
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_icon, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var icon: ImageView = v.rv_iconPlaceholder
        var progress: ProgressBar = v.rv_progress
    }
}

