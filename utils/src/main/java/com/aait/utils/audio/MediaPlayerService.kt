package com.aait.utils.audio

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class MediaPlayerService : Service(), MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {


    private val binder = LocalBinder()
    private var mMediaPlayer: MediaPlayer? = null
    private var voiceLink: String? = null

    // Binder given to clients
    inner class LocalBinder : Binder() {
        fun getService(): MediaPlayerService = this@MediaPlayerService
    }


    // send data from fragment to service
    private val StateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.getStringExtra("action")

            when (action) {
                "PLAY" -> {
                    prepareMediaPlayer()
                }

                "RESUME" -> {
                    resumeAudio()
                }

                "PAUSE" -> {
                    pauseAudio()
                }

                "STOP" -> {
                    stopAudio()
                }
            }
        }
    }


    override fun onCreate() {
        super.onCreate()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(StateReceiver, IntentFilter("mediaPlayer"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        voiceLink = intent?.getStringExtra("link")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }


    override fun onDestroy() {
        super.onDestroy()
        stopAudio()
    }


    /***
     *  prepare media player with some functions
     */
    private fun prepareMediaPlayer() {
        mMediaPlayer = MediaPlayerUtil().initMediaPlayer()
        mMediaPlayer?.apply {
            setOnPreparedListener(this@MediaPlayerService)
            setOnCompletionListener(this@MediaPlayerService)
            setDataSource("https://ms18.sm3na.com/138/Sm3na_com_61391.mp3")
            prepareAsync() // prepare async to not block main thread
        }
    }

    // Called when MediaPlayer is ready */
    override fun onPrepared(player: MediaPlayer) {
        sendStatusToBroadCast("start")
        player.start()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        try {
            stopAudio()
        } catch (e: Exception) {

        }
        sendStatusToBroadCast("complete")
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        sendStatusToBroadCast("error")
        onCompletion(null)
        return false
    }

    //send data from service to receiver then to fragment or activity
    private fun sendStatusToBroadCast(status: String) {
        val sendLevel = Intent()
        sendLevel.action = "mediaPlayerStatus"
        sendLevel.putExtra("status", status)
        sendBroadcast(sendLevel)
    }


    fun pauseAudio() {
        try {
            mMediaPlayer?.pause()
        } catch (e: Exception) {
            mMediaPlayer?.release()
            onCompletion(null)
        }
    }

    fun resumeAudio() {
        try {
            mMediaPlayer?.apply {
                seekTo(mMediaPlayer?.currentPosition!!)
                start()
            }
        } catch (e: Exception) {
            mMediaPlayer?.release()
            onCompletion(null)
            prepareMediaPlayer()
        }
    }

    fun stopAudio() {
        mMediaPlayer?.apply {
            release()
            stop()
        }
    }

}