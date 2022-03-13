package com.walmart.test.room

import androidx.room.*
import com.walmart.test.model.NasaResponse

@Dao
interface NasaDAO {

    @Insert
    fun insert(joke: NasaResponse)

    @Query("SELECT * FROM NasaResponse ORDER BY date DESC LIMIT 1")
    fun getNasa(): NasaResponse
}