package com.wonmirzo.activity

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import com.wonmirzo.R
import com.wonmirzo.services.BoundService
import com.wonmirzo.services.StartedService

class MainActivity : AppCompatActivity() {
    private var boundService: BoundService? = null
    private var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        val btnStartStartedService: Button = findViewById(R.id.btnStartStartedService)
        val btnStopStartedService: Button = findViewById(R.id.btnStopStartedService)
        val btnPrintTimestamp: Button = findViewById(R.id.btnPrintTimestamp)
        val btnStartBoundService: Button = findViewById(R.id.btnStartBoundService)
        val btnStopBoundService: Button = findViewById(R.id.btnStopBoundService)
        val tvTimeStamp: TextView = findViewById(R.id.tvTimeStamp)

        val btnStartBroadCastActivity: Button = findViewById(R.id.btnStartBroadCastActivity)
        btnStartBroadCastActivity.setOnClickListener { openBroadcastActivity() }

        btnStartStartedService.setOnClickListener {
            startStartedService()
        }
        btnStopStartedService.setOnClickListener {
            stopStartedService()
        }

        btnStartBoundService.setOnClickListener {
            startBoundService()
        }

        btnStopBoundService.setOnClickListener {
            stopBoundService()
        }

        btnPrintTimestamp.setOnClickListener {
            if (isBound) {
                tvTimeStamp.text = boundService!!.timestamp
            }
        }
    }

    private fun openBroadcastActivity() {
        startActivity(Intent(this@MainActivity, BroadCastReceiverActivity::class.java))
    }

    private fun startStartedService() {
        val intent = Intent(this, StartedService::class.java)
        startService(intent)
    }

    private fun stopStartedService() {
        val intent = Intent(this, StartedService::class.java)
        stopService(intent)
    }

    private fun startBoundService() {
        val intent = Intent(this, BoundService::class.java)
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
    }

    private fun stopBoundService() {
        if (isBound) {
            unbindService(mServiceConnection)
            isBound = false
        }
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder: BoundService.MyBinder = service as BoundService.MyBinder
            boundService = myBinder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }
    }
}