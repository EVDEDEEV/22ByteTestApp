package my.project.a22bytetestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import my.project.a22bytetestapp.data.repository.NewsRepositoryImpl
import my.project.a22bytetestapp.presentation.models.News
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NewsRepositoryImpl,
) : ViewModel() {

    suspend fun getNews(): Flow<PagingData<News>> {
        return repository.getNewsList().cachedIn(viewModelScope)
    }
}

