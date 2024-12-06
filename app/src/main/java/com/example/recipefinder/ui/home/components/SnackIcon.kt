package com.example.recipefinder.ui.home.components


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Fastfood: ImageVector
    get() {
        if (_Fastfood != null) {
            return _Fastfood!!
        }
        _Fastfood = ImageVector.Builder(
            name = "Fastfood",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(533f, 520f)
                quadToRelative(-32f, -45f, -84.5f, -62.5f)
                reflectiveQuadTo(340f, 440f)
                reflectiveQuadToRelative(-108.5f, 17.5f)
                reflectiveQuadTo(147f, 520f)
                close()
                moveTo(40f, 600f)
                quadToRelative(0f, -109f, 91f, -174.5f)
                reflectiveQuadTo(340f, 360f)
                reflectiveQuadToRelative(209f, 65.5f)
                reflectiveQuadTo(640f, 600f)
                close()
                moveToRelative(0f, 160f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(600f)
                verticalLineToRelative(80f)
                close()
                moveTo(720f, 920f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(56f)
                lineToRelative(56f, -560f)
                horizontalLineTo(450f)
                lineToRelative(-10f, -80f)
                horizontalLineToRelative(200f)
                verticalLineToRelative(-160f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(160f)
                horizontalLineToRelative(200f)
                lineTo(854f, 862f)
                quadToRelative(-3f, 25f, -22f, 41.5f)
                reflectiveQuadTo(788f, 920f)
                close()
                moveToRelative(0f, -80f)
                horizontalLineToRelative(56f)
                close()
                moveTo(80f, 920f)
                quadToRelative(-17f, 0f, -28.5f, -11.5f)
                reflectiveQuadTo(40f, 880f)
                verticalLineToRelative(-40f)
                horizontalLineToRelative(600f)
                verticalLineToRelative(40f)
                quadToRelative(0f, 17f, -11.5f, 28.5f)
                reflectiveQuadTo(600f, 920f)
                close()
                moveToRelative(260f, -400f)
            }
        }.build()
        return _Fastfood!!
    }

private var _Fastfood: ImageVector? = null
