package com.seva.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seva.app.ui.theme.*

@Composable
fun OnboardingScreen(onGetStartedClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SevaLightBg)
    ) {
        // Abstract Background Shapes (Simplified)
        Box(
            modifier = Modifier
                .size(300.dp, 400.dp)
                .offset(x = (-50).dp, y = 50.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(SevaOrange.copy(alpha = 0.05f))
        )
        Box(
            modifier = Modifier
                .size(300.dp, 400.dp)
                .align(Alignment.TopEnd)
                .offset(x = 100.dp, y = 100.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(SevaOrange.copy(alpha = 0.05f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            // Logo Section
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White)
                    .padding(20.dp)
            ) {
                // Mocking the logo with an icon
                Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = null,
                    tint = SevaOrange,
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Official Portal Badge
            Surface(
                color = SevaBadgeBg,
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = SevaBadgeText,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "OFFICIAL PORTAL",
                        style = MaterialTheme.typography.labelMedium,
                        color = SevaBadgeText,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // App Title
            Text(
                text = "Seva App",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                color = SevaDarkBlue
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tagline
            Text(
                text = "Empowering Citizens, Simplifying Access.",
                style = MaterialTheme.typography.bodyLarge,
                color = SevaGrey,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(240.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Action Cards Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionCard(
                    modifier = Modifier.weight(1f),
                    title = "Gov Services",
                    icon = Icons.Default.AccountBalance
                )
                ActionCard(
                    modifier = Modifier.weight(1f),
                    title = "Documents",
                    icon = Icons.Default.Description
                )
                ActionCard(
                    modifier = Modifier.weight(1f),
                    title = "Support",
                    icon = Icons.Default.HeadsetMic
                )
            }
        }

        // Bottom Section
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onGetStartedClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SevaOrange)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Get Started",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "By continuing, you agree to our Terms of Service & Privacy Policy",
                    style = MaterialTheme.typography.bodySmall,
                    color = SevaGrey.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ActionCard(modifier: Modifier = Modifier, title: String, icon: ImageVector) {
    Surface(
        modifier = modifier.height(100.dp),
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 2.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SevaOrange,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = SevaDarkBlue,
                textAlign = TextAlign.Center
            )
        }
    }
}
