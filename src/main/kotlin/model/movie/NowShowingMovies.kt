package model.movie

class NowShowingMovies(
    movies: List<Movie>,
) {
    private val movies = movies.toList()

    fun findByName(name: String): Movie? = movies.firstOrNull { it.name.name == name }
}
