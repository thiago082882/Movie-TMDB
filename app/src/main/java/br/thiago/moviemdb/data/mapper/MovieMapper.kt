package br.thiago.moviemdb.data.mapper


import br.thiago.moviemdb.data.model.movie.GenreResponse
import br.thiago.moviemdb.data.model.movie.MovieResponse
import br.thiago.moviemdb.domain.model.movie.Genre
import br.thiago.moviemdb.domain.model.movie.Movie

fun GenreResponse.toDomain(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun MovieResponse.toDomain(): Movie {
    return Movie(
        adult = adult,
        backdropPath = backdropPath,
        genres = genres?.map { it.toDomain() },
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        runtime = runtime
    )
}





