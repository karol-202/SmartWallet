package pl.karol202.smartwallet.ui.compose.drawer

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.karol202.smartwallet.ui.compose.AmbientRoute
import pl.karol202.smartwallet.ui.compose.Route
import pl.karol202.smartwallet.ui.compose.Routes

@Composable
fun DrawerContent(onRouteSelect: (Route.TopLevel) -> Unit,
                  onDrawerClose: () -> Unit)
{
	ScrollableColumn(
		modifier = Modifier.padding(vertical = 8.dp),
		content = {
			Routes.topLevel.forEach { route ->
				DrawerItem(
					text = stringResource(route.nameResource),
					icon = route.icon,
					selected = AmbientRoute.current == route,
					onClick = {
						onRouteSelect(route)
						onDrawerClose()
					}
				)
			}
		}
	)
}

@Composable
private fun DrawerItem(text: String,
                       icon: ImageVector,
                       selected: Boolean,
                       onClick: () -> Unit)
{
	ListItem(
		modifier = Modifier
				.clickable(onClick = onClick)
				.padding(horizontal = 8.dp, vertical = 4.dp)
				.background(
					color = if(selected) MaterialTheme.colors.primary.copy(alpha = 0.22f) else MaterialTheme.colors.surface,
					shape = RoundedCornerShape(4.dp)
				)
				.preferredHeight(40.dp),
		icon = {
			Icon(
				imageVector = icon,
				tint = if(selected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
			)
		},
		text = {
			Text(
				text = text,
				style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
				color = if(selected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
			)
		}
	)
}
