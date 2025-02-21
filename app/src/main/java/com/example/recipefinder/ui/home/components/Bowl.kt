package com.example.recipefinder.ui.home.components;

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Rice_bowl: ImageVector
	get() {
		if (_Rice_bowl != null) {
			return _Rice_bowl!!
		}
		_Rice_bowl = ImageVector.Builder(
            name = "Rice_bowl",
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
				moveTo(360f, 880f)
				quadToRelative(-17f, 0f, -28.5f, -11.5f)
				reflectiveQuadTo(320f, 840f)
				verticalLineToRelative(-30f)
				quadToRelative(-105f, -42f, -172.5f, -130f)
				reflectiveQuadTo(80f, 480f)
				quadToRelative(0f, -83f, 31.5f, -156f)
				reflectiveQuadTo(197f, 197f)
				reflectiveQuadToRelative(127f, -85.5f)
				reflectiveQuadTo(480f, 80f)
				reflectiveQuadToRelative(156f, 31.5f)
				reflectiveQuadTo(763f, 197f)
				reflectiveQuadToRelative(85.5f, 127f)
				reflectiveQuadTo(880f, 480f)
				quadToRelative(0f, 112f, -67.5f, 200f)
				reflectiveQuadTo(640f, 810f)
				verticalLineToRelative(30f)
				quadToRelative(0f, 17f, -11.5f, 28.5f)
				reflectiveQuadTo(600f, 880f)
				close()
				moveToRelative(40f, -400f)
				horizontalLineToRelative(160f)
				verticalLineToRelative(-310f)
				quadToRelative(-20f, -5f, -40f, -7.5f)
				reflectiveQuadToRelative(-40f, -2.5f)
				reflectiveQuadToRelative(-40f, 2.5f)
				reflectiveQuadToRelative(-40f, 7.5f)
				close()
				moveToRelative(-240f, 0f)
				horizontalLineToRelative(160f)
				verticalLineToRelative(-277f)
				quadToRelative(-75f, 43f, -117.5f, 117f)
				reflectiveQuadTo(160f, 480f)
				moveToRelative(480f, 0f)
				horizontalLineToRelative(160f)
				quadToRelative(0f, -86f, -42.5f, -160f)
				reflectiveQuadTo(640f, 203f)
				close()
				moveTo(400f, 800f)
				horizontalLineToRelative(160f)
				verticalLineToRelative(-44f)
				quadToRelative(72f, -29f, 133f, -76.5f)
				reflectiveQuadTo(784f, 560f)
				horizontalLineTo(176f)
				quadToRelative(30f, 72f, 91f, 119.5f)
				reflectiveQuadTo(400f, 756f)
				close()
				moveToRelative(0f, 0f)
				horizontalLineToRelative(160f)
				close()
			}
		}.build()
		return _Rice_bowl!!
	}

private var _Rice_bowl: ImageVector? = null
