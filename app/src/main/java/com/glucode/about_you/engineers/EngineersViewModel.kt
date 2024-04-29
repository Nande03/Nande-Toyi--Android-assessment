package com.glucode.about_you.engineers

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.mockdata.MockData


class EngineersViewModel : ViewModel() {
    private val _engineers = MutableLiveData<List<Engineer>>()
    val engineers: LiveData<List<Engineer>> = _engineers

    init {
        Log.d("EngineersViewModel", "Number of engineers: ${MockData.engineers.size}")
        _engineers.value = MockData.engineers
    }

    fun sortingEngineersByYears() {
        _engineers.value = _engineers.value?.sortedBy { it.quickStats.years }
    }

    fun sortEngineersByCoffee() {
        _engineers.value = _engineers.value?.sortedBy { it.quickStats.coffees }
    }

    // Function to sort engineers by bugs fixed
    fun sortEngineersByBugs() {
        _engineers.value = _engineers.value?.sortedBy { it.quickStats.bugs }
    }

    // Function to update engineer's image URL
    fun updateEngineerImage(position: Int, imageUrl: Uri?) {
        val currentList = _engineers.value.orEmpty().toMutableList()
        if (position >= 0 && position < currentList.size) {
            currentList[position] = currentList[position].copy(imageUrl = imageUrl)
            _engineers.value = currentList
        }
    }
}