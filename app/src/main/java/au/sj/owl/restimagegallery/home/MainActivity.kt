package au.sj.owl.restimagegallery.home

import android.app.DownloadManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.ImageView
import au.sj.owl.restimagegallery.R.layout
import au.sj.owl.restimagegallery.home.link.Link
import au.sj.owl.restimagegallery.home.link.LinksDB
import kotlinx.android.synthetic.main.fragment_grid.rvImgs
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    lateinit var mHomeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        mHomeViewModel.db = Room.databaseBuilder(applicationContext, LinksDB::class.java, "links-db").build()

        var links: List<Link> = listOf()
        rvImgs.adapter = GalleryAdapter(this, links)
        rvImgs.layoutManager = GridLayoutManager(this, 4)

        ViewModelProviders.of(this).get(HomeViewModel::class.java).getLinks()
                .observe(this, Observer<List<Link>> { links ->
                    rvImgs.adapter = GalleryAdapter(this, links!!)
                })
    }

    fun downloadLargeFile() {
        val m = packageManager
        var s = packageName
        val p = m.getPackageInfo(s, 0)
        s = p.applicationInfo.dataDir

        val downloadmanager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse("http://www.example.com/myfile.mp3")
        val request = DownloadManager.Request(uri)
        request.setTitle("My File")
        request.setDescription("Downloading")
        request.setVisibleInDownloadsUi(true)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationUri(Uri.parse("$s/myfile.mp3"))
        downloadmanager.enqueue(request)
    }

    fun onItemClicked(link: String,
                      sharedImView: ImageView) {
        Timber.d("jsp item clicked")
        val intent = Intent(this, DetailedActivity::class.java)
        intent.putExtra("link", link)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                                                                         sharedImView,
                                                                         sharedImView.transitionName)
        startActivity(intent, options.toBundle())
    }
}


