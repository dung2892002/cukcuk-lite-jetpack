package com.example.presentation.ui.calculator

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.components.CukcukButton
import com.example.presentation.components.CukcukImageButton
import com.example.presentation.theme.CukcukTheme
import com.example.domain.utils.FormatDisplay
import org.koin.androidx.compose.koinViewModel

@Composable
fun DoubleCalculatorDialog(
    input: String = "0.0",
    message: String = "",
    maxValue: Double = 999999999.0,
    minValue: Double = 0.0,
    onSubmit: (String) -> Unit,
    viewModel: CalculatorViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val resultValue = viewModel.resultValue.value
    val submitState = viewModel.submitState.value
    val messageError = viewModel.errorMessage.value

    LaunchedEffect(Unit) {
        viewModel.setInitState(input,minValue, maxValue, message)
    }

    LaunchedEffect(messageError) {
        if (messageError != null) {
            Toast.makeText(context, messageError, Toast.LENGTH_SHORT).show()
            viewModel.setMessageError(null)
        }
    }

    LaunchedEffect(submitState) {
        if (submitState == true) {
            onSubmit(resultValue)
            viewModel.setSubmitState(false)
        }
    }

    val keys = listOf(
        listOf("7", "8", "9", "Xóa"),
        listOf("4", "5", "6", "C"),
        listOf("1", "2", "3", ","),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable(enabled = false) {}
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = FormatDisplay.formatExpression(resultValue),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }

            Column(
                modifier = Modifier
                    .background(
                        color = Color.LightGray
                    )
                    .padding(5.dp)
            ) {
                keys.forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp, horizontal = 5.dp),
                    ) {
                        row.forEach { key ->
                            if (key == "Xóa") {
                                CukcukImageButton(
                                    title = "Xóa",
                                    onClick = {
                                        viewModel.onClickButton("Xóa")
                                    },
                                    bgColor = Color.White,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                        .padding(horizontal = 5.dp),
                                    icon = painterResource(R.drawable.ic_remove)
                                )
                            }
                            else {
                                CukcukButton(
                                    title = key,
                                    onClick = {
                                        viewModel.onClickButton(key)
                                    },
                                    bgColor = Color.White,
                                    textColor = Color.Black,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                        .padding(horizontal = 5.dp)
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 5.dp),
                ) {
                    CukcukButton(
                        title = "0",
                        onClick = {
                            viewModel.onClickButton("0")
                        },
                        bgColor = Color.White,
                        textColor = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .padding(horizontal = 5.dp)
                    )

                    CukcukButton(
                        title = "000",
                        onClick = {
                            viewModel.onClickButton("000")
                        },
                        bgColor = Color.White,
                        textColor = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .padding(horizontal = 5.dp)
                    )

                    CukcukButton(
                        title = "ĐỒNG Ý",
                        onClick = {
                            viewModel.onClickButton("XONG")
                        },
                        bgColor = colorResource(R.color.main_color),
                        textColor = Color.White,
                        modifier = Modifier
                            .weight(2f)
                            .height(48.dp)
                            .padding(horizontal = 5.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DoubleCalculatorDialogPreview() {
    CukcukTheme {
        DoubleCalculatorDialog(
            input = "0.0",
            onSubmit = {}
        )
    }
}