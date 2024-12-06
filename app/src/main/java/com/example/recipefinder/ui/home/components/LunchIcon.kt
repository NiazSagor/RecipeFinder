package com.example.recipefinder.ui.home.components


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Lunch_dining: ImageVector
    get() {
        if (_Lunch_dining != null) {
            return _Lunch_dining!!
        }
        _Lunch_dining = ImageVector.Builder(
            name = "Lunch_dining",
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
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(80f, 760f)
                verticalLineToRelative(-120f)
                horizontalLineToRelative(800f)
                verticalLineToRelative(120f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(800f, 840f)
                close()
                moveToRelative(0f, -120f)
                verticalLineToRelative(40f)
                horizontalLineToRelative(640f)
                verticalLineToRelative(-40f)
                close()
                moveToRelative(320f, -180f)
                quadToRelative(-36f, 0f, -57f, 20f)
                reflectiveQuadToRelative(-77f, 20f)
                reflectiveQuadToRelative(-76f, -20f)
                reflectiveQuadToRelative(-56f, -20f)
                reflectiveQuadToRelative(-57f, 20f)
                reflectiveQuadToRelative(-77f, 20f)
                verticalLineToRelative(-80f)
                quadToRelative(36f, 0f, 57f, -20f)
                reflectiveQuadToRelative(77f, -20f)
                reflectiveQuadToRelative(76f, 20f)
                reflectiveQuadToRelative(56f, 20f)
                reflectiveQuadToRelative(57f, -20f)
                reflectiveQuadToRelative(77f, -20f)
                reflectiveQuadToRelative(77f, 20f)
                reflectiveQuadToRelative(57f, 20f)
                reflectiveQuadToRelative(56f, -20f)
                reflectiveQuadToRelative(76f, -20f)
                reflectiveQuadToRelative(79f, 20f)
                reflectiveQuadToRelative(55f, 20f)
                verticalLineToRelative(80f)
                quadToRelative(-56f, 0f, -75f, -20f)
                reflectiveQuadToRelative(-55f, -20f)
                reflectiveQuadToRelative(-58f, 20f)
                reflectiveQuadToRelative(-78f, 20f)
                reflectiveQuadToRelative(-77f, -20f)
                reflectiveQuadToRelative(-57f, -20f)
                moveTo(80f, 400f)
                verticalLineToRelative(-40f)
                quadToRelative(0f, -115f, 108.5f, -177.5f)
                reflectiveQuadTo(480f, 120f)
                reflectiveQuadToRelative(291.5f, 62.5f)
                reflectiveQuadTo(880f, 360f)
                verticalLineToRelative(40f)
                close()
                moveToRelative(400f, -200f)
                quadToRelative(-124f, 0f, -207.5f, 31f)
                reflectiveQuadTo(166f, 320f)
                horizontalLineToRelative(628f)
                quadToRelative(-23f, -58f, -106.5f, -89f)
                reflectiveQuadTo(480f, 200f)
                moveToRelative(0f, 120f)
            }
        }.build()
        return _Lunch_dining!!
    }

private var _Lunch_dining: ImageVector? = null
