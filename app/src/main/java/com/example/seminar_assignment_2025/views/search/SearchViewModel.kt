package com.example.seminar_assignment_2025.views.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seminar_assignment_2025.domainmodel.Movie
import com.example.seminar_assignment_2025.data.movie.MovieRepository
import com.example.seminar_assignment_2025.data.search.SearchHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {

    val searchHistory: StateFlow<List<String>> = searchHistoryRepository.searchHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults.asStateFlow()

    private val _movieDetail = MutableStateFlow<Movie?>(null)
    val movieDetail: StateFlow<Movie?> = _movieDetail.asStateFlow()

    fun addSearchTerm(term: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                searchHistoryRepository.addSearchTerm(term)
            }
        }
    }

    fun removeSearchTerm(term: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                searchHistoryRepository.removeSearchTerm(term)
            }
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                searchHistoryRepository.clearSearchHistory()
            }
        }
    }

    fun searchByTitle(query: String) {
        viewModelScope.launch {
            val results = withContext(Dispatchers.IO) {
                movieRepository.searchByTitle(query)
            }
            _searchResults.value = results
        }
    }

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            val detail = withContext(Dispatchers.IO) {
                movieRepository.getMovieDetail(movieId)
            }
            _movieDetail.value = detail
        }
    }

    fun getGenreName(id: Int): String {
        return movieRepository.getGenreName(id)
    }
}