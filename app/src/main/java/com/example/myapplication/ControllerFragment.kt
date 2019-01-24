package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.Fragment
import io.github.controlwear.virtual.joystick.android.JoystickView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import kotlin.math.cos
import kotlin.math.sin


// TODO: Rename parameter arguments, choose names that match




class ControllerFragment : Fragment() {


    val bluetooth = App.bluetoothHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_bluetooth_controller, container, false)

    }

    companion object {
        //max 2.55
        const val constSpeed= 2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val joystick = view.findViewById(R.id.joystick) as JoystickView
        val joystick2 = view.findViewById(R.id.joystick2) as JoystickView
        val distance = view.findViewById(R.id.distance) as TextView
        var arrow = view.findViewById(R.id.arrow) as View

        joystick.setOnMoveListener { angle, strength ->
            val doubleAngle = Math.toRadians(angle.toDouble())
            val x = cos(doubleAngle)
            val y = sin(doubleAngle)
            val speed = (y* constSpeed*strength).toInt()
//            Log.d("Angles",""+angle+" "+doubleAngle+" "+y)
            bluetooth.send("l:"+speed)
            bluetooth.send("r:"+speed)
        }
    joystick2.setOnMoveListener { angle, strength ->
        val doubleAngle = Math.toRadians(angle.toDouble())
        val x = cos(doubleAngle)
//        val y = sin(doubleAngle)
        val speed = x*constSpeed*strength
        val nspeed = -x*constSpeed*strength

        bluetooth.send("l:"+speed)
        bluetooth.send("r:"+nspeed)

    }


        val displayMetrics = context!!.resources!!.displayMetrics
        val maxWidth = displayMetrics!!.widthPixels


        bluetooth.lifecycleStream
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (it == BluetoothHelper.Companion.LifeCycle.CLOSED) activity?.finish()

            }
            .subscribe()

          bluetooth.recievedMessage
            .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .doOnNext {
                 try {
                 val z = it.split(":")
                     if(z.size==2){
                         if(z[0] == "d"){
                             val d =z[1].toInt()


                             val unfixed = maxWidth*d/200
                             val fixed= if(unfixed>maxWidth) maxWidth else unfixed

                             arrow.layoutParams = Constraints.LayoutParams(fixed, 50)
                             distance.text=z[1]


                         }
                     }
                 }catch (e: Exception){}

             }
            .subscribe()
        super.onViewCreated(view, savedInstanceState)

    }
var disposable : Disposable?=null
var lifecycle : Disposable?=null
    override fun onDestroy() {
        lifecycle?.dispose()
        disposable?.dispose()
        super.onDestroy()
    }
}
