package com.walmart.test.network

import com.walmart.test.model.NasaResponse
import com.walmart.test.room.NasaDAO
import java.text.SimpleDateFormat
import java.util.*

interface NasaRepository {
    suspend fun getAstronomyPicture() : UseCaseResult<NasaResponse>
}

class NasaRepositoryImpl(private val nasaAPI: NasaAPI, private val nasaDAO: NasaDAO) : NasaRepository {

    override suspend fun getAstronomyPicture(): UseCaseResult<NasaResponse> {
        return try {
            val nasaResponse = nasaAPI.getAstronomyPicture(Constants.API_KEY).await()
            nasaDAO.insert(nasaResponse)
            UseCaseResult.Success(nasaResponse)
        } catch (ex: Exception) {
            return getNasaFromCache()
        }
    }

    fun getNasaFromCache(): UseCaseResult<NasaResponse> {
        return try {
            val nasaResponse = nasaDAO.getNasa()
            nasaResponse.notAvailable = !(convertLongToTime(System.currentTimeMillis()).equals(nasaResponse.date))
            UseCaseResult.Success(nasaResponse)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex.cause)
        }
    }

    @android.annotation.SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }
}