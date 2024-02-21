package co.develhope.meteoapp.ui.di

import co.develhope.meteoapp.network.SearchRepo
import co.develhope.meteoapp.network.SearchService
import co.develhope.meteoapp.network.WeatherRepo
import co.develhope.meteoapp.network.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun provideRepo(weatherService: WeatherService): WeatherRepo {
        return WeatherRepo(weatherService)
    }
    @Provides
    fun provideSearchRepo(searchService: SearchService): SearchRepo {
        return SearchRepo(searchService)
    }
}