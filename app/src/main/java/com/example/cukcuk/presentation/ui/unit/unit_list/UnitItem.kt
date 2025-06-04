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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cukcuk.R
import com.example.cukcuk.presentation.components.CukcukImageBox
import java.util.UUID

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
                    colorRes = colorResource(R.color.unit_selected)
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