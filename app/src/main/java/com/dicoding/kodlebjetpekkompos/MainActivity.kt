package com.dicoding.kodlebjetpekkompos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.ui.Modifier
import com.dicoding.kodlebjetpekkompos.ui.theme.KodlebJetpekKomposTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KodlebJetpekKomposTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JetContactApp()
                }
            }
        }
    }
}