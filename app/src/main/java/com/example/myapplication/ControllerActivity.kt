package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class ControllerActivity : AppCompatActivity() {



val myBluetooth=App.bluetoothHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_controller)
        ControllerFragment::class.java.replace()
    }

    override fun onBackPressed() {
        myBluetooth.disconect().subscribe()
        finish()
    }

    private fun <T> Class<T>.replace() {
        val tag = this.simpleName
        val cachedFragment = supportFragmentManager.findFragmentByTag(tag)
        if (cachedFragment==null){
            val fragment = this.newInstance() as Fragment
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame, fragment, tag)
            transaction.commit()
        }
    }

    }





