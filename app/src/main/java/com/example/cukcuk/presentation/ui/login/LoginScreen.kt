package com.example.cukcuk.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cukcuk.R

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp)
            .background(
                color = colorResource(R.color.white)
            )
    ) {
        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = null
        )

        ButtonMethodLogin(
            icon = painterResource(R.drawable.ic_facebook),
            bgColor = colorResource(R.color.button_login_facebook),
            bgIconColor = colorResource(R.color.button_login_facebook_icon),
            text = "Đăng nhập bằng Facebook",
            onClick = {
                print("Đăng nhập bằng Facebook")
            }
        )

        ButtonMethodLogin(
            icon = painterResource(R.drawable.ic_google),
            bgColor = colorResource(R.color.button_login_google),
            bgIconColor = colorResource(R.color.button_login_google_icon),
            text = "Đăng nhập bằng Google",
            onClick = {
                print("Đăng nhập bằng Facebook")
            }
        )

        ButtonMethodLogin(
            icon = painterResource(R.drawable.ic_phone),
            bgColor = colorResource(R.color.button_login_account),
            bgIconColor = colorResource(R.color.button_login_account_icon),
            text = "Số điện thoại hoặc email",
            previousText = "Đăng nhập bằng",
            onClick = {
                navController.navigate("login_account")
            }
        )

        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bạn chưa có tài khoản?"
            )
            Text(
                text = "Đăng ký",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable(
                        onClick = {
                            navController.navigate("register")
                        }
                    )
                    .padding(horizontal = 10.dp)
            )
        }
    }
}

@Composable
fun ButtonMethodLogin(
    icon: Painter,
    bgColor: Color,
    bgIconColor: Color,
    text: String,
    previousText: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(
                color = bgColor
            )
            .clickable(
                onClick = onClick
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    color = bgIconColor
                )
                .width(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = colorResource(R.color.white)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    color = Color.Transparent
                )
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            if (previousText != null) {
                Text(
                    text = previousText,
                    fontSize = 14.sp,
                    color = colorResource(R.color.white)
                )
            }
            Text(
                text = text,
                fontSize = 16.sp,
                color = colorResource(R.color.white)
            )
        }

    }
}