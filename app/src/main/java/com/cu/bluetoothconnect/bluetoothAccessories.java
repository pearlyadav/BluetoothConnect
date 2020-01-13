package com.cu.bluetoothconnect;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

class bluetoothAccessories {

    Set<BluetoothDevice> pairedDevices;
    static final List<String> address = new ArrayList<>();
    BluetoothAdapter mBluetoothAdapter = null;
    static BluetoothSocket btSocket = null;
    final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

}
