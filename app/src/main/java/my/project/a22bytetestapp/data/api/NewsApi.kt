package my.project.a22bytetestapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getMovies(
        @Query("api-key") apiKey: String,
        @Query("offset") offset: Int,
    ): Response<MoviesResponse>
}