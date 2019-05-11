package com.example.stackoverflowuser.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Filter

/**
 * Created by olivier.goutay on 4/28/17.
 */

class NetworkProvider
/**
 * Constructs the different services we use
 */
private constructor() {

    companion object {

        /**
         * The volatile static singleton of [NetworkProvider]
         */
        @Volatile
        private var networkProvider: NetworkProvider? = null

        /**
         * Gets the singleton of the NetworkProvider, to avoid re-constructing retrofit etc...
         * @return The instance of [NetworkProvider]
         */
        val instance: NetworkProvider
            get() {
                synchronized(NetworkProvider::class.java) {
                    if (networkProvider == null) {
                        networkProvider = NetworkProvider()
                    }
                }
                return networkProvider!!
            }
    }

    private fun provideDefaultOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val timeOut = 120
        builder.connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(timeOut.toLong(), TimeUnit.SECONDS)
        builder.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain?): Response {
                val request = chain?.request()?.newBuilder()
                        ?.addHeader("Content-Type", "application/json")
                        ?.build()
                return chain!!.proceed(request!!);

            }
        })

        return builder.build()
    }

    fun <T> provideApi(baseUrl: String, apiClass: Class<T>): T {
        return provideApi(baseUrl, apiClass, provideDefaultOkHttpClient())
    }

    fun <T> provideApi(baseUrl: String, apiClass: Class<T>, okHttpClient: OkHttpClient): T {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
        return retrofit.create(apiClass)
    }
}
