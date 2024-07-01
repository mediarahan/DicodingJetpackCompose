package com.dicoding.kodlebjetpekkompos.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kodlebjetpekkompos.common.UiState
import com.dicoding.kodlebjetpekkompos.data.Contacts
import com.dicoding.kodlebjetpekkompos.data.Profile
import com.dicoding.kodlebjetpekkompos.repository.ContactsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ContactsRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Profile>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Profile>>
        get() = _uiState

    fun getProfile() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getProfileInfo())
        }
    }
}
