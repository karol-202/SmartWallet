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
                        isOpen: Boolean,
                        onOpenChange: (Boolean) -> Unit,
                        modifier: Modifier,
                        textFieldModifier: Modifier,
                        label: @Composable () -> Unit,
                        content: @Composable () -> Unit)
{
	val focusRequester = remember { FocusRequester() }
	var isFocused by remember { mutableStateOf(false) }

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
			onDismissRequest = { onOpenChange(false) },
			dropdownContent = { content() }
		)
		Spacer(
			modifier = Modifier
					.matchParentSize()
					.clickable {
						onOpenChange(true)
						focusRequester.requestFocus()
					}
		)
	}
}
