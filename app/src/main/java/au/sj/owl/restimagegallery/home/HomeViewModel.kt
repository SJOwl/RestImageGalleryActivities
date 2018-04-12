package au.sj.owl.restimagegallery.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import au.sj.owl.restimagegallery.home.link.Link
import au.sj.owl.restimagegallery.home.link.LinksDB
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import java.net.UnknownHostException

class HomeViewModel : ViewModel() {
    private var links: LiveData<List<Link>>? = null
    var db: LinksDB? = null
    var linksLoaded = false
    val imgH = 536*4
    val imgW = 364*4

    fun getLinks(): LiveData<List<Link>> {

        if (!linksLoaded)
            loadLinksFromServer()
        return db!!.linksDao().getLinks()
    }

    private fun loadLinksFromServer() {

        Single.fromCallable {
            var highResolutionLinks: MutableList<Link> = mutableListOf()
            try {
                var doc = Jsoup.connect("http://www.imdb.com/chart/top").get()
                var imgs = doc.select("img[src*=\"https://ia.media-imdb.com/images/M/\"]")
                imgs.mapTo(highResolutionLinks) { it ->
                    Link(it.attr("src")
                                 .replace("0,45,67_AL_", "0,$imgW,${imgH}_AL_")
                                 .replace(Regex("U.\\d*_CR."), "UY${imgH}_CR3"))
                }
                linksLoaded = true
            } catch (e: UnknownHostException) {
                return@fromCallable highResolutionLinks
            }
            return@fromCallable highResolutionLinks
        }.subscribeOn(Schedulers.io()).subscribe { list ->
            db!!.linksDao().saveLinks(list)
        }
    }
}