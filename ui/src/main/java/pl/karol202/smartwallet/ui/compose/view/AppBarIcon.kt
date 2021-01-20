package pl.karol202.smartwallet.ui.compose.view

import androidx.compose.material.AmbientContentColor
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AppBarIcon(imageVector: ImageVector,
               enabled: Boolean = true,
               onClick: () -> Unit)
{
	IconButton(
		enabled = enabled,
		onClick = onClick,
		content = {
			Icon(
				imageVector = imageVector,
				tint = AmbientContentColor.current.copy(
					alpha = if(enabled) ContentAlpha.high else ContentAlpha.disabled
				)
			)
		}
	)
}
