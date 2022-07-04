package my.project.a22bytetestapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.project.a22bytetestapp.data.api.NewsApi
import my.project.a22bytetestapp.presentation.adapters.NewsAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAdapter(): NewsAdapter = NewsAdapter()
}