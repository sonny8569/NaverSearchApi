package com.partron.naverbookapiproject.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.partron.naverbookapiproject.BuildConfig
import com.partron.naverbookapiproject.R
import com.partron.naverbookapiproject.utill.OnItemClickListener
import com.partron.naverbookapiproject.search.viewModel.SearchViewModel
import com.partron.naverbookapiproject.databinding.FragmentSearchBinding
import com.partron.naverbookapiproject.search.adapter.BookAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * 책 검색 fragment
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val TAG =  "SearchFragment"
    private lateinit var binding : FragmentSearchBinding
    private lateinit var adapter : BookAdapter
    private val viewModel : SearchViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater , container , false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        addListener()
        observer()
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
        adapter.setOnClickListener(object: OnItemClickListener{
            override fun onClickListener(data: String) {
                if(data == ""){
                    Log.e(TAG , "ERROR the Link is Null")
                    return
                }
                Log.d(TAG,  "The user Select book link")
                goToBookPage(data)
            }
        })
    }
    private fun observer(){
        viewModel.bookSearchLiveData.observe(viewLifecycleOwner , Observer {
            when(it){
                is SearchViewModel.SearchViewResult.Loading ->{
                    Log.d(TAG , it.message)
                }
                is SearchViewModel.SearchViewResult.BookResponse->{
                    Log.d(TAG , "Success to get Book")
                    val result = it.data
                    adapter.data = result
                    binding.recyclerviewBook.adapter = adapter
                }
                is SearchViewModel.SearchViewResult.ERROR->{
                    Log.e(TAG , "Error to get Book")
                    Toast.makeText(requireContext() , getString(R.string.msg_check_network) , Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
    private fun goToBookPage(url : String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "The Search View is Destroy")
    }
}