/*
package com.example.enginerev

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import androidx.core.content.ContextCompat.getSystemService
import java.util.*
import kotlin.reflect.KProperty

public class manageBluetooth{
    val bluetoothAdapter: BluetoothAdapter? = null
    val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
    private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
        val uuid: UUID = UUID.fromString("47c909e6-17f0-48de-a19e-b50d59da19ae")
        pairedDevices?.first { device ->
            device.createInsecureRfcommSocketToServiceRecord(uuid)
        }
        mmSocket?.let { socket ->
            socket.connect()
        }
    }

}

private operator fun Any.getValue(manageBluetooth: manageBluetooth, : KProperty<*>): BluetoothSocket? {

}
*/
