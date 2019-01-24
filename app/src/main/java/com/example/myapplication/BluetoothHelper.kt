package com.example.myapplication

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.util.Log
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import java.io.*
import java.util.*

class BluetoothHelper (address:String) {


    val lifecycleStream: PublishSubject<LifeCycle> = PublishSubject.create()

     val recievedMessage: PublishSubject<String> = PublishSubject.create()


    val bluetoothSocket: BluetoothSocket
    val bluetoothOutputStream: OutputStream
    val inputStream: InputStream

    var connecting = false
    val connected: Boolean
        get() {
            return bluetoothSocket.isConnected
        }


    init {
        val bluetooth = BluetoothAdapter.getDefaultAdapter()
        val device = bluetooth.getRemoteDevice(address)
        bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(sppUUID)
        bluetoothOutputStream = bluetoothSocket.outputStream
        inputStream = bluetoothSocket.inputStream


    }

     fun send(string: String) {
        if (bluetoothSocket.isConnected) try {
            Log.d("sending",string)
            bluetoothOutputStream.write(("$string\n").toByteArray())
        } catch (e: IOException) {
            disconect()
        }
    }




    fun connect(): Single<Boolean> {
        return Single.fromCallable<Boolean> {
            try {
                bluetoothSocket.connect()
                lifecycleStream.onNext(LifeCycle.OPENED)

                Thread{
                    var br :BufferedReader?=null
                    try {
                        br = BufferedReader(InputStreamReader(inputStream))
                        while (connected) {
                            var line: String = br.readLine()
                            recievedMessage.onNext(line)
                        }

                    }catch (e:IOException){}
                    finally {
                        try {
                            br?.close()
                        }catch (e:IOException){}
                    }



                }.start()
                return@fromCallable true

            } catch (e: IOException) {
                lifecycleStream.onNext(LifeCycle.ERROR)
                lifecycleStream.onNext(LifeCycle.CLOSED)
                return@fromCallable false
            }
        }


    }


    fun disconect(): Completable {
        return Completable.fromCallable {
            try {
                bluetoothSocket.close()
            } catch (e: IOException) {
            }
            try {
                inputStream.close()
            } catch (e: IOException) {}
            try {
                bluetoothOutputStream.close()
            } catch (e: IOException) {}



            lifecycleStream.onNext(LifeCycle.CLOSED)
        }
    }




    companion object {
        private val sppUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

        enum class LifeCycle {
            OPENED, CLOSED, ERROR
        }

        fun getBondedDevices(): MutableSet<BluetoothDevice> {
            return BluetoothAdapter.getDefaultAdapter().bondedDevices
        }

        val deviceHasBluetooth: Boolean by lazy {
            BluetoothAdapter.getDefaultAdapter() != null
        }

        fun requestIfItsOff(activity: Activity, requestCode: Int) {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled) {
                val turnBTon = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                activity.startActivityForResult(turnBTon, requestCode)
            }
        }


    }
}