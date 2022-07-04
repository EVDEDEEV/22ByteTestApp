package my.project.a22bytetestapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import my.project.a22bytetestapp.BuildConfig.ApiKey
import my.project.a22bytetestapp.data.api.NewsApi
import my.project.a22bytetestapp.data.models.mapToUi
import my.project.a22bytetestapp.presentation.models.News
import javax.inject.Inject

const val PAGE_INDEX = 1
const val NETWORK_PAGE_SIZE = 20
const val KEY_WORD = "Android"

class NewsPagingSource @Inject constructor(
    private val newsApi: NewsApi,
) : PagingSource<Int, News>() {

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val currentPosition = params.key ?: 1
            val response =
                newsApi.getNews(keyWord = KEY_WORD,
                    apiKey = ApiKey,
                    page = currentPosition,
                    pageSize = NETWORK_PAGE_SIZE).body()?.mapToUi()
            LoadResult.Page(
                data = response.orEmpty(),
                prevKey = if (currentPosition == 1) null
                else currentPosition.minus(PAGE_INDEX),
                nextKey = currentPosition.plus(PAGE_INDEX)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}