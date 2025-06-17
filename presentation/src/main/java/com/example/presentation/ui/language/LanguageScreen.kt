package com.example.presentation.ui.language

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.presentation.R
import com.example.presentation.components.CukcukImageBox
import com.example.presentation.components.CukcukToolbar
import com.example.presentation.enums.Language
import org.koin.androidx.compose.koinViewModel

@Composable
fun LanguageScreen(
    navController: NavHostController,
    viewModel: LanguageViewModel = koinViewModel()
) {

    val activity = LocalActivity.current

    val languages = Language.entries.toTypedArray()

    val currentLanguage = viewModel.language.value

    Scaffold(
        topBar = {
            CukcukToolbar(
                title = stringResource(R.string.toolbar_title_Language),
                menuTitle = null,
                onBackClick = {
                    navController.popBackStack()
                },
                onMenuClick = {}
            )
        },
        containerColor = colorResource(R.color.background_color_bold)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .background(
                    color = colorResource(R.color.white)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(languages) { language ->
                LanguageItem(
                    language = language,
                    currentLanguage = currentLanguage,
                    onLanguageSelected = {
                        viewModel.setLanguage(language.code)
                        activity?.recreate()
                    }
                )
            }
        }
    }
}

@Composable
fun LanguageItem(
    language: Language,
    currentLanguage: String,
    onLanguageSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable{
                onLanguageSelected()
            }
            .drawBehind{
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            }
            .padding(horizontal = 6.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(language.title),
            fontSize = 18.sp,
            fontWeight = FontWeight.W500
        )
        if (currentLanguage == language.code) {
            CukcukImageBox(
                size = 26,
                imageSize = 20,
                iconDrawable = painterResource(R.drawable.ic_yes),
                colorRes = colorResource(R.color.unit_selected)
            )
        }
    }
}