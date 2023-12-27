package com.example.apptfgandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.apptfgandroid.ui.theme.AppTfgAndroidTheme
import com.example.tfgapp.services.LoginRequestDTO
import com.example.tfgapp.services.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val service = RetrofitService.hello()
//
//        lifecycleScope.launch {
//            try {
//                val response = service.login(LoginRequestDTO(phone = "637650089", password = "juanfran"))
//                println("Respuestaaaa: " + response)
//            } catch (e: Exception) {
//                println("Error al llamar a la API: $e")
//            }
//        }

        setContent {
            AppTfgAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginForm()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelephoneTextField(onPhoneNumberChanged: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCountryCode by remember { mutableStateOf("+1") }
    var phoneNumber by remember { mutableStateOf("+34 ") }
    val countriesMap = mapOf(
        "United States" to "+1",
        "United Kingdom" to "+44",
        "Spain" to "+34"
    )

    OutlinedTextField(
        value = phoneNumber,
        onValueChange = {
            phoneNumber = it
            onPhoneNumberChanged(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        label = { Text("Telefono") },
        leadingIcon = { Icon(Icons.Default.Call, contentDescription = null) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
        trailingIcon = {
            Box(
                modifier = Modifier.clickable {
                    expanded = !expanded
                },
            ) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )

    if (expanded) {
        Column(
            Modifier
                .height(240.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text("Seleccione el país")

            LazyColumn {
                items(countriesMap.keys.toList()) { country ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedCountryCode = countriesMap[country]!!
                                phoneNumber = selectedCountryCode
                                expanded = false
                            }
                            .padding(16.dp)
                    ) {
                        Text(country)
                        Spacer(modifier = Modifier.width(8.dp))
                        if (selectedCountryCode == countriesMap[country]!!) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "País seleccionado",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(onPasswordChanged: (String) -> Unit){
    var password by remember { mutableStateOf("") }

    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
            onPasswordChanged(it) // Notify the parent composable about the password change
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Password") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = MaterialTheme.colorScheme.primary,
//                unfocusedBorderColor = contentColorFor(MaterialTheme.colorScheme.primaryVariant)
//            )
    )

}



@Composable
fun LoginForm(modifier: Modifier = Modifier) {
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("+34 ") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Acceso",
            modifier = Modifier.background(Color.DarkGray)
        )
        TelephoneTextField(onPhoneNumberChanged = {
            phoneNumber = it
        })
        PasswordTextField(onPasswordChanged = {
            password = it
        })

        Button(
            onClick = {
                scope.launch {
                    val service = RetrofitService.login()
                    val response = service.login(LoginRequestDTO(phone = phoneNumber, password = password))
                    println("Respuesta: " + response)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Login")
        }
    }
}
@Preview
@Composable
fun LoginFormPreview() {
    Surface {
        LoginForm()
    }
}