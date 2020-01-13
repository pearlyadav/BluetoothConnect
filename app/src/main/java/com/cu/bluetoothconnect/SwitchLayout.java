package com.cu.bluetoothconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SwitchLayout extends AppCompatActivity {

    CheckBox[] mCheck = new CheckBox[64];
    String ss;
    StringBuilder x = new StringBuilder();
    StringBuilder data = new StringBuilder();
    Button mSend,mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_layout);

        for (int i = 0; i < mCheck.length; i++) {
            String buttonID = "mCheck" + (i + 1);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            mCheck[i] = findViewById(resID);
            Saved.s.append(mCheck[i].getId(), (i + 1));
        }

        mSend = findViewById(R.id.mButton);
        mSave = findViewById(R.id.mSave);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( bluetoothAccessories.btSocket==null ){
                    Toast.makeText(SwitchLayout.this, "Device not connected.Please Ensure that the device is connected.", Toast.LENGTH_SHORT).show();
                    return;
                }

                data.setLength(0);
                x.setLength(0);

                for (CheckBox checkBox : mCheck) {

                    if (checkBox.isChecked()) {

                        Saved.keys.add(checkBox.getId());

                    }
                }

                for (CheckBox checkBox : mCheck) {

                    if (checkBox.isChecked()) {

//                        send data to arduino here...

                        x.append(Saved.s.get(checkBox.getId()));
                        x.append(",");
                        if (Saved.s.get(checkBox.getId()) == 0)
                            ss = "Error!";
                        else {
                            ss = Saved.s.get(checkBox.getId()) + " Checked!!!";
                            data.append(Saved.s.get(checkBox.getId()));
                            data.append(" ");
                        }

                    } else {

                        data.append( (Saved.s.get(checkBox.getId()) + 100) );
                        data.append(" ");

                    }
                }

                data.append(" -1 ");

                ss = data.substring(0,data.length()-1);
                data.setLength(0);
                data.append(ss);
//                Toast.makeText(SwitchLayout.this, data, Toast.LENGTH_LONG).show();
                led_on_off(data);

            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    x.substring(0,x.length()-1);
                    x.append(" Saved!!!");
                }catch (Exception e) {
                    x.append("Problem Saving the Configuration.");
                }
                Toast.makeText(SwitchLayout.this, x, Toast.LENGTH_SHORT).show();
                x.setLength(0);
            }
        });

    }

    private void led_on_off(StringBuilder i)
    {
        try
        {
//            Toast.makeText(this, "Data Sent: " + i, Toast.LENGTH_SHORT).show();
            if (bluetoothAccessories.btSocket!=null)
            {
                Toast.makeText(this, "Data Sent: " + i, Toast.LENGTH_SHORT).show();
                bluetoothAccessories.btSocket.getOutputStream().write(i.toString().getBytes());
            }

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

}
