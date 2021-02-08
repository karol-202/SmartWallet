package pl.karol202.smartwallet.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument
import com.livefront.sealedenum.GenSealedEnum
import pl.karol202.smartwallet.ui.R

interface Route
{
	val route: String
	val args: List<NamedNavArgument> get() = emptyList()

	interface TopLevel : Route
	{
		val nameResource: Int
		val icon: ImageVector
	}
}

sealed class Routes : Route
{
	object Transactions : Routes(), Route.TopLevel
	{
		override val route = "transactions"
		override val nameResource = R.string.screen_transactions
		override val icon = Icons.Filled.ShoppingBasket
	}

	object TransactionEdit : Routes()
	{
		const val ARG_TRANSACTION_ID = "transactionId"

		override val route = "transactionEdit?$ARG_TRANSACTION_ID={$ARG_TRANSACTION_ID}"
		override val args = listOf(navArgument(ARG_TRANSACTION_ID) { type = NavType.StringType; nullable = true })

		fun constructRoute(transactionId: String? = null) =
				"transactionEdit" + constructQueryParams(ARG_TRANSACTION_ID to transactionId)
	}

	object Categories : Routes(), Route.TopLevel
	{
		override val route = "categories"
		override val nameResource = R.string.screen_categories
		override val icon = Icons.Filled.Bookmark
	}

	object CategoryEdit : Routes()
	{
		const val ARG_CATEGORY_ID = "categoryId"

		override val route = "categoryEdit?$ARG_CATEGORY_ID={$ARG_CATEGORY_ID}"
		override val args = listOf(navArgument(ARG_CATEGORY_ID) { type = NavType.StringType; nullable = true })

		fun constructRoute(categoryId: String? = null) =
				"categoryEdit" + constructQueryParams(ARG_CATEGORY_ID to categoryId)
	}

	object SubcategoryEditNew : Routes()
	{
		const val ARG_CATEGORY_ID = "categoryId"

		override val route = "subcategoryNew?$ARG_CATEGORY_ID={$ARG_CATEGORY_ID}"
		override val args = listOf(navArgument(ARG_CATEGORY_ID) { type = NavType.StringType })

		fun constructRoute(categoryId: String) =
				"subcategoryNew" + constructQueryParams(ARG_CATEGORY_ID to categoryId)
	}

	object SubcategoryEditExisting : Routes()
	{
		const val ARG_SUBCATEGORY_ID = "subcategoryId"

		override val route = "subcategoryEdit?$ARG_SUBCATEGORY_ID={$ARG_SUBCATEGORY_ID}"
		override val args = listOf(navArgument(ARG_SUBCATEGORY_ID) { type = NavType.StringType })

		fun constructRoute(subcategoryId: String) =
				"subcategoryEdit" + constructQueryParams(ARG_SUBCATEGORY_ID to subcategoryId)
	}

	object Accounts : Routes(), Route.TopLevel
	{
		override val route = "accounts"
		override val nameResource = R.string.screen_accounts
		override val icon = Icons.Filled.AccountBalance
	}

	object AccountEdit : Routes()
	{
		const val ARG_ACCOUNT_ID = "accountId"

		override val route = "accountEdit?$ARG_ACCOUNT_ID={$ARG_ACCOUNT_ID}"
		override val args = listOf(navArgument(ARG_ACCOUNT_ID) { type = NavType.StringType; nullable = true })

		fun constructRoute(accountId: String? = null) =
				"accountEdit" + constructQueryParams(ARG_ACCOUNT_ID to accountId)
	}

	@GenSealedEnum
	companion object
	{
		val default = Transactions
		val topLevel = values.filterIsInstance<Route.TopLevel>()
	}
}

fun Route.TopLevel.constructRoute() = route

private fun constructQueryParams(vararg params: Pair<String, String?>) =
		if(params.isEmpty()) ""
		else "?" + params.filter { (_, value) -> value != null }
				.joinToString(separator = "&") { (key, value) -> "$key=$value" }
