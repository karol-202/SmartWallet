package pl.karol202.smartwallet.ui.compose.view

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import java.util.*

@Composable
fun SimpleAlertDialog(title: String,
                      confirmText: String,
                      dismissText: String,
                      onConfirm: () -> Unit,
                      onDismiss: () -> Unit)
{
	AlertDialog(
		title = {
			Text(text = title)
		},
		onDismissRequest = onDismiss,
		confirmButton = {
			TextButton(
				onClick = onConfirm,
				content = {
					Text(text = confirmText.toUpperCase(Locale.ROOT))
				}
			)
		},
		dismissButton = {
			TextButton(
				onClick = onDismiss,
				content = {
					Text(text = dismissText.toUpperCase(Locale.ROOT))
				}
			)
		}
	)
}
