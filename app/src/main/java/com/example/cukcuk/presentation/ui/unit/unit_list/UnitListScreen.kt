package com.example.cukcuk.presentation.ui.unit.unit_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.cukcuk.presentation.ui.unit.unit_form.UnitForm


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitListScreen(
    viewModel: UnitListViewModel = hiltViewModel()

) {
    val units = viewModel.units.value
    val isOpenForm = viewModel.isFormOpen.value
    val unitUpdateID = viewModel.unitUpdateID.value


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Danh sách đơn vị")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {viewModel.backScreen()}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {viewModel.openForm(null)}) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Thêm mới"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(units) { unit ->
                    Text(
                        text = unit.UnitName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.openForm(unit.UnitID)
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    }

    if (isOpenForm) {
        UnitForm(
            unitId = unitUpdateID,
            onClose = {
                viewModel.closeForm(true)
            }
        )
    }
}

