package pl.karol202.smartwallet.ui.compose

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument
import com.livefront.sealedenum.GenSealedEnum

sealed class Route
{
	object Transactions : Route(), NoArg
	{
		override val route = "transactions"
	}

	object TransactionCreate : Route(), NoArg
	{
		override val route = "transactions/new/edit"
	}

	object TransactionEdit : Route()
	{
		const val ARG_TRANSACTION_ID = "transactionId"

		override val route = "transactions/{$ARG_TRANSACTION_ID}/edit"
		override val args = listOf(navArgument(ARG_TRANSACTION_ID) { type = NavType.StringType })

		fun constructRoute(transactionId: String) = "transactions/$transactionId/edit"
	}

	object Categories : Route(), NoArg
	{
		override val route = "categories"
	}

	object CategoryCreate : Route(), NoArg
	{
		override val route = "categories/new/edit"
	}

	object CategoryEdit : Route()
	{
		const val ARG_CATEGORY_ID = "categoryId"

		override val route = "categories/{$ARG_CATEGORY_ID}/edit"
		override val args = listOf(navArgument(ARG_CATEGORY_ID) { type = NavType.StringType })

		fun constructRoute(categoryId: String) = "categories/$categoryId/edit"
	}

	interface NoArg

	@GenSealedEnum
	companion object
	{
		val default = Categories
	}

	abstract val route: String
	open val args: List<NamedNavArgument> = emptyList()
}

fun <A> A.constructRoute() where A : Route, A : Route.NoArg = route
