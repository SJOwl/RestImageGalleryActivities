package au.sj.owl.restimagegallery.home

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import au.sj.owl.restimagegallery.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_detailed.detailImg
import kotlinx.android.synthetic.main.activity_detailed.detailedRoot

class DetailedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        var url = intent.getStringExtra("link")
        detailImg.transitionName = url
        detailImg.setOnClickListener {
            finishAfterTransition()
        }

        postponeEnterTransition()
        Glide.with(this)
                .load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?,
                                              model: Any?,
                                              target: Target<Drawable>?,
                                              isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?,
                                                 model: Any?,
                                                 target: Target<Drawable>?,
                                                 dataSource: DataSource?,
                                                 isFirstResource: Boolean): Boolean {
                        val bmp = (resource as BitmapDrawable).bitmap
                        Palette.from(bmp).generate{p->
                            var color = p.getMutedColor(Color.parseColor("#fafafa"))
                            detailedRoot.setBackgroundColor(color)
                            startPostponedEnterTransition()
                        }
                        return false
                    }

                })
                .into(detailImg)
    }
}
