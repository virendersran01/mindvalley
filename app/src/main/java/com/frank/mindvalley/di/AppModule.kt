package com.frank.mindvalley.di

import android.content.Context
import androidx.room.Room
import com.frank.mindvalley.common.Config
import com.frank.mindvalley.db.MindValleyDB
import com.frank.mindvalley.db.daos.ChannelDao
import com.frank.mindvalley.network.ChannelService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideChannelService(retrofit: Retrofit): ChannelService {
        return retrofit.create(ChannelService::class.java)
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder().addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("${Config.BASE_URL}")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOKHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return MoshiConverterFactory.create(moshi)
    }


    @Singleton
    @Provides
    fun provideMindValleyDb(context: Context): MindValleyDB {
        return Room.databaseBuilder(context, MindValleyDB::class.java, "mindvalley.db")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideChannelDao(db: MindValleyDB): ChannelDao {
        return db.channelDao()
    }


}