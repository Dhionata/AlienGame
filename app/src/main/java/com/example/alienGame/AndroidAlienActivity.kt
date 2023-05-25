package com.example.alienGame

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.alienGame.ui.theme.QualMelhorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QualMelhorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
            AndroidAliensWithInfo()
        }
    }
}

data class AndroidAlien(val color: Color, val weight: Float, val padding: Dp)

@Composable
fun AndroidAlien(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier.clickable {},
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        colorFilter = ColorFilter.tint(color = color),
        contentDescription = "Alien"
    )
}

@Composable
fun androidAliens(): List<AndroidAlien> {
    return listOf(
        AndroidAlien(
            color = Color.Green,
            weight = 1F,
            padding = 4.dp
        ),
        AndroidAlien(
            color = Color.Magenta,
            weight = 2F,
            padding = 0.dp
        ),
        AndroidAlien(
            color = Color.Red,
            weight = 1F,
            padding = 4.dp
        )
    )
}

@Composable
fun AndroidAliensRow() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
    ) {
        androidAliens().forEach { alien ->
            AndroidAlien(
                color = alien.color,
                modifier = Modifier
                    .weight(alien.weight)
                    .padding(alien.padding)
            )
        }
    }
}

@Composable
fun AndroidAliensColumn() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        androidAliens().forEach { alien ->
            AndroidAlien(
                color = alien.color,
                modifier = Modifier
                    .weight(alien.weight)
                    .padding(alien.padding)
            )
        }
    }
}


@Composable
fun AndroidAliensGameOverBox() {
    var clicked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(color = Color.Gray)
        )
        Button(onClick = {
            Log.i("1", "Botão do GAME OVER")
            clicked = true
        }) {
            Text(text = "GAME OVER")
        }
        if (clicked) {
            AndroidAliensWithInfo()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Counter(
    count: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    var oldCount by remember {
        mutableStateOf(count)
    }
    SideEffect {
        oldCount = count
    }
    Row(modifier = modifier) {
        val countString = count.toString()
        val oldcountString = oldCount.toString()
        for (i in countString.indices) {
            val oldChar = oldcountString.getOrNull(i)
            val newChar = countString[i]
            val char = if (oldChar == newChar) {
                oldcountString[i]
            } else {
                countString[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = { slideInVertically { it } with slideOutVertically { -it } }
            ) {
                Text(text = it.toString(), style = style, softWrap = false)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AndroidAliensWithInfo() {
    var clicked by remember { mutableStateOf(false) }
    val count by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            Text(
                text = "Total clicks: ${Counter(count = count)}",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleSmall
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    Log.i("2", "Clicaram no botão PRESS START")
                    clicked = true
                },
                Modifier.padding(start = 130.dp, top = 300.dp)
            ) {
                Text(text = "PRESS START")
            }
            if (clicked) {
                AndroidAliensGameOverBox()
            }
        }
    ) {
        AndroidAliensRow()
        AndroidAliensColumn()
    }
}

@Composable
fun AndroidAliensGrid(modifier: Modifier = Modifier) {
    LazyVerticalGrid(columns = GridCells.Fixed(5), modifier = modifier) {
        items(50) {
            AndroidAlien(color = Color.Cyan)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultPreview() {
    AndroidAliensWithInfo()
}
