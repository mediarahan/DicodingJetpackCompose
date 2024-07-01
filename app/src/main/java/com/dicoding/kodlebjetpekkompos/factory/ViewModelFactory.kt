package com.dicoding.kodlebjetpekkompos.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.kodlebjetpekkompos.repository.ContactsRepository
import com.dicoding.kodlebjetpekkompos.screen.detail.DetailViewModel
import com.dicoding.kodlebjetpekkompos.screen.home.HomeViewModel
import com.dicoding.kodlebjetpekkompos.screen.profile.ProfileViewModel

class ViewModelFactory(private val repository: ContactsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}