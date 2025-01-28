package com.example.recipefinder.ui.myiconpack

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.Unit

public val MyIconPack.Wave: ImageVector
    get() {
        if (_wave != null) {
            return _wave!!
        }
        _wave = Builder(name = "Wave", defaultWidth = 1440.0.dp, defaultHeight = 320.0.dp,
                viewportWidth = 1440.0f, viewportHeight = 320.0f).apply {
            path(fill = SolidColor(Color(0xFF72E5FA)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(0.0f, 32.0f)
                lineTo(60.0f, 74.7f)
                curveTo(120.0f, 117.0f, 240.0f, 203.0f, 360.0f, 240.0f)
                curveTo(480.0f, 277.0f, 600.0f, 267.0f, 720.0f, 250.7f)
                curveTo(840.0f, 235.0f, 960.0f, 213.0f, 1080.0f, 224.0f)
                curveTo(1200.0f, 235.0f, 1320.0f, 277.0f, 1380.0f, 298.7f)
                lineTo(1440.0f, 320.0f)
                lineTo(1440.0f, 320.0f)
                lineTo(1380.0f, 320.0f)
                curveTo(1320.0f, 320.0f, 1200.0f, 320.0f, 1080.0f, 320.0f)
                curveTo(960.0f, 320.0f, 840.0f, 320.0f, 720.0f, 320.0f)
                curveTo(600.0f, 320.0f, 480.0f, 320.0f, 360.0f, 320.0f)
                curveTo(240.0f, 320.0f, 120.0f, 320.0f, 60.0f, 320.0f)
                lineTo(0.0f, 320.0f)
                close()
            }
        }
        .build()
        return _wave!!
    }

private var _wave: ImageVector? = null

@Preview
@Composable
private fun Preview(): Unit {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = MyIconPack.Wave, contentDescription = "")
    }
}
