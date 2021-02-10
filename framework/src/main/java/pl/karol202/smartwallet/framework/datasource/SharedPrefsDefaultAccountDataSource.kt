package pl.karol202.smartwallet.framework.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import pl.karol202.smartwallet.data.datasource.DefaultAccountDataSource
import pl.karol202.smartwallet.framework.sharedprefs.observeString

private const val KEY_DEFAULT_ACCOUNT_ID = "default_account_id"

class SharedPrefsDefaultAccountDataSource(private val sharedPrefs: SharedPreferences) : DefaultAccountDataSource
{
	override val defaultAccountId = sharedPrefs.observeString(KEY_DEFAULT_ACCOUNT_ID, null)

	override fun setDefaultAccountId(accountId: String?)
	{
		sharedPrefs.edit { putString(KEY_DEFAULT_ACCOUNT_ID, accountId) }
	}
}
