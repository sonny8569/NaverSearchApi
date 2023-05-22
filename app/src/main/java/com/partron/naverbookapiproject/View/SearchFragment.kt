package com.partron.naverbookapiproject.View

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.partron.naverbookapiproject.BuildConfig
import com.partron.naverbookapiproject.MainActivity
import com.partron.naverbookapiproject.R
import com.partron.naverbookapiproject.Utill.Resource
import com.partron.naverbookapiproject.View.Adapter.BookAdapter
import com.partron.naverbookapiproject.ViewModel.Factory.SearchViewModelFactory
import com.partron.naverbookapiproject.ViewModel.SearchViewModel
import com.partron.naverbookapiproject.databinding.FragmentSearchBinding

/**
 * 책 검색 fragment
 */
class SearchFragment : Fragment() {
    private val TAG =  "SearchFragment"

    private var _binding : FragmentSearchBinding ? = null
    private val binding get() = _binding!!

    private var _viewModel : SearchViewModel ? = null
    private val viewModel get() = _viewModel!!

    private lateinit var adapter : BookAdapter

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater , container , false )
        initViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        addListener()
    }

    /**
     * viewModel init
     */
    private fun initViewModel(){
        val factory = SearchViewModelFactory((requireActivity() as MainActivity).repository)
        _viewModel = ViewModelProvider(this , factory)[SearchViewModel :: class.java]
    }

    private fun init(){
        val layoutManger : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewBook.layoutManager = layoutManger
        adapter = BookAdapter(requireContext())
    }

    private fun addListener(){
        binding.btnSearch.setOnClickListener {
            val data = binding.editSearchBook.text.toString().replace(" ","")
            if(data == ""){
                Toast.makeText(requireContext() , getString(R.string.msg_check_book) , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.requestBookApi(BuildConfig.CLIENT_ID , BuildConfig.CLIENT_PASSWORD , data)
        }


        viewModel.bookSearchLiveData.observe(viewLifecycleOwner , Observer {
            when(it.status){
                Resource.Status.LOADING ->{
                    Log.d(TAG , "Loading for search Book")
                }
                Resource.Status.SUCCESS ->{
                    Log.d(TAG , "Success to get Book")
                    val result = it.data!!
                    adapter.data = result
                    binding.recyclerviewBook.adapter = adapter
                }
                Resource.Status.ERROR ->{
                    Log.e(TAG , "Error to get Book")
                    Toast.makeText(requireContext() , getString(R.string.msg_check_network) , Toast.LENGTH_SHORT).show()
                }
                Resource.Status.FaIL ->{
                    Log.d(TAG , "Fail to get Book")
                    Toast.makeText(requireContext() , it.message , Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG , "The Search View is Destory")
        _binding = null
        _viewModel = null
    }
}