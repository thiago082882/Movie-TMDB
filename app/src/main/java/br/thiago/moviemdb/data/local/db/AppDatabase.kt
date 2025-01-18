package br.thiago.moviemdb.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.thiago.moviemdb.data.local.dao.MovieDao
import br.thiago.moviemdb.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}