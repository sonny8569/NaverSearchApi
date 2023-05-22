package com.partron.naverbookapiproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.partron.naverbookapiproject.Https.Repository
import com.partron.naverbookapiproject.Utill.Resource
import com.partron.naverbookapiproject.ViewModel.Factory.SearchViewModelFactory
import com.partron.naverbookapiproject.ViewModel.SearchViewModel
import com.partron.naverbookapiproject.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val Tag =  "MainActivity"
    val repository  = Repository()
    private var _viewModel : SearchViewModel ?= null
    private val viewModel get()= _viewModel!!
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        addListener()
    }

    private fun init(){
        val factory = SearchViewModelFactory(repository)
        _viewModel = ViewModelProvider(this , factory)[SearchViewModel :: class.java]
    }

    private fun addListener(){
        viewModel.bookSearchLiveData.observe(this , Observer {
            when(it.status){
                Resource.Status.LOADING ->{
                    Log.d(Tag , "Loading for Book Search ")
                }
                Resource.Status.SUCCESS ->{
                    Log.d(Tag , "Success to get Book API")
                }
                Resource.Status.FaIL ->{
                    Log.d(Tag,  "Fail to get BookApi")
                    Toast.makeText(this , it.message , Toast.LENGTH_SHORT).show()
                }
                Resource.Status.ERROR->{
                    Log.e(Tag,  "Error Book Api")
                    Toast.makeText(this , "네트워크를 확인해 주세요" , Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


}