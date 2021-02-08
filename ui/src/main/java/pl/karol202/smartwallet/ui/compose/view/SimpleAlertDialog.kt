package pl.karol202.smartwallet.ui.compose.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.unit.dp
import java.util.*

enum class SimpleAlertButtonsOrientation
{
	HORIZONTAL, VERTICAL
}

interface SimpleAlertButtonsScope
{
	fun button(text: String, onClick: () -> Unit)

	fun button(@StringRes textRes: Int, onClick: () -> Unit)

	fun dismissButton(text: String, onClick: () -> Unit)

	fun dismissButton(@StringRes textRes: Int, onClick: () -> Unit)
}

private data class SimpleAlertButtonsItem(val text: String,
                                          val onClick: () -> Unit,
                                          val isDismissing: Boolean)

private class SimpleAlertButtonsScopeImpl(private val resToString: (Int) -> String) : SimpleAlertButtonsScope
{
	var items = emptyList<SimpleAlertButtonsItem>()

	override fun button(text: String, onClick: () -> Unit)
	{
		items = items + SimpleAlertButtonsItem(text, onClick, false)
	}

	override fun button(@StringRes textRes: Int, onClick: () -> Unit) = button(resToString(textRes), onClick)

	override fun dismissButton(text: String, onClick: () -> Unit)
	{
		items = items + SimpleAlertButtonsItem(text, onClick, true)
	}

	override fun dismissButton(@StringRes textRes: Int, onClick: () -> Unit) = dismissButton(resToString(textRes), onClick)
}

@Composable
fun SimpleAlertDialog(title: String,
                      confirmText: String,
                      dismissText: String,
                      onConfirm: () -> Unit,
                      onDismiss: () -> Unit)
{
	SimpleAlertDialog(
		title = title,
		buttonsOrientation = SimpleAlertButtonsOrientation.HORIZONTAL,
		buttons = {
			dismissButton(dismissText, onDismiss)
			button(confirmText, onConfirm)
		}
	)
}

@Composable
fun SimpleAlertDialog(title: String,
                      buttonsOrientation: SimpleAlertButtonsOrientation = SimpleAlertButtonsOrientation.HORIZONTAL,
                      buttons: SimpleAlertButtonsScope.() -> Unit)
{
	val resources = AmbientContext.current.resources
	val buttonsList = SimpleAlertButtonsScopeImpl(resToString = { resources.getString(it) }).apply(buttons).items
	val dismissButton = buttonsList.find { it.isDismissing }

	AlertDialog(
		title = {
			Text(text = title)
		},
		onDismissRequest = { dismissButton?.onClick?.invoke() },
		buttons = {
			when(buttonsOrientation)
			{
				SimpleAlertButtonsOrientation.HORIZONTAL -> SimpleAlertButtonsHorizontal(buttonsList)
				SimpleAlertButtonsOrientation.VERTICAL -> SimpleAlertButtonsVertical(buttonsList)
			}
		}
	)
}

@Composable
private fun SimpleAlertButtonsHorizontal(buttons: List<SimpleAlertButtonsItem>)
{
	Row(
		modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 8.dp)
				.padding(top = 16.dp, bottom = 8.dp),
		horizontalArrangement = Arrangement.End,
		content = {
			buttons.forEachIndexed { index, button ->
				SimpleAlertButton(
					modifier = if(index != 0) Modifier.padding(start = 8.dp) else Modifier,
					text = button.text,
					onClick = button.onClick
				)
			}
		}
	)
}

@Composable
private fun SimpleAlertButtonsVertical(buttons: List<SimpleAlertButtonsItem>)
{
	Column(
		modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp),
		horizontalAlignment = Alignment.End,
		content = {
			buttons.forEach { button ->
				SimpleAlertButton(
					modifier = Modifier.padding(top = 12.dp),
					text = button.text,
					onClick = button.onClick
				)
			}
		}
	)
}

@Composable
private fun SimpleAlertButton(modifier: Modifier = Modifier,
                              text: String,
                              onClick: () -> Unit)
{
	TextButton(
		modifier = modifier,
		onClick = onClick,
		content = {
			Text(text = text.toUpperCase(Locale.ROOT))
		}
	)
}
