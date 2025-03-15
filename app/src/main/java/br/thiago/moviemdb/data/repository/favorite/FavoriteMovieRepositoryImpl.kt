package br.thiago.moviemdb.data.repository.favorite


import br.thiago.moviemdb.domain.model.favorite.FavoriteMovie
import br.thiago.moviemdb.domain.repository.movie.FavoriteMovieRepository
import br.thiago.moviemdb.util.FirebaseHelper
import br.thiago.moviemdb.util.normalize
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class FavoriteMovieRepositoryImpl @Inject constructor(
    firebaseDatabase: FirebaseDatabase
) : FavoriteMovieRepository {

    private val favoritesRef = firebaseDatabase.reference
        .child("favorites")

    override suspend fun saveFavorites(favorites: List<FavoriteMovie>) {
        return suspendCoroutine { continuation ->
            favoritesRef
                .child(FirebaseHelper.getUserId())
                .setValue(favorites)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resumeWith(Result.success(Unit))
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }

    override suspend fun searchFavorites(query: String): List<FavoriteMovie> {
        return suspendCoroutine { continuation ->
            favoritesRef
                .child(FirebaseHelper.getUserId())
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val favorites: MutableList<FavoriteMovie> = mutableListOf()

                        val normalizedQuery = query.normalize()

                        for (ds in task.result.children) {
                            val favorite = ds.getValue(FavoriteMovie::class.java)
                            favorite?.let {
                                val title = it.title?.normalize()
                                if (title?.contains(normalizedQuery, ignoreCase = true) == true) {
                                    favorites.add(it)
                                }
                            }
                        }
                        continuation.resumeWith(Result.success(favorites))
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }

    override suspend fun getFavorites(): List<FavoriteMovie> {
        return suspendCoroutine { continuation ->
            favoritesRef
                .child(FirebaseHelper.getUserId())
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val favorites: MutableList<FavoriteMovie> = mutableListOf()
                        for (ds in task.result.children) {
                            val favorite = ds.getValue(FavoriteMovie::class.java)
                            favorite?.let { favorites.add(it) }
                        }
                        continuation.resumeWith(Result.success(favorites))
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }

}