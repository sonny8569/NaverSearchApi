package com.partron.naverbookapiproject.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.partron.naverbookapiproject.Https.Data.Book
import com.partron.naverbookapiproject.databinding.AdapterBookBinding

class BookAdapter (private val context : Context) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    var data = ArrayList<Book>()

    inner class  ViewHolder( val binding : AdapterBookBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterBookBinding.inflate(LayoutInflater.from(parent.context) , parent ,false )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtTitle.text = data[position].title
        Glide.with(context)
            .load(data[position].image)
            .into(holder.binding.imgBook)
    }

    override fun getItemCount(): Int {
       return data.size
    }

}