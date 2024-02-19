package co.develhope.meteoapp.di

import co.develhope.meteoapp.network.WeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWeatherRepo(): WeatherRepo{
        return WeatherRepo()
    }
}