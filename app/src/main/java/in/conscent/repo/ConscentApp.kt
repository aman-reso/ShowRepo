package `in`.conscent.repo

import `in`.conscent.mylibrary.apimodule.ApiModule
import android.app.Application
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent

@Module(includes = [ApiModule::class])
@InstallIn(SingletonComponent::class)
@HiltAndroidApp
class ConscentApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}