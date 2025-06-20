package com.example.presentation.ui.calculator

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.components.CukcukButton
import com.example.presentation.components.CukcukImageButton
import com.example.presentation.theme.CukcukTheme
import com.example.domain.utils.FormatDisplay
import com.example.presentation.enums.CalculatorButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun IntegerCalculatorDialog(
    input: String = "0.0",
    title: String = "",
    message: String = "",
    maxValue: Double = 999999999.0,
    minValue: Double = 0.0,
    onClose: () -> Unit,
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
        listOf(
            CalculatorButton.DECREASE,
            CalculatorButton.INCREASE,
            CalculatorButton.CLEAR
        ),
        listOf(
            CalculatorButton.SEVEN,
            CalculatorButton.EIGHT,
            CalculatorButton.NINE
        ),
        listOf(
            CalculatorButton.FOUR,
            CalculatorButton.FIVE,
            CalculatorButton.SIX
        ),
        listOf(
            CalculatorButton.ONE,
            CalculatorButton.TWO,
            CalculatorButton.THREE
        )
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
            Modifier.background(Color.LightGray)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.main_color))
                    .padding(6.dp),
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
                    modifier = Modifier.combinedClickable(onClick = {
                        onClose()
                    })
                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                    )
                    .padding(vertical = 10.dp, horizontal = 5.dp),
            ) {
                Row(
                    modifier = Modifier
                        .weight(2f)
                        .height(48.dp)
                        .padding(horizontal = 5.dp)
                        .border(
                            width = (1/2).dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(2.dp)
                        )
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = FormatDisplay.formatExpression(resultValue),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                }

                CukcukImageButton(
                    title = stringResource(CalculatorButton.DELETE.label),
                    onClick = {
                        viewModel.onClickButton(CalculatorButton.DELETE, context)
                    },
                    bgColor = colorResource(R.color.calculator_button),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .padding(horizontal = 5.dp)
                        .border(
                            width = (1/2).dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    icon = painterResource(R.drawable.ic_remove)
                )
            }

            keys.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                        )
                        .padding(vertical = 10.dp, horizontal = 5.dp),
                ) {
                    row.forEach { key ->
                        CukcukButton(
                            title = stringResource(key.label),
                            onClick = {
                                viewModel.onClickButton(key, context)
                            },
                            bgColor = colorResource(R.color.calculator_button),
                            textColor = Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .padding(horizontal = 5.dp)
                                .border(
                                    width = (1/2).dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                    )
                    .padding(5.dp),
            ) {
                CukcukButton(
                    title = stringResource(CalculatorButton.ZERO.label),
                    onClick = {
                        viewModel.onClickButton(CalculatorButton.ZERO, context)
                    },
                    bgColor = colorResource(R.color.calculator_button),
                    textColor = Color.Black,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .padding(horizontal = 5.dp)
                        .border(
                            width = (1/2).dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        )
                )

                CukcukButton(
                    title = stringResource(CalculatorButton.DONE.label),
                    onClick = {
                        viewModel.onClickButton(CalculatorButton.DONE, context)
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

@Preview
@Composable
fun IntegerCalculatorDialogPreview() {
    CukcukTheme {
        IntegerCalculatorDialog(
            input = "0.0",
            title = "Nhập số người",
            onClose = {},
            onSubmit = {}
        )
    }
}