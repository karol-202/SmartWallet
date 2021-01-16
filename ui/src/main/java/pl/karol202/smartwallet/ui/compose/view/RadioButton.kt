package pl.karol202.smartwallet.ui.compose.view

import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonWithText(text: String,
                        selected: Boolean,
                        onClick: () -> Unit,
                        enabled: Boolean = true)
{
	RadioButtonRow(
		selected = selected,
		onClick = onClick,
		enabled = enabled
	) {
		Text(
			text = text,
			modifier = Modifier.padding(start = 16.dp)
		)
	}
}

@Composable
fun RadioButtonRow(selected: Boolean,
                   onClick: () -> Unit,
                   enabled: Boolean = true,
                   content: @Composable () -> Unit)
{
	Row(
		modifier = Modifier
				.selectable(
					selected = selected,
					onClick = onClick,
					indication = null
				)

	) {
		RadioButton(
			selected = selected,
			onClick = onClick,
			enabled = enabled
		)
		content()
	}
}
