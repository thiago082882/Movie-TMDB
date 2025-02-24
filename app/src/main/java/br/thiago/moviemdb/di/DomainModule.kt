package br.thiago.moviemdb.di

import br.thiago.moviemdb.data.repository.auth.FirebaseAuthenticationImpl
import br.thiago.moviemdb.domain.repository.auth.FirebaseAuthentication
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsFirebaseAuthenticationImpl(
        firebaseAuthenticationImpl: FirebaseAuthenticationImpl
    ): FirebaseAuthentication

//    @Binds
//    abstract fun bindsMovieRepositoryImpl(
//        movieRepositoryImpl: MovieRepositoryImpl
//    ): MovieRepository
//
//    @Binds
//    abstract fun bindsMovieDetailsRepositoryImpl(
//        movieDetailsRepositoryImpl: MovieDetailsRepositoryImpl
//    ): MovieDetailsRepository
//
//    @Binds
//    abstract fun bindsMovieLocalRepositoryImpl(
//        movieLocalRepositoryImpl: MovieLocalRepositoryImpl
//    ): MovieLocalRepository
//
//    @Binds
//    abstract fun bindsUserRepositoryImpl(
//        userRepositoryImpl: UserRepositoryImpl
//    ): UserRepository

}