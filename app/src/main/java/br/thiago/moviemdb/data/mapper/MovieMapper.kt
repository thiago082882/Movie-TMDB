package br.thiago.moviemdb.data.mapper

import br.thiago.moviemdb.data.local.entity.MovieEntity
import br.thiago.moviemdb.data.model.movie.AuthorDetailsResponse
import br.thiago.moviemdb.data.model.movie.CountryResponse
import br.thiago.moviemdb.data.model.movie.CreditResponse
import br.thiago.moviemdb.data.model.movie.GenreResponse
import br.thiago.moviemdb.data.model.movie.MovieResponse
import br.thiago.moviemdb.data.model.movie.MovieReviewResponse
import br.thiago.moviemdb.data.model.movie.PersonResponse
import br.thiago.moviemdb.domain.model.movie.AuthorDetails
import br.thiago.moviemdb.domain.model.movie.Country
import br.thiago.moviemdb.domain.model.movie.Credit
import br.thiago.moviemdb.domain.model.movie.Genre
import br.thiago.moviemdb.domain.model.movie.Movie
import br.thiago.moviemdb.domain.model.movie.MovieReview
import br.thiago.moviemdb.domain.model.movie.Person


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
        productionCountries = productionCountries?.map { it.toDomain() },
        runtime = runtime
    )
}

fun CountryResponse.toDomain(): Country {
    return Country(
        name = name
    )
}

fun PersonResponse.toDomain(): Person {
    return Person(
        adult = adult,
        gender = gender,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profilePath = profilePath,
        castId = castId,
        character = character,
        creditId = creditId,
        order = order
    )
}

fun CreditResponse.toDomain(): Credit {
    return Credit(
        cast = cast?.map { it.toDomain() }
    )
}

fun AuthorDetailsResponse.toDomain(): AuthorDetails {
    return AuthorDetails(
        name = name,
        username = username,
        avatarPath = avatarPath,
        rating = rating
    )
}

fun MovieReviewResponse.toDomain(): MovieReview {
    return MovieReview(
        author = author,
        authorDetails = authorDetailsResponse?.toDomain(),
        content = content,
        createdAt = createdAt,
        id = id,
        updatedAt = updatedAt,
        url = url
    )
}

fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        poster = posterPath,
        runtime = runtime,
        insertion = System.currentTimeMillis()
    )
}

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        posterPath = poster,
        runtime = runtime
    )
}