package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {



    lateinit var devicelist: ListView


    val requestCode = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        devicelist = findViewById(R.id.listView)
        if(!BluetoothHelper.deviceHasBluetooth){
            Toast.makeText(this,"No bluetooth",Toast.LENGTH_LONG).show()
            return
        }
        BluetoothHelper.requestIfItsOff(this,requestCode)


        refresh.setOnClickListener {
            getPairedDevices()
        }

        getPairedDevices()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == requestCode && resultCode == Activity.RESULT_OK) {
            getPairedDevices()
    }

    }



    private fun getPairedDevices() {
        val pairedDevices = BluetoothHelper.getBondedDevices()
        val list = ArrayList<String>()

        if (pairedDevices.isNotEmpty()) {
            for (bt in pairedDevices) {
                list.add(bt.name + "\n" + bt.address) //Get the device's name and the address
            }
        } else {
            Toast.makeText(applicationContext, "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        devicelist.adapter = adapter

        devicelist.setOnItemClickListener { _, view, _, _ ->

            val info = (view as TextView).text.toString()
            val address = info.substring(info.length - 17)
            val myBluetooth = BluetoothHelper(address)

            window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            Toast.makeText(applicationContext, "Connecting", Toast.LENGTH_LONG).show()

            myBluetooth.connect()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    val msg = if(it) "Connected"
                    else "Connection failed"
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
                    if(!it)return@subscribe

                    App.bluetoothHelper = myBluetooth
                    val i = Intent(this, ControllerActivity::class.java)
                    startActivity(i)
                },{})

        }

    }


}






