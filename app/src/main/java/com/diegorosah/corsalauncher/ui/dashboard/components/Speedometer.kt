package com.diegorosah.corsalauncher.ui.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Speedometer(
    currentSpeed: Int,
    maxSpeed: Int = 240,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.aspectRatio(1f)
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2
            val startAngle = 135f
            val sweepAngle = 270f

            // Background Arc
            drawArc(
                color = Color.DarkGray,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 20f, cap = StrokeCap.Round),
                size = Size(radius * 2, radius * 2),
                topLeft = Offset(center.x - radius, center.y - radius)
            )

            // Active Arc
            val progress = (currentSpeed.toFloat() / maxSpeed).coerceIn(0f, 1f)
            drawArc(
                color = Color(0xFF00FF41), // Neon Green
                startAngle = startAngle,
                sweepAngle = sweepAngle * progress,
                useCenter = false,
                style = Stroke(width = 20f, cap = StrokeCap.Round),
                size = Size(radius * 2, radius * 2),
                topLeft = Offset(center.x - radius, center.y - radius)
            )

            // Ticks
            val tickCount = 12 // Every 20km/h
            for (i in 0..tickCount) {
                val angle = startAngle + (sweepAngle / tickCount) * i
                val angleRad = Math.toRadians(angle.toDouble())
                val startTick = Offset(
                    center.x + (radius - 30f) * cos(angleRad).toFloat(),
                    center.y + (radius - 30f) * sin(angleRad).toFloat()
                )
                val endTick = Offset(
                    center.x + (radius - 10f) * cos(angleRad).toFloat(),
                    center.y + (radius - 10f) * sin(angleRad).toFloat()
                )
                drawLine(
                    color = Color.Gray,
                    start = startTick,
                    end = endTick,
                    strokeWidth = 4f
                )
            }
        }

        // Text
        androidx.compose.foundation.layout.Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$currentSpeed",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "km/h",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
    }
}
