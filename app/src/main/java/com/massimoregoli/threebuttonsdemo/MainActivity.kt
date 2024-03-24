package com.massimoregoli.threebuttonsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.massimoregoli.threebuttonsdemo.ui.theme.ThreeButtonsDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThreeButtonsDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ThreeButtons()
                }
            }
        }
    }
}

@Composable
fun ThreeButtons() {
    var text by rememberSaveable { mutableStateOf("Hi Android!") }
    var state by rememberSaveable { mutableIntStateOf(1) }
    var color by rememberSaveable { mutableIntStateOf(Color.White.toArgb()) }
    var textColor by rememberSaveable { mutableIntStateOf(Color.White.toArgb()) }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        if (state == 1) {
            Activity1(text, textColor) {
                color = it.toArgb()
                state = 2
            }
        } else {
            Activity2(text, color) { t, c ->
                text = t
                if (c != -1)
                    textColor = c
                state = 1
            }
        }
    }
}

@Composable
fun Activity1(text: String,
              color: Int,
              onclick: (color: Color) -> Unit) {
    Column(modifier=Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, 
            alignment= Alignment.CenterVertically)) {


        MyButton(color = Color.Red, text = "Red") {
            onclick(Color.Red)
        }
        MyButton(color = Color.Green, text = "Green") {
            onclick(Color.Green)
        }
        MyButton(color = Color.Blue, text = "Blue") {
            onclick(Color.Blue)
        }

        Text(
            text = text,
            color = Color(color)
        )
    }
}

@Composable
fun Activity2(text: String,
              color: Int,
              onclick: (text: String, color: Int) -> Unit) {
    var value by rememberSaveable { mutableStateOf(text) }
    BackHandler {
        onclick(text, -1)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(value = value, onValueChange = { value = it },
            label={ Text(text = "Your text here")})
        MyButton(color = Color(color), text = "OK") {
            onclick(value, color)
        }
    }
}
@Composable
fun MyButton(color: Color, text: String, onclick: () -> Unit) {
    Button(
        modifier = Modifier.size(width = 120.dp, height = 40.dp),
        onClick = onclick,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {
        Text(text = text, color = Color.White)
    }
}
