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
    onClose: () -> Unit,
    viewModel: UnitFormViewModel = hiltViewModel()
) {
    LaunchedEffect(unitId) {
        unitId?.let {
            viewModel.fetchUnitDetail(it)
        }
    }

    val unit = viewModel.unit.value
    val errorMessage = viewModel.errorMessage.value

    LaunchedEffect(errorMessage) {
        if (errorMessage == null) onClose()
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
                        onClose()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Submit")
                }
                Button(
                    onClick = onClose,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}
