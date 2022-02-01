package com.aait.data.utils

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object MultiPartUtil {

    fun String.toMultiPart(): RequestBody {
        return RequestBody.create(
            MediaType.parse("text/plain"),
            this
        )
    }

    fun prepareImagePart(
        partName: String,
        path: String
    ): MultipartBody.Part {
        val file = File(path)
        val requestFile = RequestBody.create(
            MediaType.parse("image/*"),
            file
        )
        return MultipartBody.Part.createFormData(partName, partName, requestFile)
    }

    fun preparePDFPart(
        partName: String,
        path: String
    ): MultipartBody.Part {
        val file = File(path)
        val requestFile = RequestBody.create(
            MediaType.parse("pdf/*"),
            file
        )
        return MultipartBody.Part.createFormData(partName, partName, requestFile)
    }

    fun prepareAudioPart(
        partName: String,
        path: String
    ): MultipartBody.Part {
        val file = File(path)
        val requestFile = RequestBody.create(
            MediaType.parse("audio/*"),
            file
        )
        return MultipartBody.Part.createFormData(partName, partName, requestFile)
    }


    fun prepareVideoPart(
        partName: String,
        path: String
    ): MultipartBody.Part {
        val file = File(path)
        val requestFile = RequestBody.create(
            MediaType.parse("video/*"),
            file
        )
        return MultipartBody.Part.createFormData(partName, partName, requestFile)
    }
}