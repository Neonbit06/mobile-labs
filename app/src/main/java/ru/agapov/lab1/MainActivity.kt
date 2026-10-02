package ru.agapov.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.agapov.lab1.ui.theme.Lab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PlaceholderScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PlaceholderScreen(modifier: Modifier = Modifier) {
    Text(
        text = "Lab 1 — List Search",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PlaceholderScreenPreview() {
    Lab1Theme {
        PlaceholderScreen()
    }
}