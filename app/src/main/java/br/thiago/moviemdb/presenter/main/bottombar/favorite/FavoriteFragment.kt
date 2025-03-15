package br.thiago.moviemdb.presenter.main.bottombar.favorite



import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.thiago.moviemdb.R
import br.thiago.moviemdb.data.mapper.toDomain
import br.thiago.moviemdb.databinding.FragmentFavoriteBinding
import br.thiago.moviemdb.domain.model.favorite.FavoriteMovie
import br.thiago.moviemdb.presenter.main.bottombar.home.adapter.MovieAdapter
import br.thiago.moviemdb.util.StateView
import br.thiago.moviemdb.util.applyScreenWindowInsets
import br.thiago.moviemdb.util.initToolbar
import br.thiago.moviemdb.util.onNavigate
import br.thiago.moviemdb.MainGraphDirections
import com.ferfalk.simplesearchview.SimpleSearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(toolbar = binding.toolbar, showIconNavigation = false)

        applyScreenWindowInsets(
            view = view,
            applyBottom = false
        )

        initRecycler()

        getFavorites()

        initSearchView()
    }

    private fun getFavorites() {
        viewModel.getFavorites().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    isLoading(isLoading = true)
                }

                is StateView.Success -> {
                    configData(movies = stateView.data ?: emptyList())
                }

                is StateView.Error -> {
                    isLoading(isLoading = false)
                }
            }
        }
    }

    private fun searchFavorites(query: String) {
        viewModel.searchFavorites(query).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    isLoading(isLoading = true)
                }

                is StateView.Success -> {
                    configData(movies = stateView.data ?: emptyList())
                }

                is StateView.Error -> {
                    isLoading(isLoading = false)
                }
            }
        }
    }

    private fun initSearchView() {
        binding.simpleSearchView.setOnQueryTextListener(object :
            SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotBlank() || query.isEmpty()) {
                    searchFavorites(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                return false
            }
        })

        binding.simpleSearchView.setOnSearchViewListener(object :
            SimpleSearchView.SearchViewListener {
            override fun onSearchViewShown() {
            }

            override fun onSearchViewClosed() {
                getFavorites()
            }

            override fun onSearchViewShownAnimation() {

            }

            override fun onSearchViewClosedAnimation() {

            }
        })
    }

    private fun initRecycler() {
        movieAdapter = MovieAdapter(
            context = requireContext(),
            layoutInflater = R.layout.movie_genre_item,
            movieClickListener = { movieId ->
                movieId?.let {
                    val action = MainGraphDirections
                        .actionGlobalMovieDetailsFragment(movieId)
                    findNavController().onNavigate(action)
                }
            }
        )

        val lm = GridLayoutManager(requireContext(), 2)

        with(binding.rvMovies) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun configData(movies: List<FavoriteMovie>) {
        isLoading(isLoading = false)

        movieAdapter.submitList(movies.map { it.toDomain() })
        binding.layoutEmpty.isVisible = movies.isEmpty()
    }

    private fun isLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.rvMovies.isVisible = !isLoading
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_view, menu)
        val item = menu.findItem(R.id.action_search)
        binding.simpleSearchView.setMenuItem(item)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
