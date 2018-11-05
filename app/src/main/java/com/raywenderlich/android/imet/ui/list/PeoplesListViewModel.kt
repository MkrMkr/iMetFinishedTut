package com.raywenderlich.android.imet.ui.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.util.Log
import com.raywenderlich.android.imet.IMetApp
import com.raywenderlich.android.imet.data.model.People

class PeoplesListViewModel(application: Application) : AndroidViewModel(application) {
    private val peopleRepository = getApplication<IMetApp>().getPeopleRepository()
    private val peopleList = MediatorLiveData<List<People>>()

    init {
        getAllPeople()
    }

    fun getPeopleList(): LiveData<List<People>> {
        return peopleList
    }

    fun getAllPeople() {
        peopleList.addSource(peopleRepository.getAllPeople()) {
            Log.i("testPull", "pull people from view model!")
            peopleList.postValue(it)
        }
    }

    fun searchPeople(name: String) {
        peopleList.addSource(peopleRepository.findPeople(name), {
            peopleList.postValue(it)
        })
    }
}