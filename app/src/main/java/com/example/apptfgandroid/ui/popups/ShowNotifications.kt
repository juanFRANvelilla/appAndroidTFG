package com.example.apptfgandroid.ui.popups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.apptfgandroid.models.UserDTO
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.apptfgandroid.models.ContactRequestDTO
import com.example.apptfgandroid.ui.screens.ManageContacts.getUsersExample

//@Preview
//@Composable
//fun sdfdsfsssss(){
//    ShowNotifications(onDismiss = { /*TODO*/ }, request = getUsersExample().toList())
//}

@Composable
fun ShowNotifications(
    onDismiss: () -> Unit,
    request: List<UserDTO>,
    onAcceptRequest: (ContactRequestDTO) -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        }
        ) {
        Box(
            modifier = Modifier
//                .padding(30.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(14.dp)
                )

        ){
            LazyColumn(
                modifier = Modifier
                    .padding(14.dp)
            ) {
                items(request) { user ->
                    Column {
                        Text(text = user.username, color = Color.Red)
                        Text(text = "${user.firstName} ${user.lastName} ha solicitado ser tu contacto")
                        Button(
                            onClick = {
                                val contactRequestDTO = ContactRequestDTO(username = user.username)
                                onAcceptRequest(contactRequestDTO)
                            }
                        ) {
                            Text(text = "ACEPTAR")
                        }
                    }
                }
            }
        }
    }
}
