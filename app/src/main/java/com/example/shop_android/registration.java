package com.example.shop_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;


public class registration extends AppCompatActivity {
    // khai bao
    EditText fist, last, email, pass, cpass;
    Button btnregister;
    TextView link_login;
    ProgressBar progressBar;
    Spinner sex;

    String fistname = "";
    String lastname = "";
    String Email = "";
    String Pass = "";
    String Cpass = "";
    String anh = "";
    String gioitinh = "Nam";
    String id;
    User user;
    private static final int PICK_IMAGE_REQUIRE = 1;
    private URI imageUri;
    FirebaseAuth fAuth;
    FirebaseDatabase Database;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setControl();
        setEnvet();
    }

    private void setEnvet() {
        bien();
        fist.addTextChangedListener(new TextWatcher() {
            @Override

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bien();
                if (fistname.isEmpty()) {
                    fist.setError("fistname is require!!");
                    fist.requestFocus();
                    return;
                }
            }
        });
        last.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bien();
                if (lastname.isEmpty()) {
                    last.setError("lastname is require!!");
                    last.requestFocus();
                    return;
                }
            }
        });
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
            }
        });
        cpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bien();
                if (Pass.equals(Cpass) == false) {
                    cpass.setError("password and confirm password not match");
                    cpass.requestFocus();

                } else if (kiemtra() == true) {
                    btnregister.setEnabled(true);
                }
            }
        });
        cpass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    bien();
                    progressBar.setVisibility(View.VISIBLE);
                    register(Email, Pass);
                    return true;
                }
                return false;
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bien();
                progressBar.setVisibility(View.VISIBLE);
                register(Email, Pass);

            }
        });

        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }


    private boolean kiemtra() {
        bien();
        if (fistname.isEmpty()) {
            fist.setError("fistname is require!!");
            fist.requestFocus();
            return false;
        }
        if (lastname.isEmpty()) {
            last.setError("lastname is require!!");
            last.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Enter Valid Email Address");
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
        if (Pass.equals(Cpass) == false) {
            cpass.setError("password and confirm password not match");
            cpass.requestFocus();
            return false;
        } else {
            return true;
        }
    }


    private void register(String Email, String Pass) {
        fAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                    Toast.makeText(registration.this, "ok", Toast.LENGTH_SHORT).show();
                    UpdateUI();


                } else {
                    Toast.makeText(registration.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UpdateUI() {
        if (sex.getSelectedItemPosition() == 1) {
            gioitinh = "Nữ";
        }
        if (sex.getSelectedItemPosition() == 0) {
            gioitinh = "Nam";
        }

        Database = FirebaseDatabase.getInstance();
        mDatabase = Database.getReference("User");

        String keyid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        user = new User(keyid,fistname,lastname,Email,gioitinh,anh);
        mDatabase.child(keyid).setValue(user);

        Intent intent = new Intent(getApplicationContext(), starup.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void bien() {
        fistname = fist.getText().toString();
        lastname = last.getText().toString();
        Email = email.getText().toString();
        Pass = pass.getText().toString();
        Cpass = cpass.getText().toString();
        anh = "https://firebasestorage.googleapis.com/v0/b/android-shop-ae9d2.appspot.com/o/Image%2Fuser.jpg?alt=media&token=710dd3aa-8bb1-4048-bb0f-13320ad94825";

    }

    private void setControl() {
        fist = findViewById(R.id.fist);
        last = findViewById(R.id.last);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        cpass = findViewById(R.id.cpass);
        sex = findViewById(R.id.spsex);
        link_login=findViewById(R.id.link_login);
        btnregister = findViewById(R.id.btnregister);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
    }
}