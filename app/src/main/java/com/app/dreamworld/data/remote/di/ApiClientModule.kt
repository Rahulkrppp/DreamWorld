package com.app.dreamworld.data.remote.di

import android.content.Context
import com.app.dreamworld.BuildConfig
import com.app.dreamworld.data.remote.ApiService
import com.app.dreamworld.util.extension.getAndroidDeviceId
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier


@Qualifier
annotation class MeetUp

@Module
@InstallIn(SingletonComponent::class)
class ApiClientModule {

    @Provides //actual instance is created here
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    fun provideApplication(@ApplicationContext appContext: Context): Context {
        return appContext.applicationContext
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object NetworkModule {

        @Provides
        fun provideGson(): Gson = GsonBuilder().setLenient().create()

        @Provides
        fun provideOkHttpClient(context: Context): OkHttpClient {
            val builder = OkHttpClient.Builder()

            // Interceptor for common headers and query params
//            builder.addInterceptor { chain ->
//                val original = chain.request()
//                val url = original.url.newBuilder()
//                    .addQueryParameter("deviceType", "android")
//                    .addQueryParameter("deviceId", context.getAndroidDeviceId())
//                    .build()
//
//                val requestBuilder = original.newBuilder().url(url)
//                requestBuilder.header("Content-Type", "application/json")
//
//                if (!original.url.pathSegments.contains("appVersion") &&
//                    !original.url.pathSegments.contains("staticpages")) {
//                    requestBuilder.header("access-key", /*BaseApplication.tenantSharedPreference.getPref(EasyPref.ACCESS_KEY, "")*/ "")
//                    requestBuilder.header("authorization", "Bearer " + /*BaseApplication.sharedPreference.getPref(EasyPref.USER_ACCESS_TOKEN*/"")
//                }
//
//                requestBuilder.header(
//                    "Accept-Language", /*BaseApplication.languageSharedPreference.getLanguagePref(EasyPref.CURRENT_LANGUAG*/"")
//
//                chain.proceed(requestBuilder.build())
//            }

            // Data converter interceptor
            // builder.addInterceptor(DataConverterInterceptor())

            // Response interceptor for token refresh logic
            builder.addInterceptor { chain ->
                val response = chain.proceed(chain.request())

                // Wrap the response for custom logic
                val responseBody = response.body
                /* if (responseBody != null) {
                     val resString = responseBody.string()
                     try {
                         val baseResponse = Gson().fromJson(resString, HBBaseResponse::class.java)
                         if (baseResponse.isAuthenticationError) {
                             // Launch coroutine to refresh token and retry
                             CoroutineScope(Dispatchers.IO).launch {
                                 val postData = HashMap<String, String>()
                                 val headerData = HashMap<String, String>()

                                 headerData["access-key"] = BaseApplication.tenantSharedPreference.getPref(EasyPref.ACCESS_KEY, "")
                                 headerData["authorization"] = "Bearer " + BaseApplication.sharedPreference.getPref(EasyPref.USER_ACCESS_TOKEN, "")
                                 postData["refreshToken"] = BaseApplication.sharedPreference.getPrefModel(EasyPref.USER_DATA, User::class.java)?.refreshToken.toBlankString()

                                 val call1 = provideSourceApiService().callGenerateNewAccessToken(headerData, postData)
                                 call1.enqueue(object : Callback<WSObjectResponse<RefreshToken>> {
                                     override fun onResponse(
                                         call: Call<WSObjectResponse<RefreshToken>>,
                                         response: retrofit2.Response<WSObjectResponse<RefreshToken>>
                                     ) {
                                         val newToken = response.body()?.data?.accessToken.toBlankString()
                                         BaseApplication.sharedPreference.setPref(EasyPref.USER_ACCESS_TOKEN, newToken)

                                         val userData = BaseApplication.sharedPreference.getPrefModel(EasyPref.USER_DATA, User::class.java)
                                         userData?.refreshToken = response.body()?.data?.refreshToken.toBlankString()
                                         BaseApplication.sharedPreference.setPref(EasyPref.USER_DATA, userData!!)
                                         BaseApplication.application.callApi()
                                     }

                                     override fun onFailure(call: Call<WSObjectResponse<RefreshToken>>, t: Throwable) {
                                         Log.e("TokenRefresh", "Failure: $t")
                                     }
                                 })
                             }
                         }
                     } catch (e: Exception) {
                         e.printStackTrace()
                     }

                     return@addInterceptor response.newBuilder()
                         .body(ResponseBody.create(responseBody.contentType(), resString))
                         .build()
                 }*/
                response
            }

            // Logging interceptor
            val logging = HttpLoggingInterceptor()
            logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            builder.addInterceptor(logging)

            builder.connectTimeout(10, TimeUnit.SECONDS)
            builder.writeTimeout(120, TimeUnit.SECONDS)

            return builder.build()
        }

        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }


        @Provides
        fun provideApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }

}

