package com.challenge.task.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.challenge.task.api.ImageApi
import com.challenge.task.api.MyResponse
import com.challenge.task.api.model.ImageModel
import com.challenge.task.R
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

/**
 * Repository class responsible for handling image-related data operations.
 * @param context The application context.
 * @param imageApi The API interface for image-related operations.
 */
open class ImageRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageApi: ImageApi
) {
    // LiveData to observe the state of image data loading
    private val imageLiveData = MutableLiveData<MyResponse<ImageModel>>()

    // LiveData property exposing the quotesLiveData for external observation
    val imageData: LiveData<MyResponse<ImageModel>>
        get() = imageLiveData

    // fetch api data with query
    suspend fun getImage(query: String) {
        imageLiveData.postValue(MyResponse.Loading())   // set loading state first
        try {

            val result: Response<ImageModel> =
                imageApi.getImages(context.getString(R.string.clientId), query, "jpg | png")

            if (result.body() != null) {
                imageLiveData.postValue(MyResponse.Success(result.body()))
//                Log.e("MVVM_DATA : ", MyResponse.Success(result.body()).toString())
//                Log.e("MVVM_DATA : ", MyResponse.Success(result.body()).data.toString())

            } else {
                imageLiveData.postValue(MyResponse.Exception(NullPointerException()))
//                Log.e("MVVM_DATA : ", "null=> "+ result.message().toString())
//                Log.e("MVVM_DATA : ","null=> "+ result.code().toString())
            }
        } catch (e: Exception) {
            imageLiveData.postValue(MyResponse.Exception(e))
//            Log.e("MVVM_DATA : ", "Exception=> " +e.toString())
//            Log.e("MVVM_DATA : ","Exception=> " + e.message.toString())
        }

    }
}