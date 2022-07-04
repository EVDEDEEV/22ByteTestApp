package my.project.a22bytetestapp.data.models


import com.google.gson.annotations.SerializedName
import my.project.a22bytetestapp.presentation.models.News

data class NewsResponse(
    @SerializedName("articles")
    val articles: List<Article?>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?,
)

fun NewsResponse?.mapToUi(): List<News> = this?.articles?.map { news ->
    News(
        title = news?.title.orEmpty(),
        image = news?.urlToImage.orEmpty(),
        source = news?.source?.name.orEmpty(),
        url = news?.url.orEmpty()
    )
}.orEmpty()