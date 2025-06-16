package com.example.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.presentation.R

@Composable
fun CukcukDialog(
    title: String,
    message: AnnotatedString? = null,
    valueTextField: String? = null,
    placeHolderValue: String? = null,
    colorBorderTextField: Color? = null,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    confirmButtonText: String,
    cancelButtonText: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    buttonTextSize: Int = 16
) {
    Dialog(onDismissRequest = onCancel) {
        Surface(
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
        ) {
            Column(
                Modifier.background(Color.LightGray)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(R.color.main_color))
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.wrapContentHeight()
                    )

                    Icon(
                        painter = painterResource(R.drawable.quit_icon),
                        contentDescription = "Close",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .combinedClickable(onClick = onCancel)
                            .padding(10.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(10.dp)
                        .heightIn(min = 60.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (message != null) {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    if (valueTextField != null) {

                        BasicTextField(
                            value = valueTextField,
                            onValueChange = {
                                onValueChange?.invoke(it)
                            },
                            maxLines = 1,
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.Black,
                                fontSize = 16.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .border(
                                    width =  1.dp,
                                    color = colorBorderTextField ?: Color.Transparent,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 10.dp),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (valueTextField.isEmpty() && placeHolderValue!=null) {
                                        Text(
                                            text = placeHolderValue,
                                            color = Color.Gray,
                                            fontSize = 16.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            },
                            cursorBrush = SolidColor(colorResource(R.color.main_color)),
                            keyboardOptions = KeyboardOptions.Default
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(R.color.dialog_footer_color)
                        )
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End)
                ) {

                    Spacer(modifier = Modifier.weight(3f))

                    if (cancelButtonText != null) {
                        CukcukButton(
                            title = cancelButtonText,
                            bgColor = Color.White,
                            textColor = Color.Red,
                            onClick = onCancel,
                            modifier = Modifier.weight(2f),
                            fontSize = buttonTextSize
                        )
                    } else Spacer(modifier = Modifier.weight(2f))

                    CukcukButton(
                        title = confirmButtonText,
                        bgColor = colorResource(R.color.main_color),
                        textColor = Color.White,
                        onClick = onConfirm,
                        modifier = Modifier.weight(2f),
                        fontSize = buttonTextSize
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewCukcukDialogContent() {

    MaterialTheme {
        CukcukDialog(
            title = "Xác nhận xóa",
            message = null,
            valueTextField = null,
            onConfirm = {},
            onCancel = {},
            confirmButtonText = "CÓ",
            cancelButtonText = "KHÔNG",
            onValueChange = {it -> println(it)}
        )
    }
}
