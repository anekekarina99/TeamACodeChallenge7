package com.teamacodechallenge7.utils

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException

fun errorHandling(throwable: Throwable?): String {
    var msg = ""
    if (throwable is HttpException) {
        msg = throwable.response()?.errorBody().let {
            it?.let { it1 -> getErrorMessage(it1) }
        }.toString()

    }
    return msg
}

private fun getErrorMessage(response: ResponseBody): String? {
    return try {
        val jsonObject = JSONObject(response.string())
        jsonObject.getString("errors")
    } catch (e: Exception) {
        e.message
    }
}

