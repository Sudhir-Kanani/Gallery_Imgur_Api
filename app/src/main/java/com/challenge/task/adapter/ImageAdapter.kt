package com.challenge.task.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.task.Data
import com.challenge.task.databinding.ItemImageLayoutBinding


class ImageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // The list of images displayed by the adapter
    private var imageList: ArrayList<Data> = ArrayList()

    // Function to update the imageList and notify data set changed
    @SuppressLint("NotifyDataSetChanged")
    fun filterList(searchList: ArrayList<Data>) {
        imageList = searchList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Inflate the item view layout using ViewBinding
        val binding = ItemImageLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(imageList[position])      // Binds the data to the ViewHolder at the specified position

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ViewHolder(private val binding: ItemImageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Binds the data to the item view using ViewBinding
        fun bind(bankModel: Data) {
            binding.itemModel = bankModel
        }

    }
}
