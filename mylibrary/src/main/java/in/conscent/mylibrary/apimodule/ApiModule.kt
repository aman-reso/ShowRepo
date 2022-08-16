package `in`.conscent.mylibrary.apimodule

import `in`.conscent.mylibrary.constants.AppConstants
import `in`.conscent.mylibrary.network.interfaces.NetworkInterface
import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder().setLenient()
        return gsonBuilder.create()
    }

    /*
     * The method returns the Cache object
     * */
    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        val httpCacheDirectory = File(application.cacheDir, "responses")
        return Cache(httpCacheDirectory, cacheSize)
    }

    /*
     * The method returns the Okhttp object
     * */
    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.cache(cache)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)

        return httpClient.build()
    }


    /*
     * The method returns the Retrofit object
     * */
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(AppConstants.APP_BASE_URL).client(okHttpClient)
            .build()
    }


    /**
     * gives api interface
     */
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): NetworkInterface = retrofit.create(NetworkInterface::class.java)

    companion object {
        const val cacheSize = (5 * 1024 * 1024).toLong()//5MB
    }

}

