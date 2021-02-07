package pl.karol202.smartwallet.ui.compose.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
                        enabled: Boolean = true,
                        modifier: Modifier,
                        textFieldModifier: Modifier,
                        label: @Composable () -> Unit,
                        content: ExposedDropdownScope.() -> Unit)
{
	Box(modifier = modifier) {
		if(enabled) ExposedDropdownMenuEnabled(
			selectedValue = selectedValue,
			textFieldModifier = textFieldModifier,
			label = label,
			content = content
		)
		else ExposedDropdownMenuDisabled(
			selectedValue = selectedValue,
			textFieldModifier = textFieldModifier,
			label = label
		)
	}
}

@Composable
@SuppressLint("ModifierParameter")
private fun BoxScope.ExposedDropdownMenuEnabled(selectedValue: String,
                                                textFieldModifier: Modifier,
                                                label: @Composable () -> Unit,
                                                content: ExposedDropdownScope.() -> Unit)
{
	val focusRequester = remember { FocusRequester() }
	var isFocused by remember { mutableStateOf(false) }
	var isOpen by remember { mutableStateOf(false) }

	DropdownMenu(
		toggle = {
			DropdownTextField(
				selectedValue = selectedValue,
				modifier = textFieldModifier
						.focusRequester(focusRequester)
						.onFocusChanged { isFocused = it.isFocused },
				open = isOpen,
				focused = isFocused,
				label = label
			)
		},
		expanded = isOpen,
		onDismissRequest = { isOpen = false },
		dropdownContent = {
			DropdownContent(
				onDrawerClose = { isOpen = false },
				content = content
			)
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

@Composable
@SuppressLint("ModifierParameter")
private fun ExposedDropdownMenuDisabled(selectedValue: String,
                                        textFieldModifier: Modifier,
                                        label: @Composable () -> Unit)
{
	DropdownMenu(
		toggle = {
			DropdownTextField(
				selectedValue = selectedValue,
				modifier = textFieldModifier,
				enabled = false,
				open = false,
				focused = false,
				label = label
			)
		},
		expanded = false,
		onDismissRequest = {},
		dropdownContent = {}
	)
}

@Composable
private fun DropdownTextField(selectedValue: String,
                              modifier: Modifier,
                              enabled: Boolean = true,
                              open: Boolean,
                              focused: Boolean,
                              label: @Composable () -> Unit)
{
	TextField(
		modifier = modifier,
		value = selectedValue,
		onValueChange = {},
		enabled = enabled,
		readOnly = true,
		label = label,
		trailingIcon = {
			Icon(
				imageVector = if(open) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
				tint =
					if(focused) MaterialTheme.colors.primary
					else AmbientContentColor.current
			)
		},
	)
}

@Composable
private fun DropdownContent(onDrawerClose: () -> Unit,
                            content: ExposedDropdownScope.() -> Unit)
{
	val scope = ExposedDropdownScopeImpl().also(content)
	scope.items.forEach {
		when(it)
		{
			is ExposedDropdownItem.Standard -> DropdownMenuItem(
				enabled = it.enabled,
				onClick = {
					it.onClick(onDrawerClose)
				},
				content = it.content
			)
			is ExposedDropdownItem.Custom -> it.content()
		}
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
