package pl.karol202.smartwallet.framework.provider

import android.content.Context
import pl.karol202.smartwallet.data.provider.DefaultNameProvider
import pl.karol202.smartwallet.framework.R

class DefaultNameProviderImpl(private val context: Context) : DefaultNameProvider
{
	override fun getDefaultOthersCategoryName() =
			context.getString(R.string.others_category_default_name)
}
