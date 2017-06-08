package me.vickychijwani.kotlinkoans

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.annotation.Nullable
import me.vickychijwani.kotlinkoans.network.ProductionHttpClientFactory
import okhttp3.OkHttpClient
import java.io.File


open class KotlinKoansApplication(): Application() {

    private val HTTP_CACHE_PATH = "http_cache"

    companion object Singleton {
        private lateinit var sInstance: KotlinKoansApplication
        fun getInstance(): KotlinKoansApplication {
            return sInstance
        }
    }

    protected lateinit var mOkHttpClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        initOkHttpClient()
    }

    open fun initOkHttpClient() {
        val cacheDir = createCacheDir(this)
        mOkHttpClient = ProductionHttpClientFactory().create(cacheDir)
    }

    fun getOkHttpClient(): OkHttpClient {
        return mOkHttpClient
    }

    open fun addDebugDrawer(activity: Activity) {
        // no-op, overridden in debug build
    }

    @Nullable protected fun createCacheDir(context: Context): File? {
        var cacheDir = context.applicationContext.externalCacheDir
        if (cacheDir == null) {
            cacheDir = context.applicationContext.cacheDir
        }

        val cache = File(cacheDir, HTTP_CACHE_PATH)
        if (cache.exists() || cache.mkdirs()) {
            return cache
        } else {
            return null
        }
    }

}
