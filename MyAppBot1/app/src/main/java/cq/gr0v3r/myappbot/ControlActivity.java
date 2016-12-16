package cq.gr0v3r.myappbot;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by HP-420 on 24/10/2016.
 */

public class ControlActivity extends AppCompatActivity {

    Button btnDis;
    SeekBar brightness;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;

    RelativeLayout layout_joystick;
    ImageView image_joystick, image_border;
    TextView lumn;

    ImageButton btAdelante,btAtras,btIsquierda,btDerecha,btStop;

    JoyStickClass js;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        //Link the buttons and textViews to respective views

        btnDis = (Button) findViewById(R.id.button4);
        btAdelante = (ImageButton)findViewById(R.id.imageButtonArriba);
        btAtras = (ImageButton)findViewById(R.id.imageButtonAbajo);
        btIsquierda = (ImageButton)findViewById(R.id.imageButtoIzquierda);
        btDerecha = (ImageButton)findViewById(R.id.imageButtonDerecha);
        btStop = (ImageButton)findViewById(R.id.imageButtonStop);

        brightness = (SeekBar) findViewById(R.id.customSeekBar);
        lumn = (TextView) findViewById(R.id.lumn);


        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);

        js = new JoyStickClass(getApplicationContext(), layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);

        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {

                js.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    int direction = js.get8Direction();

                    if(direction == JoyStickClass.STICK_UP) {
                        // Data output
                        mConnectedThread.write("1");
                    } else if(direction == JoyStickClass.STICK_UPRIGHT) {
                        // Data output
                    } else if(direction == JoyStickClass.STICK_RIGHT) {
                        // Data output
                    } else if(direction == JoyStickClass.STICK_DOWNRIGHT) {
                        // Data output
                    } else if(direction == JoyStickClass.STICK_DOWN) {
                        // Data output
                    } else if(direction == JoyStickClass.STICK_DOWNLEFT) {
                        // Data output
                    } else if(direction == JoyStickClass.STICK_LEFT) {
                        // Data output
                    } else if(direction == JoyStickClass.STICK_UPLEFT) {
                        // Data output
                    } else if(direction == JoyStickClass.STICK_NONE) {
                        // Data output
                        mConnectedThread.write("0");
                    }
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    // not press
                    mConnectedThread.write("0");
                    //stop();
                }
                return true;
            }
        });

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();


        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect();
            }
        });

    btAdelante.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            adelante();
        }
    });

    btAtras.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            // Data output
            atras();
        }
    });

    btIsquierda.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            // Data output
            izquierda();
        }
    });

    btDerecha.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            // Data output
            derecha();
        }
    });

    btStop.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            stop();
        }
    });
    }

    private void stop(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("stop".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void adelante(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("adelante".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void atras(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("atras".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void izquierda(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("izquierda".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void derecha(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("derecha".toString().getBytes());
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


    private void Disconnect() {
        if (btSocket != null) {
            try {
                btSocket.close();
            } catch (IOException e) {
                msg("Error");
            }
        }
        finish();

    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        //address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        address = intent.getStringExtra(DeviceListActivity.EXTRA_ADDRESS);

        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            msg("Socket creation failed");
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        //mConnectedThread.write("x");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(btAdapter==null) {
            msg("Device does not support bluetooth");
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }
}
