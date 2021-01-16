package pl.karol202.smartwallet.ui.compose

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument
import com.livefront.sealedenum.GenSealedEnum

sealed class Route
{
	object Transactions : Route()
	{
		override val route = "transactions"

		fun constructRoute() = route
	}

	object TransactionCreate : Route()
	{
		override val route = "transactions/new/edit"

		fun constructRoute() = route
	}

	object TransactionEdit : Route()
	{
		const val ARG_TRANSACTION_ID = "transactionId"

		override val route = "transactions/{$ARG_TRANSACTION_ID}/edit"
		override val args = listOf(navArgument(ARG_TRANSACTION_ID) { type = NavType.LongType })

		fun constructRoute(transactionId: Long) = "transactions/$transactionId/edit"
	}

	@GenSealedEnum
	companion object
	{
		val default = Transactions
	}

	abstract val route: String
	open val args: List<NamedNavArgument> = emptyList()
}
