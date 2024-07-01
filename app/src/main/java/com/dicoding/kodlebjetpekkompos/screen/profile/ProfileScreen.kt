package com.dicoding.kodlebjetpekkompos.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.kodlebjetpekkompos.R
import com.dicoding.kodlebjetpekkompos.common.UiState
import com.dicoding.kodlebjetpekkompos.di.Injection
import com.dicoding.kodlebjetpekkompos.factory.ViewModelFactory
import com.dicoding.kodlebjetpekkompos.screen.detail.DetailScreenContent
import com.dicoding.kodlebjetpekkompos.screen.detail.DetailViewModel
import com.dicoding.kodlebjetpekkompos.ui.theme.KodlebJetpekKomposTheme

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getProfile()
            }

            is UiState.Success -> {
                val profileData = uiState.data
                ProfileScreenContent(
                    profileImage = profileData.profileImage,
                    profileName = profileData.name,
                    profileSubtitle = profileData.email,
                    onBackClick = navigateBack,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun ProfileScreenContent(
    profileImage: Int,
    profileName: String,
    profileSubtitle: String,
    onBackClick: () -> Unit,
) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = stringResource(R.string.back),
        modifier = Modifier
            .padding(16.dp)
            .size(48.dp)
            .clickable { onBackClick() }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(profileImage),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(192.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = profileName,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = profileSubtitle,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    KodlebJetpekKomposTheme {
        ProfileScreenContent(
            profileImage = R.drawable.rioyh,
            profileName = stringResource(R.string.name),
            profileSubtitle = stringResource(R.string.email),
        ) {
        }
    }
}