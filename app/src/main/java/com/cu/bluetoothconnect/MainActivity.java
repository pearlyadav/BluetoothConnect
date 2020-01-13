package com.cu.bluetoothconnect;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    Button mConfiguration,mPairedDevices,mViewSaved;
    TextView mConnectionState;
    LinearLayout mSwitchLayout;
    Switch mAllLed;
    String ConnectionState;
    bluetoothAccessories myBluetooth = new bluetoothAccessories();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConfiguration = findViewById(R.id.configuration);
        mPairedDevices = findViewById(R.id.pairedDevices);
        mConnectionState = findViewById(R.id.connectionState);
        mSwitchLayout = findViewById(R.id.switchLayout);
        mViewSaved = findViewById(R.id.mViewSaved);
        mAllLed = mSwitchLayout.findViewById(R.id.allLed);

        myBluetooth.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_list_view, R.id.listText);

        if ( myBluetooth.mBluetoothAdapter == null ) {
            Toast.makeText(getApplicationContext(), "Your Phone Doesn't have Bluetooth\nClosing the Application.", Toast.LENGTH_SHORT).show();
            finish();
        } else if ( !myBluetooth.mBluetoothAdapter.isEnabled() ){
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT,1);
        }

        myBluetooth.pairedDevices = myBluetooth.mBluetoothAdapter.getBondedDevices();

        if ( myBluetooth.pairedDevices.size() >0 ){

            for ( BluetoothDevice bt : myBluetooth.pairedDevices) {
                arrayAdapter.add(bt.getName());
                bluetoothAccessories.address.add(bt.getAddress());
            }

        }

        if(bluetoothAccessories.btSocket==null){
            ConnectionState = "Not Connected";

        } else {
            ConnectionState = "Connected.";
        }

        mConnectionState.setText(ConnectionState);

        mPairedDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Paired Devices :");

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String str = arrayAdapter.getItem(which);
                        String address = bluetoothAccessories.address.get(which);
                        str = str + "\n" + address;
                        BluetoothDevice device = myBluetooth.mBluetoothAdapter.getRemoteDevice(address);
                        try{
                            bluetoothAccessories.btSocket = device.createInsecureRfcommSocketToServiceRecord(myBluetooth.myUUID);
                            bluetoothAccessories.btSocket.connect();
                        } catch (IOException e){
                            str = "Connection Failed!!!";
                        }
                        AlertDialog.Builder inner = new AlertDialog.Builder(MainActivity.this);
                        inner.setTitle("Selected Item : ");
                        inner.setMessage(str);
                        inner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        inner.show();


                    }
                });
                builder.show();

            }
        });

        mConfiguration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SwitchLayout.class);
                startActivity(i);
            }
        });

        mViewSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, Grouping.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_CANCELED ) {
            Toast.makeText(getApplicationContext(), "Bluetooth must be Enabled\nClosing the application.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}

