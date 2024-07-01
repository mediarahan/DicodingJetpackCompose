package com.dicoding.kodlebjetpekkompos.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.kodlebjetpekkompos.common.UiState
import com.dicoding.kodlebjetpekkompos.data.Contacts
import com.dicoding.kodlebjetpekkompos.di.Injection
import com.dicoding.kodlebjetpekkompos.factory.ViewModelFactory
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dicoding.kodlebjetpekkompos.R

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val searchedContacts by viewModel.groupedContacts.collectAsState()
    val query by viewModel.query

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllContacts()
            }

            is UiState.Success -> {
                HomeScreenContent(
                    query = query,
                    searchedContacts = searchedContacts, //AWAS NI ANAK JADI BIANG KELADI
                    viewModel = viewModel,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarElement(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(R.string.search_text))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .heightIn(min = 48.dp)
    ) {
    }
}

@Composable
fun CharacterHeader(
    char: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Text(
            text = char,
            fontWeight = FontWeight.Black,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    query: String,
    searchedContacts: Map<Char, List<Contacts>>,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    Box(modifier = modifier) {
        LazyColumn {
            item {
                SearchBarElement(
                    query = query,
                    onQueryChange = viewModel::search,
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                )
            }
            searchedContacts.forEach { (initial, contacts) ->
                stickyHeader {
                    CharacterHeader(initial.toString())
                }
                items(contacts) { contact ->
                    ContactListItem(
                        name = contact.name,
                        photoUrl = contact.photoUrl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(tween(durationMillis = 100))
                            .clickable { navigateToDetail(contact.id) }
                    )
                }
            }
        }
    }
}