package com.challenge.task.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.challenge.task.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Custom BindingAdapter for loading images into ImageView using Glide.
@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String) {
    Glide.with(this.context).load(url).placeholder(R.drawable.ic_placeholder).into(this)
}

// Custom BindingAdapter for displaying the local time in a specified format in a TextView.
@SuppressLint("SimpleDateFormat", "WeekBasedYear")
@BindingAdapter("localTime")
fun TextView.localTime(datetime : Long){
    /*we have a Unix timestamp (the number of seconds since January 1, 1970, UTC) rather than a timestamp in milliseconds.*/
    val date = Date(datetime * 1000)  // Multiply by 1000 to convert seconds to milliseconds
    val sdf3 = SimpleDateFormat("dd/MM/YY hh:mm a", Locale.getDefault());
    this.text =  sdf3.format(date)    // Format the provided timestamp and set the result to the TextView
}
