package pl.karol202.smartwallet.framework.datasource

import pl.karol202.smartwallet.data.datasource.IdDataSource
import java.util.*

private const val STATIC_ID_PREFIX = "static_"

class UuidIdDataSource : IdDataSource
{
	override fun createRandomId() = UUID.randomUUID().toString()

	override fun getStaticId(subject: String) = "$STATIC_ID_PREFIX$subject"
}
