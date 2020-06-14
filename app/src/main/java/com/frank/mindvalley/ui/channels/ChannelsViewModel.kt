package com.frank.mindvalley.ui.channels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frank.mindvalley.models.CategoryModel
import com.frank.mindvalley.models.ChannelModel
import com.frank.mindvalley.models.CourseModel
import com.frank.mindvalley.repositories.ChannelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChannelsViewModel @Inject constructor(private val chanelRepository: ChannelRepository) : ViewModel() {

    private var _listNewEpisodes = MutableLiveData<List<CourseModel>?>()
    val listNewEpisodes: LiveData<List<CourseModel>?>
        get() = _listNewEpisodes

    private var _listChannels = MutableLiveData<List<ChannelModel>?>()
    val listChannels: LiveData<List<ChannelModel>?>
        get() = _listChannels

    private var _listCategories = MutableLiveData<List<CategoryModel>?>()
    val listCategories: LiveData<List<CategoryModel>?>
        get() = _listCategories

    private var _loadingData = MutableLiveData<Boolean>(false)
    val loadingData: LiveData<Boolean>
        get() = _loadingData

    fun fetchData() {
        viewModelScope.launch {
            _loadingData.value = true
            getNewEpisodes()
            getChannels()
            getCategories()
            _loadingData.value = false
        }
    }

    private suspend fun getNewEpisodes() {
        withContext(Dispatchers.IO) {
            val result = chanelRepository.getNewEpisodes()
            _listNewEpisodes.postValue(result)
        }
    }

    private suspend fun getChannels() {
        withContext(Dispatchers.IO) {
            val result = chanelRepository.getChannels()
            _listChannels.postValue(result)
        }
    }

    private suspend fun getCategories() {
        withContext(Dispatchers.IO) {
            val result = chanelRepository.getCategories()
            _listCategories.postValue(result)
        }
    }

}