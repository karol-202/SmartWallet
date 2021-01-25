package pl.karol202.smartwallet.interactors.filter

interface FilterScope<T>
{
	fun filter(filter: (T) -> Boolean)
}

private class FilterScopeImpl<T> : FilterScope<T>
{
	var filters = emptyList<(T) -> Boolean>()
		private set

	override fun filter(filter: (T) -> Boolean)
	{
		filters = filters + filter
	}
}

typealias FilterFunction<T> = FilterScope<T>.() -> Unit

fun <T> noFilters(): FilterFunction<T> = { }

fun <T> List<T>.filterWith(function: FilterFunction<T>) = filter { item ->
	FilterScopeImpl<T>().apply(function).filters.all { it(item) }
}
