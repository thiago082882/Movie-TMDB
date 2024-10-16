package br.thiago.moviemdb.di

import br.thiago.moviemdb.data.api.ServiceApi
import br.thiago.moviemdb.network.ServiceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun providerServiceProvider() = ServiceProvider()

    @Provides
    fun providerServiceApi(
     serviceProvider: ServiceProvider
    ):ServiceApi{
        return serviceProvider.createService(ServiceApi::class.java)
    }

}