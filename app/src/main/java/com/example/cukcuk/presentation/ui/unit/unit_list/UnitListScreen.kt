package com.example.cukcuk.presentation.ui.unit.unit_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.presentation.components.CukcukButton
import com.example.cukcuk.presentation.components.CukcukDialog
import com.example.cukcuk.presentation.components.CukcukImageBox
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
    val units = viewModel.units.value
    val isOpenForm = viewModel.isFormOpen.value
    val unitUpdate = viewModel.unitUpdate.value
    val isOpenDialogDelete = viewModel.isOpenDialogDelete.value

    var unitSelectedIDArg = backStackEntry.arguments?.getString("currentUnitId")

    val unitSelected = viewModel.unitSelected.value

    LaunchedEffect(unitSelectedIDArg) {
        if (unitSelectedIDArg != null)
            viewModel.findUnitSelected(UUID.fromString(unitSelectedIDArg))
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
                hasMenuAction = true,
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
            message = "Bạn có chắc muốn xóa đơn vị tính không",
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

@Composable
fun UnitItem(
    unit: com.example.cukcuk.domain.model.Unit,
    unitSelectedId: UUID?,
    onSelected: () -> Unit,
    onClickDelete: () -> Unit,
    onClickEditIcon: () -> Unit) {

    val showMenu = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            }
            .combinedClickable(
                onClick = {onSelected()}
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.width(40.dp),
            contentAlignment = Alignment.Center
        ) {
            if (unit.UnitID == unitSelectedId) {
                CukcukImageBox(
                    size = 28,
                    imageSize = 20,
                    iconDrawable = painterResource(R.drawable.ic_yes),
                    colorRes = colorResource(R.color.color_unit_selected)
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .combinedClickable(
                    onClick = {onSelected()},
                    onLongClick = { showMenu.value = true }
                )
        ) {
            Text(
                text = unit.UnitName,
                modifier = Modifier.fillMaxHeight().wrapContentHeight()
            )

            DropdownMenu(
                expanded = showMenu.value,
                onDismissRequest = { showMenu.value = false },
                modifier = Modifier
                    .height(48.dp)
                    .width(IntrinsicSize.Min)
                    .background(
                        color = Color(0xFFEEEEEE),
                        shape = RoundedCornerShape(2.dp)
                    )
                    .clip(RoundedCornerShape(2.dp))
            ) {
                DropdownMenuItem(
                    modifier = Modifier.height(32.dp),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.outline_close_24),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    },
                    text = {
                        Text(
                            text =  "Xoá đơn vị tính",
                            modifier = Modifier.fillMaxHeight().wrapContentHeight(Alignment.CenterVertically)
                        )
                    },
                    onClick = {
                        showMenu.value = false
                        onClickDelete()
                    }
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_edit_pencil),
            contentDescription = "Ảnh ví dụ",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(28.dp)
                .clickable{onClickEditIcon()}
        )
    }
}

