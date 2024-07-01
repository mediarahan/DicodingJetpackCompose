package com.dicoding.kodlebjetpekkompos.repository

import com.dicoding.kodlebjetpekkompos.data.Contacts
import com.dicoding.kodlebjetpekkompos.data.ContactsData
import com.dicoding.kodlebjetpekkompos.data.Profile
import com.dicoding.kodlebjetpekkompos.data.ProfileData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ContactsRepository {

    private val allContacts = mutableListOf<Contacts>()

    fun getAllContact(): Flow<List<Contacts>> {
        return flowOf(allContacts)
    }

    fun getContactById(contactId: Long): Contacts {
        return allContacts.first {
            it.id == contactId
        }
    }

  fun getProfileInfo(): Profile {
      return ProfileData.profile
    }

    init {
        ContactsData.contacts.forEach {contact ->
            val newContactList = Contacts (
                id = contact.id,
                name = contact.name,
                photoUrl = contact.photoUrl,
                description = contact.description,
                affiliation = contact.affiliation
            )
            allContacts.add(newContactList)
        }
    }

    fun searchContacts(query: String): Flow<List<Contacts>> {
        return flowOf(ContactsData.contacts)
            .map {contact ->
                contact.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }
    }

    companion object {
        @Volatile
        private var instance: ContactsRepository? = null

        fun getInstance(): ContactsRepository =
            instance ?: synchronized(this) {
                ContactsRepository().apply {
                    instance = this
                }
            }
    }
}