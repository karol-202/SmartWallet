package pl.karol202.smartwallet.data.datasource

interface IdDataSource
{
	fun createRandomId(): String

	fun getStaticId(subject: String): String
}
