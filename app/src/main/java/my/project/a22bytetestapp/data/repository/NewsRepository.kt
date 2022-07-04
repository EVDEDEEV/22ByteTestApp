package my.project.a22bytetestapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import my.project.a22bytetestapp.data.api.NewsApi
import my.project.a22bytetestapp.presentation.models.News
import javax.inject.Inject

interface NewsRepository {
    suspend fun getNewsList(): Flow<PagingData<News>>
}


class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
) : NewsRepository {

    override suspend fun getNewsList(): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                NewsPagingSource(api)
            }
        ).flow
    }
}