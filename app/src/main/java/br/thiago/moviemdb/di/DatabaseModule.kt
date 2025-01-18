package br.thiago.moviemdb.di

import android.content.Context
import androidx.room.Room
import br.thiago.moviemdb.data.local.dao.MovieDao
import br.thiago.moviemdb.data.local.db.AppDatabase
import br.thiago.moviemdb.util.Database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        Database.MOVIE_DATABASE
    ).build()

    @Provides
    fun providesMovieDao(database: AppDatabase): MovieDao = database.movieDao()

}