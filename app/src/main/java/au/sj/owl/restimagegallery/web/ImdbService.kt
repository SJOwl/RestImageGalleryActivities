package au.sj.owl.restimagegallery.web

import retrofit2.Call
import retrofit2.http.GET

interface ImdbService {

    @GET("chart/top")
    fun getPage(): Call<String>
}