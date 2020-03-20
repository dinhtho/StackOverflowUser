package com.example.stackoverflowuser.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    var error = MutableLiveData<Throwable>()
    var loading = MutableLiveData<Boolean>()
}