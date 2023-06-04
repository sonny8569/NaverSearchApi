package com.partron.naverbookapiproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.partron.naverbookapiproject.Respository.Repository
import com.partron.naverbookapiproject.Utill.Resource
import com.partron.naverbookapiproject.ViewModel.Factory.SearchViewModelFactory
import com.partron.naverbookapiproject.ViewModel.SearchViewModel
import com.partron.naverbookapiproject.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val Tag =  "MainActivity"
//    val repository  = Repository()
    private var _viewModel : SearchViewModel ?= null
    private val viewModel get()= _viewModel!!
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }





}