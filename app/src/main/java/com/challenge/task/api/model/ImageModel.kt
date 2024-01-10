package com.challenge.task.api.model

import com.challenge.task.Data

data class ImageModel(
    val `data`: ArrayList<Data>,
    val status: Int,
    val success: Boolean
)