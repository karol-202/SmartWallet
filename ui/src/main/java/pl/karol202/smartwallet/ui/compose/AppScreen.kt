package pl.karol202.smartwallet.ui.compose

import androidx.annotation.StringRes
import pl.karol202.smartwallet.ui.R

enum class AppScreen(@StringRes val titleRes: Int)
{
	TRANSACTIONS(R.string.screen_transactions);

	companion object
	{
		val default = TRANSACTIONS
		val all = values().toList()
	}

	val route get() = name
}