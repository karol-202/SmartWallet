package pl.karol202.smartwallet.ui.compose.view

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OutlinedToggleButton(modifier: Modifier = Modifier,
                         checked: Boolean,
                         onClick: () -> Unit,
                         content: @Composable () -> Unit)
{
	OutlinedButton(
		modifier = modifier,
		onClick = onClick,
		colors = ButtonDefaults.outlinedButtonColors(
			backgroundColor =
			if(checked) MaterialTheme.colors.primary.copy(alpha = 0.2f)
			else MaterialTheme.colors.surface
		),
		content = { content() }
	)
}
