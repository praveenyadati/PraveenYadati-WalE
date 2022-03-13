package com.walmart.test.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.walmart.test.model.NasaResponse

@Database(entities = [NasaResponse::class], version = 1, exportSchema = false)

abstract class NasaDataBase : RoomDatabase() {
    abstract val nasaDao: NasaDAO
}