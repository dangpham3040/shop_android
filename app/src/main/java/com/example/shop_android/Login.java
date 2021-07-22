package com.example.shop_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    //khai bao
    String Email = "";
    String Pass = "";
    EditText email, pass;
    TextView link_register;
    Button btnlogin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


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

    }

    private void login() {
        bien();
        fAuth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(getApplicationContext(), home.class));
                progressBar.setVisibility(View.VISIBLE);

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

        email = findViewById(R.id.lemail);
        pass = findViewById(R.id.lpass);
        progressBar = findViewById(R.id.progressBar);
        btnlogin = findViewById(R.id.btnlogin);
        link_register = findViewById(R.id.link_register);
        fAuth = FirebaseAuth.getInstance();
    }


}