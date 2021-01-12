package pl.karol202.smartwallet.ui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(darkMode: Boolean = isSystemInDarkTheme(),
             content: @Composable () -> Unit)
{
	MaterialTheme(colors = if(darkMode) darkModeColors else lightModeColors,
	              content = content)
}