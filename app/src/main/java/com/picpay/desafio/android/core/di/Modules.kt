package com.picpay.desafio.android.core.di

import android.content.Context
import androidx.room.Room
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.data.UsersRepositoryImpl
import com.picpay.desafio.android.data.local.PicpayDatabase
import com.picpay.desafio.android.data.mapper.UsersMapper
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.domain.repository.UsersRepository
import com.picpay.desafio.android.domain.usecase.UsersUseCase
import com.picpay.desafio.android.domain.usecase.UsersUseCaseImpl
import com.picpay.desafio.android.presentation.GetUsersViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://careers.picpay.com/tests/mobdev/"

val networkModule = module {
    single { provideOkhttp() }
    single { provideRetrofit(okHttpClient = get()) }
    single { provideService(retrofit = get()) }
}

val domainModule = module {
    single { UsersMapper() }
    single<UsersUseCase> { UsersUseCaseImpl(repository = get()) }
}

val repositoryModule = module {
    single<UsersRepository> {
        UsersRepositoryImpl(
            api = get(),
            mapper = get(),
            userEntityDao = get<PicpayDatabase>().userEntityDao
        )
    }
}

val viewModelModule = module {
    viewModel { GetUsersViewModel(useCase = get()) }
}

val databaseModule = module {
    single { provideDatabase(androidContext()) }
}

private fun provideDatabase(androidContext: Context): PicpayDatabase =
    Room.databaseBuilder(
        androidContext.applicationContext,
        PicpayDatabase::class.java,
        "picpay_database"
    )
        .fallbackToDestructiveMigration()
        .build()

private fun provideService(retrofit: Retrofit) = retrofit.create(PicPayService::class.java)

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

private fun provideOkhttp(): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
