package com.aait.utils.audio

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import java.io.IOException
import javax.inject.Inject


class VoiceRecord @Inject constructor(val context: Context) {


    var mediaRecorderListener: ((MediaRecorder, String) -> Unit)? = null
    var calculateAmplitudeListener: ((Float) -> Unit)? = null

    private var mediaRecorder: MediaRecorder? = null
    private var ampli: Float = 1.0f


    fun startRecord() {
        mediaRecorder = MediaRecorder()
        val voicePath =
            context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath + "/recording.3gp"

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setAudioSamplingRate(16000)

        try {
            mediaRecorder?.setOutputFile(voicePath)
        } catch (e: java.lang.Exception) {
            Log.i("sdsdsdsdsd", "startRecord: " + e.toString())
        }
        try {
            mediaRecorder?.prepare()
        } catch (e: IOException) {
        }
        mediaRecorder?.start()

        // fire listener
        mediaRecorder?.let {
            mediaRecorderListener?.invoke(it, voicePath)
        }
    }

    fun stopRecording() {
        mediaRecorder?.stop()
        mediaRecorder?.reset()
        mediaRecorder?.release()
    }
}