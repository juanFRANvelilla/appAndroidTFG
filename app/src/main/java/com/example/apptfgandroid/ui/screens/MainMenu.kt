package com.example.apptfgandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apptfgandroid.models.LoginRequestDTO
import com.example.apptfgandroid.navigation.AppScreens
import com.example.tfgapp.models.ServerResponseDTO
import com.example.tfgapp.services.RetrofitService
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Composable
fun MainMenu(
    navController: NavController,
    data: ServerResponseDTO
) {
    Box(modifier = Modifier.background(Color.Cyan)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "data")
            Spacer(modifier = Modifier.height(16.dp))
            ShowContacts(data.message)
        }
    }
}

@Composable
fun ShowContacts(token: String){
    val scope = rememberCoroutineScope()
    Button(
        onClick = {
            scope.launch {
                try {
                    println("token: $token")
                    val service = RetrofitService.showContacts(token)
                    val response = service.showContacts()

//                    errorMessage = null
//                    navController.navigate(route = AppScreens.MainMenu.route + "/" + response.toString())
                    println("respuesta contactos: $response.toString())")
                } catch (e: Exception) {
                    when (e) {
                        is HttpException -> {
                            when (e.code()) {
//                                403 -> {
//                                    errorMessage = "Credenciales incorrectas"
//                                    password = ""
//                                }
//                                else -> {
//                                    errorMessage = "Error de servidor"
//                                    password = ""
//                                }
                            }
                        }
//                        else -> {
//                            errorMessage = "Error de servidor" + e.message
//                            println(e.message)
//                            password = ""
//                        }
                    }
                }
            }
        }
    ) {
        Text("Contactos")
    }
}

@Preview
@Composable
fun preview(){
//    MainMenu()
}
