package com.example.enginerev

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.eltonvs.obd.command.engine.RPMCommand
import com.github.eltonvs.obd.connection.ObdDeviceConnection
import java.util.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnconnect = findViewById<Button>(R.id.connectButton)
// set on-click listener
        btnconnect.setOnClickListener {
            val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
            val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.getAdapter()
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            pairedDevices?.forEach { device ->
                // deviceName = device.name
                //val deviceHardwareAddress = device.address // MAC address
                val cThread = ConnectThread(device)
                cThread.run()

            }

        }
    }



    private inner class ConnectThread(device: BluetoothDevice) : Thread() {

        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            val uuid: UUID = UUID.fromString("47c909e6-17f0-48de-a19e-b50d59da19ae")

            device.createRfcommSocketToServiceRecord(uuid)
        }

        override fun run() {
            // Cancel discovery because it otherwise slows down the connection.
            //bluetoothAdapter?.cancelDiscovery()

            mmSocket?.let { socket ->
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                socket.connect()
                //yep
                // The connection attempt succeeded. Perform work associated with
                // the connection in a separate thread.
                manageMyConnectedSocket(socket)
            }
        }

        // Closes the client socket and causes the thread to finish.
        /*fun cancel() {
            try {
                mmSocket?.close()
            } catch (e: IOException) {
            }
        }*/
        fun manageMyConnectedSocket(socket: BluetoothSocket) {
            val inputStream = socket.inputStream
            val outputStream = socket.outputStream
            val obdConnection = ObdDeviceConnection(inputStream, outputStream)
            val run = true

            while(run){


                login(obdConnection).toString()
            }
        }
        fun login(obdConnection: ObdDeviceConnection){
            val tV2 = findViewById<TextView>(R.id.textView2)
            // Create a new coroutine on the UI thread
            GlobalScope.launch {
                var delayedResponse = obdConnection.run(RPMCommand(), delayTime = 500L)
                var stringresponse = delayedResponse.toString()
                tV2.text = stringresponse
            }
        }



    }
}