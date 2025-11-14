package com.example.seminar_assignment_2025.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seminar_assignment_2025.data.Movie
import com.example.seminar_assignment_2025.data.MovieRepository
import com.example.seminar_assignment_2025.data.SearchHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
            searchHistoryRepository.addSearchTerm(term)
        }
    }

    fun removeSearchTerm(term: String) {
        viewModelScope.launch {
            searchHistoryRepository.removeSearchTerm(term)
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            searchHistoryRepository.clearSearchHistory()
        }
    }

    fun searchByTitle(query: String) {
        viewModelScope.launch {
            _searchResults.value = movieRepository.searchByTitle(query)
        }
    }

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _movieDetail.value = movieRepository.getMovieDetail(movieId)
        }
    }

    fun getGenreName(id: Int): String {
        return movieRepository.getGenreName(id)
    }
}