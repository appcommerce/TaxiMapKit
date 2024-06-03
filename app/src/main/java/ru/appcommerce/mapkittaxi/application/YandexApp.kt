package ru.appcommerce.mapkittaxi.application

import android.app.Application
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.appcommerce.mapkittaxi.di.koinModules
import ru.appcommerce.mapkittaxi.presentation.feature.main.MainActivity
import kotlin.system.exitProcess

class YandexApp: Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("1b9d2881-b860-42e9-8de1-336559b6d7e4")
        initKoin()
        setUncaughtExceptionHandler()
    }

    private fun setUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            Log.e("ApplicationTAG", "UNCAUGHT ERROR CATCHED: ${Log.getStackTraceString(e)}")
        }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@YandexApp)
            modules(koinModules)
        }
    }

}