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

	object TransactionEdit : Route()
	{
		const val ARG_TRANSACTION_ID = "transactionId"

		override val route = "transactionEdit?$ARG_TRANSACTION_ID={$ARG_TRANSACTION_ID}"
		override val args = listOf(navArgument(ARG_TRANSACTION_ID) { type = NavType.StringType; nullable = true })

		fun constructRoute(transactionId: String? = null) =
				"transactionEdit" + constructQueryParams(ARG_TRANSACTION_ID to transactionId)
	}

	object Categories : Route(), NoArg
	{
		override val route = "categories"
	}

	object CategoryEdit : Route()
	{
		const val ARG_CATEGORY_ID = "categoryId"

		override val route = "categoryEdit?$ARG_CATEGORY_ID={$ARG_CATEGORY_ID}"
		override val args = listOf(navArgument(ARG_CATEGORY_ID) { type = NavType.StringType; nullable = true })

		fun constructRoute(categoryId: String? = null) =
				"categoryEdit" + constructQueryParams(ARG_CATEGORY_ID to categoryId)
	}

	object SubcategoryEdit : Route()
	{
		const val ARG_CATEGORY_ID = "categoryId"
		const val ARG_SUBCATEGORY_ID = "subcategoryId"

		override val route = "categories/{$ARG_CATEGORY_ID}/subcategoryEdit?$ARG_SUBCATEGORY_ID={$ARG_SUBCATEGORY_ID}"
		override val args = listOf(navArgument(ARG_CATEGORY_ID) { type = NavType.StringType; nullable = true },
		                           navArgument(ARG_SUBCATEGORY_ID) { type = NavType.StringType; nullable = true })

		fun constructRoute(categoryId: String, subcategoryId: String? = null) =
				"categories/$categoryId/subcategoryEdit" + constructQueryParams(ARG_SUBCATEGORY_ID to subcategoryId)
	}

	interface NoArg

	@GenSealedEnum
	companion object
	{
		val default = Categories
	}

	abstract val route: String
	open val args: List<NamedNavArgument> = emptyList()

	protected fun constructQueryParams(vararg params: Pair<String, String?>) =
			if(params.isEmpty()) ""
			else "?" + params.filter { (_, value) -> value != null }
					.joinToString(separator = "&") { (key, value) -> "$key=$value" }
}

fun <A> A.constructRoute() where A : Route, A : Route.NoArg = route
