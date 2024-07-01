package com.dicoding.kodlebjetpekkompos.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kodlebjetpekkompos.common.UiState
import com.dicoding.kodlebjetpekkompos.data.Contacts
import com.dicoding.kodlebjetpekkompos.repository.ContactsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: ContactsRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Contacts>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Contacts>>
        get() = _uiState

    fun getContactById(contactsId: Long) {
        viewModelScope.launch {
            repository.getAllContact()
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getContactById(contactsId))
        }
    }
}
