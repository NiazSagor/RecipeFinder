package com.example.recipefinder.ui.home.components


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Dinner_dining: ImageVector
    get() {
        if (_Dinner_dining != null) {
            return _Dinner_dining!!
        }
        _Dinner_dining = ImageVector.Builder(
            name = "Dinner_dining",
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
                moveTo(160f, 840f)
                lineToRelative(-80f, -80f)
                horizontalLineToRelative(800f)
                lineToRelative(-80f, 80f)
                close()
                moveToRelative(-40f, -120f)
                quadToRelative(6f, -18f, 16f, -34f)
                reflectiveQuadToRelative(24f, -30f)
                verticalLineToRelative(-296f)
                horizontalLineToRelative(-40f)
                verticalLineToRelative(-60f)
                horizontalLineToRelative(40f)
                verticalLineToRelative(-30f)
                horizontalLineToRelative(-40f)
                verticalLineToRelative(-60f)
                horizontalLineToRelative(40f)
                verticalLineToRelative(-30f)
                horizontalLineToRelative(-40f)
                verticalLineToRelative(-60f)
                horizontalLineToRelative(280f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(480f, 200f)
                verticalLineToRelative(10f)
                horizontalLineToRelative(360f)
                verticalLineToRelative(60f)
                horizontalLineTo(480f)
                verticalLineToRelative(10f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(400f, 360f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(244f)
                quadToRelative(14f, 2f, 28f, 6f)
                reflectiveQuadToRelative(26f, 12f)
                quadToRelative(26f, -65f, 83f, -103.5f)
                reflectiveQuadTo(583f, 480f)
                quadToRelative(90f, 0f, 153.5f, 61.5f)
                reflectiveQuadTo(800f, 692f)
                verticalLineToRelative(28f)
                close()
                moveToRelative(334f, -80f)
                horizontalLineToRelative(252f)
                quadToRelative(-17f, -36f, -50f, -58f)
                reflectiveQuadToRelative(-73f, -22f)
                quadToRelative(-42f, 0f, -77f, 21f)
                reflectiveQuadToRelative(-52f, 59f)
                moveTo(320f, 210f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(-30f)
                horizontalLineToRelative(-80f)
                close()
                moveToRelative(0f, 90f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(-30f)
                horizontalLineToRelative(-80f)
                close()
                moveToRelative(-100f, -90f)
                horizontalLineToRelative(40f)
                verticalLineToRelative(-30f)
                horizontalLineToRelative(-40f)
                close()
                moveToRelative(0f, 90f)
                horizontalLineToRelative(40f)
                verticalLineToRelative(-30f)
                horizontalLineToRelative(-40f)
                close()
                moveToRelative(0f, 314f)
                quadToRelative(10f, -5f, 19.5f, -7.5f)
                reflectiveQuadTo(260f, 602f)
                verticalLineToRelative(-242f)
                horizontalLineToRelative(-40f)
                close()
                moveToRelative(360f, 26f)
            }
        }.build()
        return _Dinner_dining!!
    }

private var _Dinner_dining: ImageVector? = null
