package com.example.mypulltorefresh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.mypulltorefresh.model.Person
import com.example.mypulltorefresh.ui.theme.MyPullToRefreshTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyPullToRefreshTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopAppBar(title = { Text(text = "Daftar Orang") }) }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        PersonListView(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PersonListView(modifier: Modifier) {
        var people by remember { mutableStateOf(dummyPeople) }
        val pullToRefreshState = rememberPullToRefreshState()
        var isRefreshing by remember { mutableStateOf((false)) }

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                lifecycleScope.launch {
                    delay(3000)
                    people = people.shuffled()
                    isRefreshing = false
                }
            },
            state = pullToRefreshState,
            indicator = {
                Indicator(
                    isRefreshing = isRefreshing,
                    containerColor = Color.Gray,
                    color = Color.Black,
                    state = pullToRefreshState
                )
            },
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(people) { person ->
                    PersonItem(person)
                }
            }
        }
    }

    @Composable
    fun PersonItem(person: Person) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = person.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = person.country,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    val dummyPeople = listOf(
        Person("Andi", "Indonesia"),
        Person("Sakura", "Jepang"),
        Person("John", "Amerika Serikat"),
        Person("Maria", "Spanyol"),
        Person("Ali", "Arab Saudi"),
        Person("Chen", "Tiongkok"),
        Person("Lucas", "Brasil"),
        Person("Emma", "Inggris"),
        Person("Raj", "India"),
        Person("Kim", "Korea Selatan")
    )
}

