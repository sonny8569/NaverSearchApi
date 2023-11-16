package com.partron.naverbookapiproject.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.partron.naverbookapiproject.Https.Data.Book
import com.partron.naverbookapiproject.utill.OnItemClickListener
import com.partron.naverbookapiproject.databinding.AdapterBookBinding

class BookAdapter (private val context : Context) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    var data = ArrayList<Book>()
    private var onClickListener : OnItemClickListener ?= null
    inner class  ViewHolder( val binding : AdapterBookBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : Book){
            binding.argTitle = data.title
            binding.argLink = data.link
            Glide.with(context).load(data.image).into(binding.imgBook)

            binding.txtLink.setOnClickListener {
                onClickListener!!.onClickListener(data.link)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterBookBinding.inflate(LayoutInflater.from(parent.context) , parent ,false )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
       return data.size
    }

    //interface 정의
    fun setOnClickListener(clickListener : OnItemClickListener){
        this.onClickListener = clickListener
    }

}