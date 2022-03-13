package com.walmart.test.di

import android.app.Application
import androidx.room.Room
import com.walmart.test.network.NasaAPI
import com.walmart.test.network.NasaRepository
import com.walmart.test.network.NasaRepositoryImpl
import com.walmart.test.room.NasaDAO
import com.walmart.test.room.NasaDataBase
import com.walmart.test.viewmodel.NasaViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit




val viewModelModule = module {

    viewModel { NasaViewModel(get()) }
}

val apiModule = module {

    fun provideNasaApi(): NasaAPI {
        val retrofit : Retrofit by inject(Retrofit::class.java) { parametersOf(Constants.BASE_URL) }
        return retrofit.create(NasaAPI::class.java)
    }

    single { provideNasaApi() }
}

val databaseModule = module {

    fun provideDatabase(application: Application): NasaDataBase {
        return Room.databaseBuilder(application, NasaDataBase::class.java, "wal_test.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    fun provideDao(database: NasaDataBase): NasaDAO {
        return database.nasaDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideNasaRepository(nasaAPI: NasaAPI, nasaDAO: NasaDAO): NasaRepository {
        return NasaRepositoryImpl(nasaAPI, nasaDAO)
    }

    single { provideNasaRepository(get(), get()) }
}
