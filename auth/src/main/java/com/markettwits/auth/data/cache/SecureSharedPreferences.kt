package com.markettwits.auth.data.cache

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


interface SecureSharedPreferences {
    fun provideMasterKeyAlias(): MasterKey
    fun provideSecureSharedPreference(): SharedPreferences
    class Base(private val context: Context, private val name: String) : SecureSharedPreferences {
        override fun provideMasterKeyAlias() = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        override fun provideSecureSharedPreference() =
            EncryptedSharedPreferences.create(
                context,
                name,
                provideMasterKeyAlias(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

    }
}
