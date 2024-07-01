package com.dicoding.kodlebjetpekkompos.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.kodlebjetpekkompos.common.UiState
import com.dicoding.kodlebjetpekkompos.di.Injection
import com.dicoding.kodlebjetpekkompos.factory.ViewModelFactory
import com.dicoding.kodlebjetpekkompos.ui.theme.KodlebJetpekKomposTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.kodlebjetpekkompos.fonts.CustomFonts
import com.dicoding.kodlebjetpekkompos.R

val customFonts = CustomFonts()

@Composable
fun DetailScreen(
    contactId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    ) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getContactById(contactId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailScreenContent(
                    name = data.name,
                    photoUrl = data.photoUrl,
                    description = data.description,
                    affiliation = data.affiliation,
                    onBackClick = navigateBack,
                    )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailScreenContent(
    name: String,
    photoUrl: String,
    description: String,
    affiliation: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.thorsg),
            contentDescription = null,
            alpha = 0.2f,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                fontFamily = customFonts.montserratFontFamily,
                text = "Student Identification",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge.copy()
            )
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = debugPlaceholder(R.drawable.rean),
                    modifier = Modifier
                        .height(280.dp)
                        .fillMaxWidth()
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .size(48.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                val annotatedStringName = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append(stringResource(R.string.name_text))
                    }
                    append(" $name")
                }
                Text(
                    text = annotatedStringName,
                    modifier = Modifier
                        .padding(start = 12.dp),
                    style = MaterialTheme.typography.headlineSmall,
                )
                Divider(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                )
                val annotatedStringClass = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append(stringResource(R.string.class_text))
                    }
                    append(" ")
                    append(stringResource(R.string.class_name_text))
                }
                Text(
                    text = annotatedStringClass,
                    modifier = Modifier
                        .padding(start = 12.dp),
                    style = MaterialTheme.typography.headlineSmall,
                )
                Divider(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                )
                val annotatedStringAffiliation = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append(stringResource(R.string.affiliation_text))
                    }
                    append(" $affiliation")
                }
                Text(
                    text = annotatedStringAffiliation,
                    modifier = Modifier
                        .padding(start = 12.dp),
                    style = MaterialTheme.typography.headlineSmall,
                )
                Divider(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                )
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Justify,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                )
            }
        }
    }
}

@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) =
    if (LocalInspectionMode.current) {
        painterResource(id = debugPreview)
    } else {
        null
    }

@Composable
@Preview(showBackground = true)
fun DetailScreenContentPreview() {
    KodlebJetpekKomposTheme {
        DetailScreenContent(
            name = "Rean Schwarzer",
            photoUrl = "https://static.wikia.nocookie.net/kiseki/images/1/1c/Rean_Schwarzer_Note_%28Sen%29.png/revision/latest?cb=20230323201130",
            description = "A young man who is part of Class VII. Easygoing, honest, polite, and sociable. He achieved beginner rank in the Eight Leaves One Blade school, and he is also the adopted son of Ymir's Baron Schwarzer.",
            affiliation = "Unaffiliated",
            onBackClick = {},
        )
    }
}