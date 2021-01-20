package pl.karol202.smartwallet.ui.compose.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSizeConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private const val toggleButtonBackgroundOpacity = 0.2f
private const val toggleButtonBorderOpacity = 0.3f
private val toggleButtonBorderSize = 1.dp

@Composable
fun ToggleButtonGroup(modifier: Modifier = Modifier,
                      backgroundColor: Color = MaterialTheme.colors.primary.copy(alpha = toggleButtonBackgroundOpacity),
                      contentColor: Color = MaterialTheme.colors.primary,
                      borderColor: Color = MaterialTheme.colors.primary.copy(alpha = toggleButtonBorderOpacity),
                      content: ToggleButtonGroupScope.() -> Unit)
{
	val scope = ToggleButtonGroupScopeImpl().also(content)

	Row(modifier = modifier) {
		scope.items.forEachIndexed { index, item ->
			val currentBackgroundColor = if(item.checked) backgroundColor else MaterialTheme.colors.surface
			val shape = when(index)
			{
				0 -> MaterialTheme.shapes.small.copy(topRight = CornerSize(0.dp), bottomRight = CornerSize(0.dp))
				scope.items.size - 1 -> MaterialTheme.shapes.small.copy(topLeft = CornerSize(0.dp), bottomLeft = CornerSize(0.dp))
				else -> MaterialTheme.shapes.small
			}
			val border = BorderStroke(
				width = toggleButtonBorderSize,
				color = borderColor
			)

			Surface(
				modifier = Modifier
						.clickable(
							onClick = item.onClick,
							indication = rememberRipple(color = backgroundColor)
						),
				color = currentBackgroundColor,
				contentColor = contentColor,
				shape = shape,
				border = border,
				content = {
					ProvideTextStyle(
						value = MaterialTheme.typography.button,
						content = {
							Row(
								modifier = Modifier
										.defaultMinSizeConstraints(
											minWidth = ButtonDefaults.MinWidth,
											minHeight = ButtonDefaults.MinHeight
										)
										.padding(ButtonDefaults.ContentPadding),
								horizontalArrangement = Arrangement.Center,
								verticalAlignment = Alignment.CenterVertically,
								content = { item.content() }
							)
						}
					)
				}
			)
		}
	}
}

interface ToggleButtonGroupScope
{
	fun item(checked: Boolean, onClick: () -> Unit, content: @Composable () -> Unit)
}

private data class ToggleButtonGroupItem(val checked: Boolean,
                                         val onClick: () -> Unit,
                                         val content: @Composable () -> Unit)

private class ToggleButtonGroupScopeImpl : ToggleButtonGroupScope
{
	var items = emptyList<ToggleButtonGroupItem>()
		private set

	override fun item(checked: Boolean, onClick: () -> Unit, content: @Composable () -> Unit)
	{
		items = items + ToggleButtonGroupItem(checked, onClick, content)
	}
}
