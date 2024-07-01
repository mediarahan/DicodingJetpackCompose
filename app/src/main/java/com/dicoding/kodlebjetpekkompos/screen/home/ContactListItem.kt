package com.dicoding.kodlebjetpekkompos.screen.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.kodlebjetpekkompos.ui.theme.KodlebJetpekKomposTheme

@Composable
fun ContactListItem(
    name: String,
    photoUrl: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ContactListItemPreview() {
    KodlebJetpekKomposTheme {
        ContactListItem("Rean Schwarzer",
            "https://static.wikia.nocookie.net/kiseki/images/1/1c/Rean_Schwarzer_Note_%28Sen%29.png/revision/latest?cb=20230323201130"
            ,)
    }
}