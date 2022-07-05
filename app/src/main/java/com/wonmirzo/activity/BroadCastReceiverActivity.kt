package com.wonmirzo.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wonmirzo.R
import com.wonmirzo.receivers.NetworkBroadcastReceiver

class BroadCastReceiverActivity : AppCompatActivity() {
    private lateinit var receiver: NetworkBroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broad_cast_receiver)
        initViews()
    }

    private fun initViews() {
        receiver = NetworkBroadcastReceiver()
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}