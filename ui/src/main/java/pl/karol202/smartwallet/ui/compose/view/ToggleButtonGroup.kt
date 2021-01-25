package pl.karol202.smartwallet.ui.compose.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import pl.karol202.smartwallet.ui.compose.view.ToggleButtonPosition.*

private const val toggleButtonBackgroundOpacity = 0.2f
private const val toggleButtonBorderOpacity = 0.3f
private const val toggleButtonBorderDisabledOpacity = 0.2f
private val toggleButtonBorderSize = 1.dp

@Composable
fun ToggleButtonGroup(modifier: Modifier = Modifier,
                      content: ToggleButtonGroupScope.() -> Unit)
{
	val scope = ToggleButtonGroupScopeImpl().also(content)

	Row(modifier = modifier) {
		scope.items.forEachIndexed { index, item ->
			ToggleButton(
				item = item,
				position = when(index)
				{
					0 -> FIRST
					scope.items.size - 1 -> LAST
					else -> MIDDLE
				}
			)
		}
	}
}

enum class ToggleButtonPosition
{
	FIRST, MIDDLE, LAST
}

@Composable
private fun ToggleButton(item: ToggleButtonGroupItem,
                         position: ToggleButtonPosition)
{
	val currentBackgroundColor =
			if(item.checked) MaterialTheme.colors.primary.copy(alpha = toggleButtonBackgroundOpacity)
			else MaterialTheme.colors.surface
	val currentContentColor =
			if(item.enabled) MaterialTheme.colors.primary
			else MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
	val currentBorderColor =
			if(item.enabled) MaterialTheme.colors.primary.copy(alpha = toggleButtonBorderOpacity)
			else MaterialTheme.colors.onSurface.copy(alpha = toggleButtonBorderDisabledOpacity)

	val shape = when(position)
	{
		FIRST -> MaterialTheme.shapes.small.copy(topRight = CornerSize(0.dp), bottomRight = CornerSize(0.dp))
		MIDDLE -> RectangleShape
		LAST -> MaterialTheme.shapes.small.copy(topLeft = CornerSize(0.dp), bottomLeft = CornerSize(0.dp))
	}
	val border = BorderStroke(
		width = toggleButtonBorderSize,
		color = currentBorderColor
	)

	Surface(
		modifier = Modifier
				.clickable(
					enabled = item.enabled,
					onClick = item.onClick,
					indication = rememberRipple(color = currentBackgroundColor)
				),
		color = currentBackgroundColor,
		contentColor = currentContentColor,
		shape = shape,
		border = border,
		content = {
			Providers(AmbientContentAlpha provides currentContentColor.alpha) {
				ProvideTextStyle(
					value = MaterialTheme.typography.button,
					content = {
						Box(
							modifier = Modifier
									.defaultMinSizeConstraints(
										minWidth = ButtonDefaults.MinWidth,
										minHeight = ButtonDefaults.MinHeight
									)
									.padding(ButtonDefaults.ContentPadding),
							contentAlignment = Alignment.Center,
							content = { item.content() }
						)
					}
				)
			}
		}
	)
}

interface ToggleButtonGroupScope
{
	fun item(checked: Boolean, enabled: Boolean = true, onClick: () -> Unit, content: @Composable () -> Unit)
}

private data class ToggleButtonGroupItem(val checked: Boolean,
                                         val enabled: Boolean,
                                         val onClick: () -> Unit,
                                         val content: @Composable () -> Unit)

private class ToggleButtonGroupScopeImpl : ToggleButtonGroupScope
{
	var items = emptyList<ToggleButtonGroupItem>()
		private set

	override fun item(checked: Boolean, enabled: Boolean, onClick: () -> Unit, content: @Composable () -> Unit)
	{
		items = items + ToggleButtonGroupItem(checked, enabled, onClick, content)
	}
}
