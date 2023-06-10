package com.partron.naverbookapiproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.partron.naverbookapiproject.Respository.Repository
import com.partron.naverbookapiproject.Utill.Resource
import com.partron.naverbookapiproject.ViewModel.Factory.SearchViewModelFactory
import com.partron.naverbookapiproject.ViewModel.SearchViewModel
import com.partron.naverbookapiproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val Tag =  "MainActivity"

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Hilt를 사용하여 의존성 주입을 수행할 수 있음
    }
}