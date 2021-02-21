package com.example.stonks.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.example.stonks.network.MarsProperty

class InfoViewModel ( marsProperty: MarsProperty,
                      app: Application) : AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<MarsProperty>()
    val selectedProperty: LiveData<MarsProperty>
        get() = _selectedProperty
    init {
        _selectedProperty.value = marsProperty
    }
    val displayPropertyPrice = Transformations.map(selectedProperty) {
//        app.applicationContext.getString(
//                when (it.isRental) {
//                    true -> R.string.display_price_monthly_rental
//                    false -> R.string.display_price
//                }, it.price)
    }
    val displayPropertyType = Transformations.map(selectedProperty) {
//        app.applicationContext.getString(R.string.display_type,
//                app.applicationContext.getString(
//                        when (it.isRental) {
//                            true -> R.string.type_rent
//                            false -> R.string.type_sale
//                        }))
    }
}
class InfoViewModelFactory(
        private val marsProperty: MarsProperty,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InfoViewModel::class.java)) {
            return InfoViewModel(marsProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}