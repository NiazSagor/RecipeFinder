package com.example.recipefinder.ui.home.components;

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Set_meal: ImageVector
	get() {
		if (_Set_meal != null) {
			return _Set_meal!!
		}
		_Set_meal = ImageVector.Builder(
            name = "Set_meal",
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
				moveTo(120f, 600f)
				quadToRelative(-33f, 0f, -56.5f, -23.5f)
				reflectiveQuadTo(40f, 520f)
				verticalLineToRelative(-360f)
				quadToRelative(0f, -33f, 23.5f, -56.5f)
				reflectiveQuadTo(120f, 80f)
				horizontalLineToRelative(720f)
				quadToRelative(33f, 0f, 56.5f, 23.5f)
				reflectiveQuadTo(920f, 160f)
				verticalLineToRelative(360f)
				quadToRelative(0f, 33f, -23.5f, 56.5f)
				reflectiveQuadTo(840f, 600f)
				close()
				moveToRelative(0f, -440f)
				verticalLineToRelative(360f)
				horizontalLineToRelative(720f)
				verticalLineToRelative(-360f)
				close()
				moveToRelative(3f, 580f)
				lineToRelative(-3f, -60f)
				lineToRelative(719f, -38f)
				lineToRelative(3f, 60f)
				close()
				moveToRelative(-3f, 99f)
				verticalLineToRelative(-60f)
				horizontalLineToRelative(720f)
				verticalLineToRelative(60f)
				close()
				moveToRelative(290f, -379f)
				quadToRelative(74f, 0f, 142.5f, -26f)
				reflectiveQuadTo(672f, 354f)
				quadToRelative(6f, 42f, 44f, 64f)
				reflectiveQuadToRelative(84f, 22f)
				verticalLineToRelative(-200f)
				quadToRelative(-46f, 0f, -84f, 22.5f)
				reflectiveQuadTo(672f, 328f)
				quadToRelative(-53f, -52f, -120.5f, -80f)
				reflectiveQuadTo(410f, 220f)
				quadToRelative(-79f, 0f, -152f, 27.5f)
				reflectiveQuadTo(140f, 340f)
				quadToRelative(45f, 65f, 118f, 92.5f)
				reflectiveQuadTo(410f, 460f)
				moveTo(120f, 160f)
				verticalLineToRelative(360f)
				close()
			}
		}.build()
		return _Set_meal!!
	}

private var _Set_meal: ImageVector? = null
