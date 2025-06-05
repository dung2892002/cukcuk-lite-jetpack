package com.example.cukcuk.presentation.ui.synchronize

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.presentation.components.CukcukButton
import com.example.cukcuk.presentation.components.CukcukLoadingDialog
import com.example.cukcuk.presentation.components.Toolbar
import com.example.cukcuk.utils.FormatDisplay
import java.time.LocalDateTime

@Composable
fun SynchronizeScreen(
    navController: NavHostController,
    viewModel: SynchronizeViewModel = hiltViewModel()
) {
    val count = viewModel.count.value
    val lastSyncTime = viewModel.lastSyncTime.value
    val loading = viewModel.loading.value

    Scaffold(
        topBar = {
            Toolbar(
                title = "Đồng bộ dữ liệu",
                menuTitle = null,
                onBackClick = { navController.popBackStack() },
                onMenuClick = {}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    color = colorResource(R.color.white)
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_sync_data),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = if (lastSyncTime != null) buildLastSyncTimeString(lastSyncTime)
                    else AnnotatedString("Chưa thực hiện đồng bộ"),
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )

            if (count > 0) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .background(
                            color = colorResource(R.color.white)
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_cloud_download),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color.Unspecified
                    )
                    Text(
                        text = buildCountSyncString(count),
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }
            }
            CukcukButton(
                title = "ĐỒNG BỘ NGAY",
                onClick = { viewModel.handleSyncData() },
                bgColor = colorResource(R.color.main_color),
                textColor = colorResource(R.color.white),
                fontSize = 20,
                modifier = Modifier.padding(10.dp).fillMaxWidth()
            )
        }
    }

    if(loading) {
        CukcukLoadingDialog(
            title = "Đang đồng bộ dữ liệu ..."
        )
    }
}

fun buildLastSyncTimeString(lastSyncTime: LocalDateTime) : AnnotatedString {
    return buildAnnotatedString {
        append("Đồng bộ lần cuối: ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(FormatDisplay.formatTo12HourWithCustomAMPM(lastSyncTime.toString()))
        }
    }
}

fun buildCountSyncString(count: Int) : AnnotatedString {
    return buildAnnotatedString {
        append("Bạn đang có ")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Red)
        ) {
            append(count.toString())
        }
        append(" giao dịch chưa được đồng bộ. Các thiết bị khác sẽ không nhận được những thay đổi này. Vui lòng kiểm tra kết nối mạng và thực hiện đồng bộ")
    }
}