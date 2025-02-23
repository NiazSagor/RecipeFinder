package com.example.recipefinder.ui.home.components;

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Sandwich: ImageVector
	get() {
		if (_Sandwich != null) {
			return _Sandwich!!
		}
		_Sandwich = ImageVector.Builder(
            name = "Sandwich",
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
				moveTo(3f, 11f)
				verticalLineToRelative(3f)
				arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1f, 1f)
				horizontalLineToRelative(16f)
				arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1f, -1f)
				verticalLineToRelative(-3f)
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
				moveTo(12f, 19f)
				horizontalLineTo(4f)
				arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, -1f)
				verticalLineToRelative(-2f)
				arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, -1f)
				horizontalLineToRelative(16f)
				arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 1f)
				verticalLineToRelative(2f)
				arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, 1f)
				horizontalLineToRelative(-3.83f)
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
				moveTo(3f, 11f)
				lineToRelative(7.77f, -6.04f)
				arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.46f, 0f)
				lineTo(21f, 11f)
				horizontalLineTo(3f)
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
				moveTo(12.97f, 19.77f)
				lineTo(7f, 15f)
				horizontalLineToRelative(12.5f)
				lineToRelative(-3.75f, 4.5f)
				arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.78f, 0.27f)
				close()
			}
		}.build()
		return _Sandwich!!
	}

private var _Sandwich: ImageVector? = null
