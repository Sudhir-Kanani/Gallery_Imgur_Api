package com.challenge.task.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.task.api.MyResponse
import com.challenge.task.api.model.ImageModel
import com.challenge.task.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class responsible for handling and preparing data for the UI related to images.
 * @param imageRepository The repository responsible for image data operations.
 */
@HiltViewModel
class ImageViewModel @Inject constructor(private val imageRepository: ImageRepository) :
    ViewModel() {
    /**
     * LiveData property exposing the state of image data loading.
     * Observers can observe changes in the imageList to update the UI accordingly.
     */
    val imageList: LiveData<MyResponse<ImageModel>>
        get() = imageRepository.imageData

    /**
     * Invoked when the text in the search field is changed.
     * Initiates a background coroutine to fetch image data based on the new text.
     * @param newText The updated text in the search field.
     */
    var job: Job? = null
    fun onTextChanged(newText: String) {
        job?.cancel()
        job =   viewModelScope.launch(Dispatchers.IO) {
            imageRepository.getImage(newText)
        }

    }
}
