package cq.gr0v3r.myappbot;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Handler bluetoothIn;

    final int handlerState = 0;        				 //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    public static String EXTRA_ADDRESS = "device_address";
    //private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;

    Button ButtonControl;
    Button ButtonZumo;
    Button ButtonLinea;
    Button ButtonLuz;
    Button ButtonLaberinto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Defino la nueva fuente cargandola desde el fichero .ttf
        //Typeface miPropiaTypeFace = Typeface.createFromAsset(getAssets(), "stocky.ttf");
        Typeface miPropiaTypeFace = Typeface.createFromAsset(getAssets(), "MixBrush.ttf");

        // Cargo en una variable tipo TextView el campo de la pantalla
        // identificado con el id poker.
        TextView TextViewTitulo = (TextView)findViewById(R.id.TextViewTitulo);
        ButtonControl = (Button)findViewById(R.id.BotonControl);
        ButtonZumo = (Button)findViewById(R.id.BotonZumo);
        ButtonLinea = (Button)findViewById(R.id.BotonLinea);
        //ButtonLuz = (Button)findViewById(R.id.BotonLuz);
        ButtonLaberinto = (Button)findViewById(R.id.BotonLaberinto);
        // Le aplico el nuevo tipo de letra
        TextViewTitulo.setTypeface(miPropiaTypeFace);
        ButtonControl.setTypeface(miPropiaTypeFace);
        ButtonZumo.setTypeface(miPropiaTypeFace);
        ButtonLinea.setTypeface(miPropiaTypeFace);
        //ButtonLuz.setTypeface(miPropiaTypeFace);
        ButtonLaberinto.setTypeface(miPropiaTypeFace);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                finish();
            }
        });

        FloatingActionButton fa = (FloatingActionButton)findViewById(R.id.fa);
        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent DeviceLista = new Intent(getApplicationContext(), DeviceListActivity.class);
                startActivity(DeviceLista);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ButtonControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Control = new Intent(getApplicationContext(), ControlActivity.class);
                Control.putExtra(EXTRA_ADDRESS, address);
                startActivity(Control);
                Toast.makeText(getBaseContext(), " "+address, Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), " Controles", Toast.LENGTH_LONG).show();
            }
        });

        ButtonZumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Control = new Intent(getApplicationContext(), Activity_zumo.class);
                Control.putExtra(EXTRA_ADDRESS, address);
                startActivity(Control);
                Toast.makeText(getBaseContext(), " "+address, Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), " ZUMO", Toast.LENGTH_LONG).show();

            }
        });

        ButtonLinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Control = new Intent(getApplicationContext(), Activity_Linea.class);
                Control.putExtra(EXTRA_ADDRESS, address);
                startActivity(Control);
                Toast.makeText(getBaseContext(), " "+address, Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), " LINEA", Toast.LENGTH_LONG).show();
            }
        });

       /* ButtonLuz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Control = new Intent(getApplicationContext(), Activity_Sigue_Luz.class);
                Control.putExtra(EXTRA_ADDRESS, address);
                startActivity(Control);
                Toast.makeText(getBaseContext(), " "+address, Toast.LENGTH_LONG).show();
            }
        });*/
        ButtonLaberinto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Control = new Intent(getApplicationContext(), Activity_laberinto.class);
                Control.putExtra(EXTRA_ADDRESS, address);
                startActivity(Control);
                Toast.makeText(getBaseContext(), " "+address, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent Control = new Intent(getApplicationContext(), ControlActivity.class);
            Control.putExtra(EXTRA_ADDRESS, address);
            startActivity(Control);
            Toast.makeText(getBaseContext(), " "+address, Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_gallery) {
            Intent Control = new Intent(getApplicationContext(), Activity_zumo.class);
            Control.putExtra(EXTRA_ADDRESS, address);
            startActivity(Control);
            Toast.makeText(getBaseContext(), " "+address, Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_slideshow) {
            Intent Control = new Intent(getApplicationContext(), Activity_Linea.class);
            Control.putExtra(EXTRA_ADDRESS, address);
            startActivity(Control);
            Toast.makeText(getBaseContext(), " "+address, Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_manage) {
            Intent Control = new Intent(getApplicationContext(), Activity_laberinto.class);
            Control.putExtra(EXTRA_ADDRESS, address);
            startActivity(Control);
            Toast.makeText(getBaseContext(), " "+address, Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_share) {
            Intent DeviceLista = new Intent(getApplicationContext(), DeviceListActivity.class);
            startActivity(DeviceLista);

        } else if (id == R.id.nav_send) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();
        //Get the MAC address from the DeviceListActivty via EXTRA
        //address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        address = intent.getStringExtra(DeviceListActivity.EXTRA_ADDRESS);
        //Toast.makeText(getBaseContext(), " "+address, Toast.LENGTH_LONG).show();
        //create device and set the MAC address
        //BluetoothDevice device = btAdapter.getRemoteDevice(address);
    }


}
