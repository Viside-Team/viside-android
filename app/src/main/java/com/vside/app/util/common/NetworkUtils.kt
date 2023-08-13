package com.vside.app.util.common

import com.google.gson.GsonBuilder
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.vside.app.util.log.VsideLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


suspend fun <T> handleApiResponse(
    response: ApiResponse<T>,
    onSuccess: suspend (ApiResponse.Success<T>) -> Unit,
    onError: suspend (ApiResponse.Failure.Error<T>) -> Unit = {
        VsideLog.d("it.errorBody : ${it.errorBody}")
        VsideLog.d("it.headers : ${it.headers}")
        VsideLog.d("it.raw : ${it.raw}")
        VsideLog.d("it.response : ${it.response}")
        VsideLog.d("it.statusCode : ${it.statusCode}")
    },
    onException: suspend (ApiResponse.Failure.Exception<T>) -> Unit = {
        VsideLog.d("it.message : ${it.message}")
        VsideLog.d("it.exception : ${it.exception}")
    }
) {
    when (response) {
        is ApiResponse.Success<T> -> onSuccess(response)
        is ApiResponse.Failure.Error<T> -> onError(response)
        is ApiResponse.Failure.Exception<T> -> onException(response)
    }
}

fun createOkHttp(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    return OkHttpClient.Builder()
        .connectTimeout(15L, TimeUnit.SECONDS)
        .readTimeout(15L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    val gson = GsonBuilder().setLenient().create()
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
        .build()
}

inline fun <reified T> createService(retrofit: Retrofit): T = retrofit.create(T::class.java)
