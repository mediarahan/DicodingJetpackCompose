package com.dicoding.kodlebjetpekkompos.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kodlebjetpekkompos.common.UiState
import com.dicoding.kodlebjetpekkompos.data.Contacts
import com.dicoding.kodlebjetpekkompos.repository.ContactsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel(private val repository: ContactsRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Contacts>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Contacts>>>
        get() = _uiState

    fun getAllContacts() {
        viewModelScope.launch {
            repository.getAllContact()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { allContacts ->
                    _uiState.value = UiState.Success(allContacts)
                }
        }
    }

    private val _groupedContacts = MutableStateFlow<Map<Char, List<Contacts>>>(emptyMap())
    private fun loadContacts() {
        repository.getAllContact()
            .onEach { contacts ->
                val sortedContacts = contacts
                    .sortedBy { it.name }
                    .groupBy { it.name[0] }
                _groupedContacts.value = sortedContacts
            }
            .launchIn(viewModelScope)
    }

    val groupedContacts: MutableStateFlow<Map<Char, List<Contacts>>>
        get() = _groupedContacts

    //semua diatas itu buat pake groupheroes kalau listnya Flow

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.searchContacts(_query.value).collect { searchedContacts ->
                val groupedSearchedContacts = searchedContacts
                    .sortedBy { it.name }
                    .groupBy { it.name[0].uppercaseChar() }
                _groupedContacts.value = groupedSearchedContacts
            }
        }
    }

    init {
        loadContacts()
    }
}