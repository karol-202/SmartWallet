package pl.karol202.smartwallet.ui.compose.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val lightModeColors = lightColors(primary = Color(0xFF00BCD4),
                                  primaryVariant = Color(0xFF0097A7),
                                  onPrimary = Color.White,
                                  secondary = Color(0xFF7C4DFF),
                                  onSecondary = Color.White)

val darkModeColors = darkColors(primary = Color(0xFF00BCD4),
                                primaryVariant = Color(0xFF0097A7),
                                onPrimary = Color.White,
                                secondary = Color(0xFF7C4DFF),
                                onSecondary = Color.White)

object AppColors
{
    val textTransactionAmountExpense = Color(0xFFE20505)
    val textTransactionAmountIncome = Color(0xFF46C900)
}
