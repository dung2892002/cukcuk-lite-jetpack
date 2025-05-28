package com.example.cukcuk.presentation.ui.unit.unit_form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.UUID

@Composable
fun UnitForm(
    unitId: UUID?,
    onClose: (Boolean) -> Unit,
    viewModel: UnitFormViewModel = hiltViewModel()
) {

    LaunchedEffect(unitId) {
        println("id: $unitId")
        if (unitId == null) {
            println("Theem moi")
        }
        else {
            println("Cap nhat")
            viewModel.fetchUnitDetail(unitId)
        }
    }

    val unit = viewModel.unit.value
    val errorMessage = viewModel.errorMessage.value

    fun closeForm(reload: Boolean) {
        viewModel.resetValue()
        onClose(reload)
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage == null) {
            closeForm(true)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = unit.UnitName,
                onValueChange = { newName ->
                    viewModel.updateNewUnitName(name = newName)
                },
                label = { Text("Unit Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = {
                        viewModel.submitForm()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Submit")
                }
                Button(
                    onClick = {
                        closeForm(false)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}
