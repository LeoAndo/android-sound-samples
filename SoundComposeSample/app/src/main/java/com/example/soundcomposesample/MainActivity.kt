package com.example.soundcomposesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.soundcomposesample.ui.theme.SoundComposeSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val soundPlayer = MainSoundPlayer(this)
        lifecycle.addObserver(soundPlayer)
        setContent {
            SoundComposeSampleTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
                            },
                        )
                    },
                    content = {
                        // A surface container using the 'background' color from the theme
                        Surface(color = MaterialTheme.colors.background) {
                            Greeting(soundPlayer)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(soundPlayer: MainSoundPlayer) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        val modifier = Modifier.fillMaxWidth()

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = modifier,
            onClick = {
                soundPlayer.playCurtainSound()
            }) {
            Text(text = "play curtain Sound")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = modifier,
            onClick = {
                soundPlayer.stopCurtainSound()
            }) {
            Text(text = "stop curtain Sound")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = modifier,
            onClick = {
                soundPlayer.playWindowSound()
            }) {
            Text(text = "play window Sound")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = modifier,
            onClick = {
                soundPlayer.stopWindowSound()
            }) {
            Text(text = "stop window Sound")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SoundComposeSampleTheme {
        Greeting(MainSoundPlayer(LocalContext.current))
    }
}