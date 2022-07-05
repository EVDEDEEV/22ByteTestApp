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
const val KEY_WORD = "android"

class NewsPagingSource @Inject constructor(
    private val newsApi: NewsApi,
) : PagingSource<Int, News>() {

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PAGE_INDEX)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PAGE_INDEX)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {

            val currentPosition = params.key ?: 1
            val pageSize = params.loadSize.coerceAtMost(NETWORK_PAGE_SIZE)
            val response =
                newsApi.getNews(keyWord = KEY_WORD,
                    apiKey = ApiKey,
                    page = currentPosition,
                    pageSize = pageSize).body()?.mapToUi()

            val nextKey = if (response?.isEmpty() == true) {
                null
            } else {

                if (
                    params.loadSize == 3 * NETWORK_PAGE_SIZE
                ) {
                    currentPosition.plus(PAGE_INDEX)
                } else {
                    currentPosition + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            }
            val prevKey = if (currentPosition == PAGE_INDEX) null
            else currentPosition.minus(PAGE_INDEX)

            LoadResult.Page(
                data = response.orEmpty(),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}