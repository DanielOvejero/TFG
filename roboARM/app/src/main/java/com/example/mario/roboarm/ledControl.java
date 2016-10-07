package com.example.mario.roboarm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import java.io.IOException;
import java.util.UUID;


public class ledControl extends ActionBarActivity {

    Button btn10, btn11, btn20, btn21, btn30, btn31, btn40, btn41, btn50, btn51; // cada uno hace referencia a su motor y a si suma (1) o resta (0)
    //TextView lumn;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //recibimos la mac address obtenida en la actividad anterior

        setContentView(R.layout.activity_led_control);

        btn10 = (Button)findViewById(R.id.button10);
        btn11 = (Button)findViewById(R.id.button11);
        btn20 = (Button)findViewById(R.id.button20);
        btn21 = (Button)findViewById(R.id.button21);
        btn30 = (Button)findViewById(R.id.button30);
        btn31 = (Button)findViewById(R.id.button31);
        btn40 = (Button)findViewById(R.id.button40);
        btn41 = (Button)findViewById(R.id.button41);
        btn50 = (Button)findViewById(R.id.button50);
        btn51 = (Button)findViewById(R.id.button51);


        //lumn = (TextView)findViewById(R.id.lumn);

        new ConnectBT().execute(); //Call the class to connect


        btn10.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mover("10");
            }
        });
        btn11.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mover("11");
            }
        });
        btn20.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mover("20");
            }
        });
        btn21.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mover("21");
            }
        });
        btn30.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mover("30");
            }
        });
        btn31.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mover("31");
            }
        });
        btn40.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mover("40");
            }
        });
        btn41.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mover("41");
            }
        });
        btn50.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mover("50");
            }
        });
        btn51.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mover("51");
            }
        });


    }

    private void Disconnect()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.close();
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish();

    }

    private void mover(java.lang.String bp)
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write(bp.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }




    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//conectamos al dispositivo y chequeamos si esta disponible
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Conexión Fallida");
                finish();
            }
            else
            {
                msg("Conectado");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}