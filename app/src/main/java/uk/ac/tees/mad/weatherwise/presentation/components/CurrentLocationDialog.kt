package uk.ac.tees.mad.weatherwise.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CurrentLocationDialog(
    onSave: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var city by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Reset Password") },
        text = {
            Column {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = country,
                    onValueChange = { country = it },
                    label = { Text("Country") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(city, country)
                    onDismiss()
                }
            ) {
                Text("Ok")
            }
        }
    )
}