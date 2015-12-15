package com.delicoin.antojitos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import delicoin2.com.delicoin.R;

public class Login extends AppCompatActivity {

   // private Button btnWithoutLogin;
    private TextView info;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    public static final String TAG = Login.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        Log.i("FACEBOOK_DELI_COIN", "Facebook is available HERE !!!");
        info = (TextView)findViewById(R.id.info);

//        btnWithoutLogin = (Button) findViewById(R.id.button);
//        btnWithoutLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
//                startActivityForResult(myIntent, 0);
//            }
//        });

        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                Log.i(TAG, "Logged in");
//                info.setText(
//                        "User ID: "
//                                + loginResult.getAccessToken().getUserId()
//                                + "\n" +
//                                "Auth Token: "
//                                + loginResult.getAccessToken().getToken()
//                );
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {

                info.setText("Login attempt failed.");

            }
        });

       /* SVG svg;
        try {
            svg = new SVGBuilder().readFromAsset(getAssets(), "location.svg").build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable icono = svg.getDrawable();
        ImageView imageView = (ImageView) findViewById(R.id.imagen);
        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        imageView.setImageDrawable(icono);*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
