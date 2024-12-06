package com.example.recipefinder.ui.home.components


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Dessert: ImageVector
    get() {
        if (_Dessert != null) {
            return _Dessert!!
        }
        _Dessert = ImageVector.Builder(
            name = "Dessert",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(14f, 4f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 6f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 10f, 4f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 14f, 4f)
                close()
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(10.2f, 3.2f)
                curveTo(5.5f, 4f, 2f, 8.1f, 2f, 13f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4f, 0f)
                verticalLineToRelative(-1f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4f, 0f)
                verticalLineToRelative(4f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4f, 0f)
                verticalLineToRelative(-4f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4f, 0f)
                verticalLineToRelative(1f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4f, 0f)
                curveToRelative(0f, -4.9f, -3.5f, -9f, -8.2f, -9.8f)
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(3.2f, 14.8f)
                arcToRelative(9f, 9f, 0f, isMoreThanHalf = false, isPositiveArc = false, 17.6f, 0f)
            }
        }.build()
        return _Dessert!!
    }

private var _Dessert: ImageVector? = null
