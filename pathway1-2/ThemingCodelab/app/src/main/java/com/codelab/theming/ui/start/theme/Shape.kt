package com.codelab.theming.ui.start.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

/**
 * @Description
 * @author yc.park (DEEP.FINE)
 * @since 2022-11-14
 * @version 1.0.0
 */
val JetnewsShapes = Shapes(
  small = CutCornerShape(topStart = 8.dp),
  medium = CutCornerShape(topStart = 24.dp),
  large = RoundedCornerShape(8.dp)
)