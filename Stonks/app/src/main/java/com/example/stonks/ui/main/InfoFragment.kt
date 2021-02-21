package com.example.stonks.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stonks.databinding.InfoFragmentBinding

class InfoFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val application = requireNotNull(activity).application
        val binding = InfoFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val marsProperty = InfoFragmentArgs.fromBundle(arguments!!).selectedProperty
        val viewModelFactory = InfoViewModelFactory(marsProperty, application)
        binding.infoModel = ViewModelProvider(
                this, viewModelFactory).get(InfoViewModel::class.java)
        return binding.root
    }
}