package com.walmart.test

import com.baseframework.BaseApplication
import com.walmart.test.di.apiModule
import com.walmart.test.di.databaseModule
import com.walmart.test.di.repositoryModule
import com.walmart.test.di.viewModelModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules

class WalEApplication : BaseApplication() {

    val modules = listOf(viewModelModule, apiModule, databaseModule, repositoryModule)

    override fun onCreate() {
        super.onCreate()
        loadKoinModules(modules)
    }

    override fun onTerminate() {
        super.onTerminate()
        unloadKoinModules(modules)
        stopKoin()
    }
}