package com.aait.utils.audio

import android.media.AudioAttributes
import android.media.MediaPlayer
import javax.inject.Inject

class MediaPlayerUtil @Inject constructor() {

    fun initMediaPlayer(): MediaPlayer {
        return MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setScreenOnWhilePlaying(true)
        }
    }


}