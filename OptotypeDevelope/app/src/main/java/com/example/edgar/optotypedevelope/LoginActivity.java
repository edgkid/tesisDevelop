package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ConfgDialog.ConfgDialogListener {

    EditText    editTextUserName;
    EditText    editTextPaswword;
    Button      buttonLogin;
    Button      buttonConfg;
    ImageView   imageViewIcon;
    Context     contextActivity;
    ConfgConnect confgConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences conexionPreferences = this.getSharedPreferences("connectPreferences",Context.MODE_PRIVATE);
        String ipWebService = conexionPreferences.getString("IpWebService", "192.168.1.2");
        String ipShowClient = conexionPreferences.getString("IpShowTest", "192.168.1.2");
        String portConnection = conexionPreferences.getString("PortConecction", "5000");

        contextActivity = this;
        confgConnect = new ConfgConnect(ipWebService, ipShowClient, portConnection);

        editTextUserName = (EditText) findViewById(R.id.editTextUserNameLogin);
        editTextPaswword = (EditText) findViewById(R.id.editTextViewPassWord);
        imageViewIcon   = (ImageView) findViewById(R.id.imageViewIconEye);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener((View.OnClickListener) contextActivity);

        buttonConfg = (Button) findViewById(R.id.buttonConectionConfg);
        buttonConfg.setOnClickListener((View.OnClickListener) contextActivity);


        verifyPreferencesLogin();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonLogin:
                actionLogin();
                break;
            case R.id.buttonConectionConfg:
                actionConfg();
                break;
        }
    }

    public void actionConfg(){

        ConfgDialog confgDialog = new ConfgDialog();
        confgDialog.show(getSupportFragmentManager(),"Message Dialog");

    }

    public void actionLogin(){

        String resourceUser = "users/"+editTextUserName.getText().toString() + "," + editTextPaswword.getText().toString();
        RequestUser requestUser = new RequestUser(resourceUser, this);

        if (requestUser.findUserOnSystem()){
            initializeApp();
            callNewActivity();
        }else{
            Toast.makeText(this, "Problemas de conexion, imposible ingresar", Toast.LENGTH_SHORT).show();
        }



    }

    /**
     *This method verify if sharedpreferences login file contend the data
     */
    public void verifyPreferencesLogin (){

        String user = "";
        String password = "";

        SharedPreferences loginPreferences = this.getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);

        user =  loginPreferences.getString("user", "defaultUser");
        password = loginPreferences.getString("password", "defaultUser");

        if (!user.equals("defaultUser") && !password.equals("defaultUser")){
            callNewActivity();
        }
    }

    /**
     * This metohd call a new window or activity
     */
    public void callNewActivity (){
        Intent dashBoardActivity = new Intent(this, DashBoardActivity.class);
        startActivity(dashBoardActivity);
    }

    /**
     * This metohd initialize database App
     */
    public void initializeApp (){
        InitializeApp newTables = new InitializeApp(contextActivity);

        newTables.findOrCreteTablePatients();
        newTables.findOrCreateTableOptotypes();
        newTables.findOrCreateTableInteraction();
        newTables.findOrCreateTableAvResultToDay();
        newTables.findOrCreateTableSignalDefect();
        newTables.findOrCreateTableAntecedentDefect();
    }

    @Override
    public void applyData(String ipWebService, String ipShowClient, String portShowClient) {

        confgConnect.setIpWebService(ipWebService);
        confgConnect.setIpShowTest(ipShowClient);
        confgConnect.setPortConecction(portShowClient);

        SharedPreferences conexionPreferences = this.getSharedPreferences("connectPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = conexionPreferences.edit();
        editor.putString("IpWebService", ipWebService);
        editor.putString("IpShowTest", ipShowClient);
        editor.putString("PortConecction", portShowClient);

        editor.commit();


    }
}
