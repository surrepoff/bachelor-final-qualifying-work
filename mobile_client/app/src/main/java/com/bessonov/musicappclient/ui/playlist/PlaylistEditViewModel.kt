package com.bessonov.musicappclient.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistEditViewModel : ViewModel() {
    private val _currentTrackIdList = MutableLiveData<List<Int>>()
    val currentTrackIdList: LiveData<List<Int>> get() = _currentTrackIdList

    private val _addTrackIdList = MutableLiveData<List<Int>>()
    val addTrackIdList: LiveData<List<Int>> get() = _addTrackIdList

    private val _removeTrackIdList = MutableLiveData<List<Int>>()
    val removeTrackIdList: LiveData<List<Int>> get() = _removeTrackIdList

    fun setCurrentTrackIdList(currentTrackIdList: List<Int>) {
        _currentTrackIdList.value = currentTrackIdList
    }

    fun setAddTrackIdList(addTrackIdList: List<Int>) {
        _addTrackIdList.value = addTrackIdList
    }

    fun setRemoveTrackIdList(removeTrackIdList: List<Int>) {
        _removeTrackIdList.value = removeTrackIdList
    }
}