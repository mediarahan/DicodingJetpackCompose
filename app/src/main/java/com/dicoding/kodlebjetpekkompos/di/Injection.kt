package com.dicoding.kodlebjetpekkompos.di

import com.dicoding.kodlebjetpekkompos.repository.ContactsRepository

object Injection {
    fun provideRepository(): ContactsRepository {
        return ContactsRepository.getInstance()
    }
}