package com.example.presentation.ui.unit.unit_list

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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavHostController
import com.example.presentation.R
import com.example.presentation.components.CukcukButton
import com.example.presentation.components.CukcukDialog
import com.example.presentation.components.CukcukToolbar
import com.example.presentation.ui.unit.unit_form.UnitForm


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitListScreen(
    navController: NavHostController,
    viewModel: UnitListViewModel = hiltViewModel()

) {
    val context = LocalContext.current

    val units = viewModel.units.value
    val isOpenForm = viewModel.isFormOpen.value
    val unitUpdate = viewModel.unitUpdate.value
    val isOpenDialogDelete = viewModel.isOpenDialogDelete.value

    val unitSelected = viewModel.unitSelected.value
    val errorMessage = viewModel.errorMessage.value


    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.setErrorMessage(null)
        }
    }

    fun selectUnitData() {
        if (unitSelected != null) {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedUnit", unitSelected)

            navController.popBackStack()
        }
        else {
            Toast.makeText(context, context.getString(R.string.toast_not_select_unit_error) , Toast.LENGTH_SHORT).show()
        }
    }

    @Composable
    fun buildDialogContent() : AnnotatedString {
        val first = stringResource(id = R.string.annotate_delete_unit_first)
        val last = stringResource(id = R.string.annotate_delete_unit_last)

        return buildAnnotatedString {
            append(first)
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(" ${unitUpdate?.UnitName} ")
            }
            append(last)
        }
    }

    Scaffold(
        topBar = {
            CukcukToolbar(
                title = stringResource(R.string.toolbar_title_UnitList),
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
                    title = stringResource(R.string.button_title_Done),
                    bgColor = colorResource(R.color.main_color),
                    textColor = Color.White,
                    onClick = {
                        selectUnitData()
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
            message = buildDialogContent(),
            valueTextField = null,
            onCancel = {
                viewModel.closeFormAndDialog(false)
            },
            onConfirm = {
                viewModel.deleteUnit()
            },
            confirmButtonText = stringResource(R.string.button_title_Yes),
            cancelButtonText = stringResource(R.string.button_title_No),
        )
    }
}

