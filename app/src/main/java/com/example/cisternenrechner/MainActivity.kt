package com.example.cisternenrechner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFE3F2FD)
                ) {
                    ZisternenRechner()
                }
            }
        }
    }
}

@Composable
fun ZisternenRechner() {
    var sliderValue by remember { mutableFloatStateOf(1.5f) }

    val maxTiefe = 2.87f
    val grundflaechePi = 3141.59f
    val maxVolumen = 5874.77f
    
    val berechnetesVolumen = grundflaechePi * (maxTiefe - sliderValue)
    val liter = if (berechnetesVolumen > 0) berechnetesVolumen.roundToInt() else 0
    
    var prozent = (berechnetesVolumen / maxVolumen) * 100f
    if (prozent < 0f) prozent = 0f
    if (prozent > 100f) prozent = 100f

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(size.width / 2, size.height * 0.35f)
                cubicTo(
                    size.width * 0.8f, size.height * 0.65f,
                    size.width * 0.75f, size.height * 0.85f,
                    size.width / 2, size.height * 0.85f
                )
                cubicTo(
                    size.width * 0.25f, size.height * 0.85f,
                    size.width * 0.2f, size.height * 0.65f,
                    size.width / 2, size.height * 0.35f
                )
                close()
            }
            drawPath(path, color = Color(0xFF0056B3).copy(alpha = 0.05f))
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Zisternen-Rechner",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF004494)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text("Gemessener Abstand (Deckel bis Wasser):", fontSize = 14.sp, color = Color.Gray)
                
                Text(
                    text = String.format(Locale.GERMANY, "%.2f m", sliderValue),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0056B3)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Slider(
                    value = sliderValue,
                    onValueChange = { sliderValue = it },
                    valueRange = 1.0f..2.87f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF0056B3),
                        activeTrackColor = Color(0xFF0056B3)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("1,00 m (Voll)", fontSize = 12.sp, color = Color.Gray)
                    Text("2,87 m (Leer)", fontSize = 12.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text("Aktuelles Volumen:", fontSize = 16.sp, color = Color.Gray)
                
                Text(
                    text = "$liter Liter",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0056B3)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = String.format(Locale.GERMANY, "%.1f %% Füllstand", prozent),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5C6BC0)
                )
            }
        }
    }
}
