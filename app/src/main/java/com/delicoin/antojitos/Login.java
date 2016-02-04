package com.delicoin.antojitos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.delicoin.antojitos.model.User;
import com.delicoin.antojitos.utilities.FirebaseUtil;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;

import org.json.JSONObject;

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
        Firebase.setAndroidContext(this);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        Log.i("FACEBOOK_DELI_COIN", "Facebook is available HERE !!!");
        info = (TextView)findViewById(R.id.info);

        Button btnWithoutLogin = (Button) findViewById(R.id.button);
        btnWithoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                Login.saveAuthentication(loginResult);
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

    /*Save Authentication of the iser*/
    public static void saveAuthentication(LoginResult loginResult)
    {
        // App code
        Log.e("onSuccess", "--------" + loginResult.getAccessToken());
        Log.e("Token", "--------" + loginResult.getAccessToken().getToken());
        Log.e("Permision", "--------" + loginResult.getRecentlyGrantedPermissions());
//        Profile profile = Profile.getCurrentProfile();
//        Log.e("ProfileDataNameF", "--" + profile.getFirstName());
//        Log.e("ProfileDataNameL", "--" + profile.getLastName());
//        Log.e("Image URI", "--" + profile.getLinkUri());

        Log.e("OnGraph", "------------------------");
        final User user = User.getInstance();
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        Log.e("GraphResponse", "-------------" + response.toString());
                        try {
                            user.init((String) object.get("id"),
                                    (String) object.get("birthday"),
                                    (String) object.get("gender"),
                                    (String) object.get("email"),
                                    (String) object.get("name"));

                            FirebaseUtil.saveLoginUserData(user);
                        }catch(Exception e)
                        {
                            Log.e(TAG,e.getMessage());
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,gender,birthday,email");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
