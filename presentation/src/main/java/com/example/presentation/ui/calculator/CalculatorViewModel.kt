package com.example.presentation.ui.calculator

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.domain.utils.FormatDisplay
import com.example.presentation.R
import com.example.presentation.enums.CalculatorButton
import kotlin.text.toDouble

class CalculatorViewModel : ViewModel() {
    private val _resultValue = mutableStateOf("")
    val resultValue: State<String> = _resultValue

    private val _submitState = mutableStateOf(false)
    val submitState: State<Boolean> = _submitState

    private val _isCalculateState = mutableStateOf(false)
    val isCalculateState: State<Boolean> = _isCalculateState

    private val _minValue = mutableDoubleStateOf(0.0)

    private val _maxValue = mutableDoubleStateOf(0.0)

    private val _maxLengthValue = mutableIntStateOf(10)

    private val _message = mutableStateOf("")

    private var firstInput = true

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage


    fun onClickButton(key: CalculatorButton, context: Context) {
        val label = context.getString(key.label)
        if (firstInput) {
            if (label.all { it.isDigit() } || key == CalculatorButton.TRIPLE_ZERO) {
                var input = _resultValue.value
                input = if (key == CalculatorButton.TRIPLE_ZERO) "0" else label
                firstInput = false
                _resultValue.value = input
                return
            } else {
                firstInput = false
            }
        }
        when (key) {
            CalculatorButton.CLEAR -> setValue("0")
            CalculatorButton.DELETE -> {
                val tmp = _resultValue.value.dropLast(1)
                setValue( tmp.ifEmpty { "0" })
            }
            CalculatorButton.DECREASE -> {
                var input = _resultValue.value
                input = evaluate("$input-1")
                _resultValue.value = input
                setCalculateState(false)
            }
            CalculatorButton.INCREASE -> {
                var input = _resultValue.value
                input = evaluate("$input+1")
                _resultValue.value = input
                setCalculateState(false)
            }
            CalculatorButton.ADD -> {
                var input = _resultValue.value
                setCalculateState(true)
                if (input.last() == '+' || input.last() == '.' || input.last() == '-') {
                    input = input.dropLast(1)
                }
                input += "+"
                _resultValue.value = input
            }
            CalculatorButton.SUBTRACT -> {
                var input = _resultValue.value
                setCalculateState(true)
                if (input.last() == '+' || input.last() == '.' || input.last() == '-') {
                    input = input.dropLast(1)
                }
                input += "-"
                _resultValue.value = input
            }
            CalculatorButton.EQUAL -> {
                var input = _resultValue.value
                input = evaluate(input)
                setCalculateState(false)
                _resultValue.value = input
            }
            CalculatorButton.TOGGLE_SIGN -> {
                var input = _resultValue.value
                input = evaluate(input)
                if (input.first() == '-') input.drop(1)
                else input = "-$input"
                setCalculateState(false)
                _resultValue.value = input
            }
            CalculatorButton.DECIMAL -> {
                var input = _resultValue.value
                if (isValidDecimalInput(input)) input += "."
                _resultValue.value = input
            }
            CalculatorButton.DONE -> {
                handleSubmit(context)
            }
            else -> {
                var input = _resultValue.value
                if (input == "0") {
                    if (key == CalculatorButton.ZERO || key == CalculatorButton.TRIPLE_ZERO) return
                } else {
                    if (!hasTwoDecimalPlaces(input) && checkMaxLengthInput()) {
                        input += label
                    }
                }
                _resultValue.value = input
            }
        }
    }

    private fun handleSubmit(context: Context) {
        if (resultValue.value.toDouble() > _maxValue.doubleValue) {
            var msgError = "${_message.value} ${context.getString(R.string.calculator_error_maxValue)} ${FormatDisplay.formatNumber(_maxValue.doubleValue.toString())}"
            setMessageError(msgError)
            return
        }
        if (resultValue.value.toDouble() < _minValue.doubleValue) {
            var msgError = "${_message.value} ${context.getString(R.string.calculator_error_minValue)} ${FormatDisplay.formatNumber(_minValue.doubleValue.toString())}"
            setMessageError(msgError)
            return
        }
       _submitState.value = true
    }

    fun setInitState(value: String, min : Double, max: Double, message: String) {
        var initialValue = value
        while (initialValue.endsWith("0") && initialValue.contains('.')) {
            initialValue = initialValue.dropLast(1)
        }
        if (initialValue.endsWith(".")) {
            initialValue = initialValue.dropLast(1)
        }
        _resultValue.value = initialValue
        _minValue.doubleValue = min
        _maxValue.doubleValue = max
        _submitState.value = false
        _message.value = message
        firstInput = true
        _maxLengthValue.intValue = max.toLong()
                                        .let { kotlin.math.abs(it) }
                                        .toString().length

        println(_maxLengthValue.intValue)
    }

    fun setValue(value: String) {
        _resultValue.value = value
    }

    fun setSubmitState(state: Boolean) {
        _submitState.value = state
    }

    fun setMessageError(message: String?) {
        _errorMessage.value = message
    }

    fun setCalculateState(state: Boolean) {
        _isCalculateState.value = state
    }

    private fun evaluate(input: String): String {
        var expr = input


        if (input.last() == '+' || input.last() == '-')
            expr = input.dropLast(1)
        
        val result = object {
            var pos = -1
            var ch: Int = 0

            fun nextChar() {
                ch = if (++pos < expr.length) expr[pos].code else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.code) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < expr.length) throw RuntimeException("Unexpected: ${expr[pos]}")
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    when {
                        eat('+'.code) -> x += parseTerm()
                        eat('-'.code) -> x -= parseTerm()
                        else -> return x
                    }
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    when {
                        eat('*'.code) -> x *= parseFactor()
                        eat('/'.code) -> x /= parseFactor()
                        else -> return x
                    }
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.code)) return parseFactor()
                if (eat('-'.code)) return -parseFactor()

                var x: Double
                val startPos = pos
                if (eat('('.code)) {
                    x = parseExpression()
                    eat(')'.code)
                } else if (ch in '0'.code..'9'.code || ch == '.'.code) {
                    while (ch in '0'.code..'9'.code || ch == '.'.code) nextChar()
                    x = expr.substring(startPos, pos).toDouble()
                } else {
                    throw RuntimeException("Unexpected: ${ch.toChar()}")
                }
                return x
            }
        }.parse()

        val resultStr = result.toBigDecimal().stripTrailingZeros().toPlainString()
        return resultStr
    }

    private fun checkMaxLengthInput() : Boolean {
        return _resultValue.value.split(Regex("[+-]")).last().length < _maxLengthValue.intValue
    }

    private fun isValidDecimalInput(input: String) : Boolean {
        for (char in input.reversed()) {
            if (char == '.') return false
            if (char == '+' || char == '-') return true
        }
        return true
    }

    fun hasTwoDecimalPlaces(input: String): Boolean {
        val parts = Regex("(?=[+-])").split(input).last().split(Regex("\\."))
        val result = parts.size == 2 && parts[1].length == 2
        return result
    }
}