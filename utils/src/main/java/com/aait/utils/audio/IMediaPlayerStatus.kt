package com.aait.utils.audio

interface IMediaPlayerStatus {

    fun onAudioStart()

    fun onAudioComplete()

    fun onAudioError()
}