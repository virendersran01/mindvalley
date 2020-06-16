package com.frank.mindvalley.ui.channels

import android.util.Log
import androidx.lifecycle.*
import com.frank.mindvalley.models.CategoryModel
import com.frank.mindvalley.models.ChannelModel
import com.frank.mindvalley.models.CourseModel
import com.frank.mindvalley.repositories.ChannelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChannelsViewModel @Inject constructor(private val chanelRepository: ChannelRepository) :
    ViewModel() {

    private var _listNewEpisodes = MutableLiveData<List<CourseModel>?>()
    val listNewEpisodes: LiveData<List<CourseModel>?>
        get() = _listNewEpisodes

    val isEmptyNewEpisodes: LiveData<Boolean> = Transformations.map(_listNewEpisodes) {
        it?.isNullOrEmpty()
    }

    private var _listChannels = MutableLiveData<List<ChannelModel>?>()
    val listChannels: LiveData<List<ChannelModel>?>
        get() = _listChannels

    val isEmptyChannels: LiveData<Boolean> = Transformations.map(_listChannels) {
        it?.isNullOrEmpty()
    }

    private var _listCategories = MutableLiveData<List<CategoryModel>?>()
    val listCategories: LiveData<List<CategoryModel>?>
        get() = _listCategories

    val isEmptyCategories: LiveData<Boolean> = Transformations.map(_listCategories) {
        it?.isNullOrEmpty()
    }

    private var _loadingNewEpisodes = MutableLiveData<Boolean>(true)
    val loadingNewEpisodes: LiveData<Boolean>
        get() = _loadingNewEpisodes


    private var _loadingChannels = MutableLiveData<Boolean>(true)
    val loadingChannels: LiveData<Boolean>
        get() = _loadingChannels

    private var _loadingCategories = MutableLiveData<Boolean>(true)
    val loadingCategories: LiveData<Boolean>
        get() = _loadingCategories

    private var _loadingData = MutableLiveData<Boolean>(false)
    var loadingData: MediatorLiveData<Boolean> = MediatorLiveData()

    init {
        loadingData.value = true
        loadingData.addSource(_loadingNewEpisodes) {
            if (!it) {
                loadingData.postValue(false)
            }

        }
        loadingData.addSource(_loadingChannels) {
            if (!it) {
                loadingData.postValue(false)
            }
        }
        loadingData.addSource(_loadingCategories) {
            if (!it) {
                loadingData.postValue(false)
            }
        }


    }


    private var _refreshingData = MutableLiveData<Boolean>(false)
    val refreshingData: LiveData<Boolean>
        get() = _refreshingData


    fun fetchData() {
        viewModelScope.launch {
            _loadingNewEpisodes.value = true
            getNewEpisodes(true)
            _loadingChannels.value = true
            getChannels(true)

            _loadingCategories.value = true
            getCategories(true)

        }
    }

    fun refresh() {
        viewModelScope.launch {
            _refreshingData.value = true
            getNewEpisodes()
            getChannels()
            getCategories()
            _refreshingData.value = false
        }
    }

    private suspend fun getNewEpisodes(isFromDbFirst: Boolean = false) {
        withContext(Dispatchers.IO) {
            try {
                val result =
                    if (isFromDbFirst) chanelRepository.getNewEpisodesFromDBFirst() else chanelRepository.getNewEpisodes()
                _listNewEpisodes.postValue(result)
                _loadingNewEpisodes.postValue(false)
            } catch (e: Exception) {
                _loadingNewEpisodes.postValue(false)
            }
        }
    }

    private suspend fun getChannels(isFromDbFirst: Boolean = false) {
        withContext(Dispatchers.IO) {
            try {
                val result =
                    if (isFromDbFirst) chanelRepository.getChannelsFromDbFirst() else chanelRepository.getChannels()
                _listChannels.postValue(result)
                _loadingChannels.postValue(false)
            } catch (e: Exception) {
                _loadingChannels.postValue(false)
            }
        }
    }

    private suspend fun getCategories(isFromDbFirst: Boolean = false) {
        withContext(Dispatchers.IO) {
            try {
                val result =
                    if (isFromDbFirst) chanelRepository.getCategoriesFromDbFirst() else chanelRepository.getCategories()
                _listCategories.postValue(result)
                _loadingCategories.postValue(false)
            } catch (e: Exception) {
                _loadingCategories.postValue(false)
            }
        }
    }

}