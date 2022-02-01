package com.aait.utils.audio

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class MediaPlayerBroadCast : BroadcastReceiver() {

    private lateinit var iMediaPlayerStatus: IMediaPlayerStatus

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!this::iMediaPlayerStatus.isInitialized)
            iMediaPlayerStatus = context as IMediaPlayerStatus

        when (intent?.getStringExtra("status")!!) {
            "error" -> {
                iMediaPlayerStatus.onAudioError()
            }

            "start" -> {
                iMediaPlayerStatus.onAudioStart()
            }

            "complete" -> {
                iMediaPlayerStatus.onAudioComplete()
            }
        }

    }


}