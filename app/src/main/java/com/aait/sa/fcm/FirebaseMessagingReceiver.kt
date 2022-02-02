package com.aait.sa.fcm

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.aait.data.remote.utils.NetworkConstants.Languages.ARABIC
import com.aait.domain.repository.PreferenceRepository
import com.aait.sa.R
import com.aait.sa.base.BaseApp.Companion.CHANNEL_ID
import com.aait.sa.home_cycle.HomeActivity
import com.aait.utils.common.fromJson
import com.aait.utils.common.onPrintLog
import com.aait.utils.common.toJson
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import kotlin.random.Random


@AndroidEntryPoint
class FirebaseMessagingReceiver : FirebaseMessagingService() {

    private lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (!remoteMessage.data.isNullOrEmpty()) {
            remoteMessage.data.onPrintLog()
            EventBus.getDefault().post(remoteMessage.data)
            displayNotification(remoteMessage.data)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            preferenceRepository.setFirebaseToken(token)
        }
    }

    private fun displayNotification(data: MutableMap<String, String>) {
        var title: String?
        var message: String?

        val notificationItem = data.toJson().fromJson<NotificationItem>()

        CoroutineScope(Dispatchers.IO).launch {
            if (preferenceRepository.getLanguage().first() == ARABIC) {
                title = notificationItem.titleAr ?: ""
                message = notificationItem.messageAr ?: ""
            } else {
                title = notificationItem.titleEn ?: ""
                message = notificationItem.messageEn ?: ""
            }

            showNotification(
                title = title,
                message = message,
                pendingIntent = getPendingIntent(notificationItem)
            )
        }
    }

    private fun getPendingIntent(notificationItem: NotificationItem): PendingIntent {
        return when (notificationItem.type) {

            else -> navigateToHomeIntent()
        }
    }

    private fun navigateToHomeIntent(): PendingIntent {
        return NavDeepLinkBuilder(applicationContext)
            .setGraph(R.navigation.home_graph)
            .setComponentName(HomeActivity::class.java)
            .setDestination(R.id.homeFragment)
            .createPendingIntent()
    }


    private fun showNotification(title: String?, message: String?, pendingIntent: PendingIntent) {
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setDefaults(Notification.DEFAULT_LIGHTS)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)

        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(Random(10000).nextInt(), builder.build())
    }
}