package com.example.cukcuk.presentation.ui.unit.unit_list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.presentation.components.CukcukButton
import com.example.cukcuk.presentation.components.CukcukDialog
import com.example.cukcuk.presentation.components.Toolbar
import com.example.cukcuk.presentation.ui.unit.unit_form.UnitForm
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitListScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    viewModel: UnitListViewModel = hiltViewModel()

) {
    val context = LocalContext.current

    val units = viewModel.units.value
    val isOpenForm = viewModel.isFormOpen.value
    val unitUpdate = viewModel.unitUpdate.value
    val isOpenDialogDelete = viewModel.isOpenDialogDelete.value

    var unitSelectedIDArg = backStackEntry.arguments?.getString("currentUnitId")

    val unitSelected = viewModel.unitSelected.value

    val errorMessage = viewModel.errorMessage.value

    LaunchedEffect(unitSelectedIDArg) {
        if (unitSelectedIDArg != null)
            viewModel.findUnitSelected(UUID.fromString(unitSelectedIDArg))
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.setErrorMessage(null)
        }
    }

    fun submitUnitData() {
        if (unitSelected != null) {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedUnit", unitSelected)

            navController.popBackStack()
        }

    }

    Scaffold(
        topBar = {
            Toolbar(
                title = "Đơn vị tính",
                menuTitle = null,
                hasMenuIcon = true,
                onBackClick = {navController.popBackStack()},
                onMenuClick = {viewModel.openForm(null)}
            )
        },

        bottomBar = {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp)
            ) {
                CukcukButton(
                    title = "XONG",
                    bgColor = colorResource(R.color.main_color),
                    textColor = Color.White,
                    onClick = {
                        submitUnitData()
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        },

        containerColor = Color.White
    ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .background(Color.White)
            )
            {
                items(units)
                { unit ->
                    UnitItem(
                        unit,
                        unitSelected?.UnitID,
                        onSelected = {
                            viewModel.selectUnit(unit)
                        },
                        onClickEditIcon = {
                            viewModel.openForm(unit)
                        },
                        onClickDelete = {
                            viewModel.openDialogDelete(unit)
                        }
                    )
                }
            }
    }

    if (isOpenForm) {
        UnitForm(
            unitId = unitUpdate?.UnitID,
            onClose = {state ->
                viewModel.closeFormAndDialog(state)
            }
        )
    }

    if (isOpenDialogDelete) {
        CukcukDialog(
            title = stringResource(R.string.dialog_content),
            message = viewModel.buildDialogContent(),
            valueTextField = null,
            onCancel = {
                viewModel.closeFormAndDialog(false)
            },
            onConfirm = {
                viewModel.deleteUnit()
            },
            confirmButtonText = "CÓ",
            cancelButtonText = "KHÔNG",
        )
    }
}

