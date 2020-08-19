package com.jntucep.c19_delhi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.annotations.NonNull;

public class Login extends AppCompatActivity {

    private int flag=0;
    private String email;
    private String password;
    private Intent intent, intent1;
    private FirebaseAuth mAuth;
    private CallbackManager callbackmanager;
    private GoogleSignInClient mGoogleSignInClient;
    private LoginButton facebookbutton;
    private SignInButton googlebutton;
    EditText Username;
    EditText Password;
    LinearLayout layout;
    boolean connected = false;
    ProgressDialog mprogress,googlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mprogress=new ProgressDialog(Login.this);
        mprogress.setMessage("Validating Credentials!!!\nPlease Wait!!");
        flag=0;

        googlebar=new ProgressDialog(Login.this);
        googlebar.setMessage("Validating Credential\nPlease Wait");

        //FacebookSdk.sdkInitialize(this.getApplicationContext());
        Login.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.logintoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LOG IN");

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(Login.this,gso);

        layout=(LinearLayout)findViewById(R.id.loginlayout);
        layout.setVisibility(View.VISIBLE);

        Username=findViewById(R.id.username);
        Password=findViewById(R.id.password);
        final Button Login=(Button)findViewById(R.id.login) ;
        TextView forgotpassword=(TextView)findViewById(R.id.forgotpassword);

        TextView signup = (TextView)findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();
        callbackmanager=CallbackManager.Factory.create();


//        if(mAuth.getCurrentUser() != null) {
//            if (!mAuth.getCurrentUser().isEmailVerified()) {
//                mAuth.getCurrentUser().sendEmailVerification();
//                Toast.makeText(getApplicationContext(), "Verify " + mAuth.getCurrentUser().getEmail() + " and then login", Toast.LENGTH_LONG).show();
//            }
//            else {
//                Intent intent = new Intent(Login.this, MainActivity.class);
//                // Toast.makeText(Login.this, mAuth.getUid(), Toast.LENGTH_SHORT).show();
//                DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid()).child("name");
//
//                reff.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Toast.makeText(Login.this, "" + dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                //intent.putExtra("CurrentUserId", mAuth.getUid() );
//                startActivity(intent);
//                //Toast.makeText(getApplicationContext(), "Login Successful!!", Toast.LENGTH_LONG).show();
//            }
//        }



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                }
                else
                    connected = false;

                if (connected == true) {
                    flag = 0;
                    email = Username.getText().toString();
                    password = Password.getText().toString();
                    if (TextUtils.isEmpty(email)) {
                        Username.setError("Enter Username");
                        flag = 1;
                    }
                    if (TextUtils.isEmpty(password)) {
                        Password.setError("Enter Password");
                        flag = 1;
                    }

                    if (flag == 0) {
                        mprogress.show();
                        setup();
                    }
                } else {
                    Toast.makeText(Login.this, "Check Internet Connectivity", Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(Login.this,ForgotPassword.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1=new Intent(Login.this, SignUp.class);
                startActivity(intent1);
            }
        });

    }



    private void setup()
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (!mAuth.getCurrentUser().isEmailVerified()) {
                        Toast.makeText(getApplicationContext(), "Verify " + mAuth.getCurrentUser().getEmail() + " and then login", Toast.LENGTH_LONG).show();
                        mprogress.dismiss();
                    } else {
                        Intent intent = new Intent(Login.this, MainActivityvolunteer.class);
                        // Toast.makeText(Login.this, mAuth.getUid(), Toast.LENGTH_SHORT).show();
                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name");

                        reff.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Toast.makeText(Login.this, "" + dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        //intent.putExtra("CurrentUserId", mAuth.getUid() );
                        startActivity(intent);
                        finish();

                        //                        progress.setVisibility(View.GONE);
                        //                          layout.setVisibility(View.VISIBLE);
                        //Toast.makeText(getApplicationContext(), "Login Successful!!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //                        layout.setVisibility(View.VISIBLE);
                    //                          progress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Error!!\nLogin Unsuccessful", Toast.LENGTH_LONG).show();
                    mprogress.dismiss();
                }
            }
        });
    }




}