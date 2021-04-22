package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayChallengeApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PicPayChallengeApplication)
            val appModules = listOf(
                networkModule,
                repositoryModule,
                viewModelModule,
                domainModule,
                databaseModule
            )
            modules(appModules)
        }
    }
}