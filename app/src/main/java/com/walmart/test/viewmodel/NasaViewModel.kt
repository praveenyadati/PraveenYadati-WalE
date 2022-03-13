package com.walmart.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.walmart.test.model.NasaResponse
import com.walmart.test.network.NasaRepository
import com.walmart.test.network.UseCaseResult
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class NasaViewModel(private val nasaRepository: NasaRepository) : ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    val showLoading = MutableLiveData<Boolean>()

    private val _nasaData = MutableLiveData<NasaResponse>()
    val jokeData: LiveData<NasaResponse> get() = _nasaData

    val isNetworkAvailable = MutableLiveData<Boolean>()

    fun getAstronomyPicture() {
        showLoading.value = true
        launch {
            coroutineContext
            val result: UseCaseResult<NasaResponse>? = withContext(Dispatchers.IO) { nasaRepository.getAstronomyPicture() }
            showLoading.value = false
            if (result == null) {
                _nasaData.value = null
            } else {
                when (result) {
                    is UseCaseResult.Success ->  _nasaData.value = result.nasa
                    is UseCaseResult.Error -> _nasaData.value = null

                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}