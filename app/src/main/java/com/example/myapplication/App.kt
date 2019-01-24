package com.example.myapplication

import android.app.Application

class App : Application() {

    companion object {
        lateinit var bluetoothHelper: BluetoothHelper
    }

    override fun onCreate() {

        super.onCreate()
    }
}