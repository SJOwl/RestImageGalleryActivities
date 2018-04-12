package au.sj.owl.restimagegallery

import au.sj.owl.restimagegallery.web.ImdbService
import org.jsoup.Jsoup
import org.junit.*
import org.junit.Assert.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun imdbPageRequest() {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://www.imdb.com/")
                .build()
        val service = retrofit.create(ImdbService::class.java) as ImdbService

        val call = service.getPage()
        //        call.enqueue(object:Callback<String>{
        //            override fun onFailure(call: Call<ResponseBody>?,
        //                                   t: Throwable?) {
        //                println("failure")
        //            }
        //
        //            override fun onResponse(call: Call<ResponseBody>?,
        //                                    response: Response<ResponseBody>?) {
        //                response.toString()
        //                println("response = ${response.body().toString()}")
        //            }
        //
        //        })

        var response: Response<String> = call.execute()
        var res = response.body().toString()
        print(res)
    }

    @Test
    fun parseImdbRequest() {
        var doc = Jsoup.connect("http://www.imdb.com/chart/top").get()
        var imgs = doc.select("img[src*=\"https://ia.media-imdb.com/images/M/\"]")
        imgs.forEach {
            var link = it.attr("src").replace("_V1_UY67_CR0,0,45,67_AL_", "_V1_UX182_CR0,0,182,268_AL_")
            println(link) }
    }

}
