package com.example.shop_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Login extends AppCompatActivity {

    //khai bao
    private String Email = "";
    private String Pass = "";
    private EditText email, pass;
    private TextView forgot;
    private TextView link_register;
    private Button btnlogin;
    private ProgressBar progressBar;
    //bien


    //firebase
    private FirebaseAuth fAuth;
    private FirebaseDatabase Database;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContol();
        setEnvet();

    }


    private void setEnvet() {

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bien();
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    email.setError("Enter Valid Email Address");
                    email.requestFocus();
                    return;
                }
            }
        });

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bien();
                if (Pass.isEmpty()) {
                    pass.setError("Pass is require!!");
                    pass.requestFocus();

                }
                if (Pass.length() < 6) {
                    pass.setError("email have 6 characters ");
                    pass.requestFocus();

                }
                if (kiemtra() == true) {
                    btnlogin.setEnabled(true);
                }
            }
        });
        pass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    login();
                    return true;
                }
                return false;
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });
        link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), registration.class));
            }
        });
        link_register.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                link_register.setText("hello");
                return false;
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Email.isEmpty()) {
                    fAuth.sendPasswordResetEmail(Email);
                    Toast.makeText(Login.this, "please check your email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "enter email to use", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void login() {
        bien();

        fAuth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(getApplicationContext(), starup.class));
                progressBar.setVisibility(View.VISIBLE);
                //lấy id của user hiện tại
                Database = FirebaseDatabase.getInstance();
                mDatabase = Database.getReference("User");
                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                mDatabase.child(currentuser).child("status").setValue("online");

            } else {
                Toast.makeText(getApplicationContext(),
                        "Please Check Your login Credentials",
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    private boolean kiemtra() {
        bien();
        if (Email.isEmpty()) {
            email.setError("email is require!!");
            email.requestFocus();
            return false;
        }
        if (Pass.isEmpty()) {
            pass.setError("Pass is require!!");
            pass.requestFocus();
            return false;
        }
        if (Pass.length() < 6) {
            pass.setError("email have 6 characters ");
            pass.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Enter Valid Email Address");
            email.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public void bien() {
        Email = email.getText().toString();
        Pass = pass.getText().toString();
    }

    private void setContol() {
        forgot = findViewById(R.id.forgot);
        email = findViewById(R.id.lemail);
        pass = findViewById(R.id.lpass);
        progressBar = findViewById(R.id.progressBar);
        btnlogin = findViewById(R.id.btnlogin);
        link_register = findViewById(R.id.link_register);
        fAuth = FirebaseAuth.getInstance();
    }


}