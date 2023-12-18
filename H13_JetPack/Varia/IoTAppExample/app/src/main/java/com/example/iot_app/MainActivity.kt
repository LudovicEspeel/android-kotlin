package com.example.iot_app

import android.content.Context
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModelProvider
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.iot_app.ui.theme.IoT_App_Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit

class MainActivity : ComponentActivity() {

    private val USER_PREFERENCES_NAME = "user_preferences"
    private val LAMP1_ON_KEY = "lamp1_on"

    val dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

    val LAMP1_ON = booleanPreferencesKey(LAMP1_ON_KEY)

    suspend fun saveLamp1State(on: Boolean) {
        dataStore.edit { settings ->
            settings[LAMP1_ON] = on
        }
    }

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lamp1OnFlow: Flow<Boolean> = dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[LAMP1_ON] ?: true
            }

        var lamp1On=false
        runBlocking {
            lamp1On = lamp1OnFlow.first()
        }

        viewModel = ViewModelProvider(this, MainViewModelFactory(lamp1On)).get(MainViewModel::class.java)

        setContent {
            IoT_App_Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Lamp(viewModel.lamp1_on.value, {
                             viewModel.setLamp1On(it)
                             Log.d("Change lamp 1",if(it) "on" else "off")
                        })
                        Lamp()
                        Lamp()
                        Button(onClick =  { lifecycleScope.launch { saveLamp1State(viewModel.lamp1_on.value) }}) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Lamp(on: Boolean = false, toggle: (Boolean) -> Unit = {}) {
    Card {
        Image(painter= painterResource(id = if (on) R.drawable.lamp else R.drawable.lampoff),
            contentDescription = "Lamp",
            modifier=Modifier.background(Color.Blue)
        )
        Switch(checked = on, onCheckedChange = toggle )
    }
}


@Preview(showBackground = true)
@Composable
fun LampPreview() {
    IoT_App_Theme {
        Surface(
            //modifier = Modifier.fillMaxSize(),
            //color = MaterialTheme.colorScheme.background
        ) {
           Lamp(true)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    IoT_App_Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Lamp()
                Lamp()
                Lamp()
            }
        }
    }
}