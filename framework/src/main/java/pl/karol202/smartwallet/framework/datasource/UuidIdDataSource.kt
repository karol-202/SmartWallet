package pl.karol202.smartwallet.framework.datasource

import pl.karol202.smartwallet.data.datasource.IdDataSource
import java.util.*

class UuidIdDataSource : IdDataSource
{
	override fun createRandomId() = UUID.randomUUID().toString()
}
