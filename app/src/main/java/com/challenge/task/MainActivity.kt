package com.challenge.task

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.task.adapter.ImageAdapter
import com.challenge.task.api.MyResponse
import com.challenge.task.databinding.ActivityMainBinding
import com.challenge.task.viewModel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ImageViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Set up RecyclerView with GridLayoutManager and ImageAdapter
        val recyclerViewLayoutManager = GridLayoutManager(applicationContext, 2)
        binding.recyclerView.layoutManager = recyclerViewLayoutManager

        val imageAdapter = ImageAdapter()
        binding.recyclerView.adapter = imageAdapter

        // Request focus on the search text field
        binding.textSearch.requestFocus()

        binding.imgView.setOnClickListener {
            if (recyclerViewLayoutManager.spanCount == 1) {
                // Switch to grid view
                animateSpanCountChange(recyclerViewLayoutManager, imageAdapter, 2)
                binding.imgView.setImageResource(R.drawable.icon_list)
            } else {
                // Switch to list view

                animateSpanCountChange(recyclerViewLayoutManager, imageAdapter, 1)
                binding.imgView.setImageResource(R.drawable.icon_grid)
            }
        }

        // Observe changes in the imageList LiveData and update UI accordingly
        viewModel.imageList.observe(this) { myResponse ->
            when (myResponse) {
                is MyResponse.Loading -> {
                    // Show loading progress
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.txtError.visibility = View.GONE
                }

                is MyResponse.Success -> {
                    // Show image data on successful response
                    myResponse.data?.let {
                        imageAdapter.filterList(it.data)
                        binding.progressCircular.visibility = View.GONE
                        if (it.data.isEmpty()) {
                            binding.txtError.text = getString(R.string.image_error)
                            binding.txtError.visibility = View.VISIBLE
                        }

                    }
                }

                is MyResponse.Error -> {
                    // Show error message on unsuccessful response
                    binding.progressCircular.visibility = View.GONE

                    binding.txtError.text = myResponse.error.toString()
                    binding.txtError.visibility = View.VISIBLE

                }

                is MyResponse.Exception -> {
                    // Show exception message on exception

                    binding.progressCircular.visibility = View.GONE

                    binding.txtError.text = myResponse.exception.toString()
                    binding.txtError.visibility = View.VISIBLE
                }
            }

        }
    }
    /**
     * Animates the span count change in the GridLayoutManager and notifies the adapter.
     * @param layoutManager The GridLayoutManager instance.
     * @param adapter The ImageAdapter instance.
     * @param newSpanCount The new span count after the animation.
     */
    private fun animateSpanCountChange(layoutManager: GridLayoutManager, adapter: ImageAdapter, newSpanCount: Int) {
        val spanCountChangeAnimator = ValueAnimator.ofInt(layoutManager.spanCount, newSpanCount)
        spanCountChangeAnimator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            layoutManager.spanCount = animatedValue
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
        }

        spanCountChangeAnimator.duration = 100
        spanCountChangeAnimator.start()
    }
}