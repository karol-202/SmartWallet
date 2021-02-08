package pl.karol202.smartwallet.domain.entity

data class Category<I : Id>(val id: I,
                            val name: String,
                            val type: Type,
                            val isOthers: Boolean)
{
	enum class Type
	{
		EXPENSE, INCOME
	}
}
