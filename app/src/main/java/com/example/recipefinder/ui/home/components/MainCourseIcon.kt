package com.example.recipefinder.ui.home.components


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Food_bank: ImageVector
    get() {
        if (_Food_bank != null) {
            return _Food_bank!!
        }
        _Food_bank = ImageVector.Builder(
            name = "Food_bank",
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
                moveTo(400f, 720f)
                horizontalLineToRelative(40f)
                verticalLineToRelative(-160f)
                quadToRelative(25f, 0f, 42.5f, -17.5f)
                reflectiveQuadTo(500f, 500f)
                verticalLineToRelative(-120f)
                horizontalLineToRelative(-40f)
                verticalLineToRelative(120f)
                horizontalLineToRelative(-20f)
                verticalLineToRelative(-120f)
                horizontalLineToRelative(-40f)
                verticalLineToRelative(120f)
                horizontalLineToRelative(-20f)
                verticalLineToRelative(-120f)
                horizontalLineToRelative(-40f)
                verticalLineToRelative(120f)
                quadToRelative(0f, 25f, 17.5f, 42.5f)
                reflectiveQuadTo(400f, 560f)
                close()
                moveToRelative(160f, 0f)
                horizontalLineToRelative(40f)
                verticalLineToRelative(-340f)
                quadToRelative(-33f, 0f, -56.5f, 23.5f)
                reflectiveQuadTo(520f, 460f)
                verticalLineToRelative(120f)
                horizontalLineToRelative(40f)
                close()
                moveTo(160f, 840f)
                verticalLineToRelative(-480f)
                lineToRelative(320f, -240f)
                lineToRelative(320f, 240f)
                verticalLineToRelative(480f)
                close()
                moveToRelative(80f, -80f)
                horizontalLineToRelative(480f)
                verticalLineToRelative(-360f)
                lineTo(480f, 220f)
                lineTo(240f, 400f)
                close()
                moveToRelative(240f, -270f)
            }
        }.build()
        return _Food_bank!!
    }

private var _Food_bank: ImageVector? = null
