package pl.karol202.smartwallet.ui.compose.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.isFocused
import androidx.compose.ui.focus.onFocusChanged

@Composable
fun ExposedDropdownMenu(selectedValue: String,
                        modifier: Modifier,
                        textFieldModifier: Modifier,
                        label: @Composable () -> Unit,
                        content: ExposedDropdownScope.() -> Unit)
{
	val focusRequester = remember { FocusRequester() }
	var isFocused by remember { mutableStateOf(false) }
	var isOpen by remember { mutableStateOf(false) }

	Box(modifier = modifier) {
		DropdownMenu(
			toggle = {
				TextField(
					modifier = textFieldModifier
							.focusRequester(focusRequester)
							.onFocusChanged { isFocused = it.isFocused },
					value = selectedValue,
					onValueChange = {},
					readOnly = true,
					label = label,
					trailingIcon = {
						Icon(
							imageVector = if(isOpen) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
							tint =
								if(isFocused) MaterialTheme.colors.primary
								else AmbientContentColor.current
						)
					},
				)
			},
			expanded = isOpen,
			onDismissRequest = { isOpen = false },
			dropdownContent = {
				val scope = ExposedDropdownScopeImpl().also(content)
				scope.items.forEach {
					when(it)
					{
						is ExposedDropdownItem.Standard -> DropdownMenuItem(
							enabled = it.enabled,
							onClick = {
								it.onClick { isOpen = false }
							},
							content = it.content
						)
						is ExposedDropdownItem.Custom -> it.content()
					}
				}
			}
		)
		Spacer(
			modifier = Modifier
					.matchParentSize()
					.clickable {
						isOpen = true
						focusRequester.requestFocus()
					}
		)
	}
}

interface ExposedDropdownScope
{
	fun item(enabled: Boolean = true, onClick: (closeDrawer: () -> Unit) -> Unit = {}, content: @Composable () -> Unit)

	fun custom(content: @Composable () -> Unit)
}

private sealed class ExposedDropdownItem
{
	data class Standard(val enabled: Boolean,
	                    val onClick: (closeDrawer: () -> Unit) -> Unit,
	                    val content: @Composable () -> Unit) : ExposedDropdownItem()

	data class Custom(val content: @Composable () -> Unit) : ExposedDropdownItem()
}

private class ExposedDropdownScopeImpl : ExposedDropdownScope
{
	var items = emptyList<ExposedDropdownItem>()
		private set

	override fun item(enabled: Boolean, onClick: (closeDrawer: () -> Unit) -> Unit, content: @Composable () -> Unit)
	{
		items = items + ExposedDropdownItem.Standard(enabled, onClick, content)
	}

	override fun custom(content: @Composable () -> Unit)
	{
		items = items + ExposedDropdownItem.Custom(content)
	}
}
