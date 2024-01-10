package com.challenge.task.api


//A generic class representing different states of a response
open class MyResponse<T>(val data : T?=null, val error: String?=null, val exception: kotlin.Exception?=null) {
    class Loading<T>: MyResponse<T>()
    class Success<T>(data: T?): MyResponse<T>(data = data)
    class Error<T>(error: String?): MyResponse<T>(error = error)
    class Exception<T>(exception: kotlin.Exception?): MyResponse<T>(exception = exception)
}