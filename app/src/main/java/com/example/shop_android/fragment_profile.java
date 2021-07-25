package com.example.shop_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView profileimg;
    EditText fist, last, email;
    RadioGroup radioGroup;
    RadioButton male, female;
    Button btnlogout;
    FirebaseDatabase Database;
    DatabaseReference mDatabase;
    FirebaseAuth fAuth;
    TextView save, cancel;
    String gioitinh;
    String Fist, Last, Email;
    String otherID;
    String currentuser;
    boolean isfist = false;
    boolean islast = false;
    boolean isemail = false;
    boolean issex = false;

    String nam = "Nam", nu = "Nữ";
    String fistname = "", lastname = "", emailuser = "";
    View view;

    public fragment_profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_profile.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_profile newInstance(String param1, String param2) {
        fragment_profile fragment = new fragment_profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private void setEnvet() {
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Login.class));
                fAuth.getInstance().signOut();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), starup.class));
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
            }
        });
        fist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (iscurrentuser() == true) {
                    fistname = fist.getText().toString();
                    if (fistname.isEmpty()) {
                        fist.setError("fistname is require!!");
                        isfist = false;
                        checUpdate();
                    }
                    if (Fist.equals(fistname)) {
                        isfist = false;
                        checUpdate();
                    } else if (!Fist.equals(fistname) && !fistname.isEmpty()) {
                        isfist = true;
                        checUpdate();
                    }
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
                if (iscurrentuser() == true) {
                    lastname = last.getText().toString();
                    if (lastname.isEmpty()) {
                        last.setError("lastname is require!!");
                        islast = false;
                        checUpdate();
                    }
                    if (Last.equals(lastname)) {
                        islast = false;
                        checUpdate();
                    } else if (!Last.equals(lastname) && !Last.isEmpty()) {
                        islast = true;
                        checUpdate();
                    }
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
                if (iscurrentuser() == true) {
                    emailuser = email.getText().toString();
                    if (emailuser.isEmpty()) {
                        isemail = false;
                        checUpdate();
                        email.setError("email is require!!");
                    }
                    if (Email.equals(emailuser)) {
                        isemail = false;
                        checUpdate();
                    }
                    if (!Email.equals(emailuser) && !Email.isEmpty()) {
                        isemail = true;
                        checUpdate();
                    }
                    if (!Patterns.EMAIL_ADDRESS.matcher(emailuser).matches()) {
                        email.setError("Enter Valid Email Address");
                        isemail = false;
                        checUpdate();
                    }
                }

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String choose_sex = "";
                if (male.isChecked()) {
                    choose_sex = nam;
                }
                if (female.isChecked()) {
                    choose_sex = nu;
                }
                if (!gioitinh.equals(choose_sex)) {
                    issex = true;
                    checUpdate();
                } else {
                    issex = false;
                    checUpdate();
                }
            }
        });


    }

    private void checUpdate() {
        if (!fistname.isEmpty() && !lastname.isEmpty() && !emailuser.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailuser).matches()) {
            if (isfist == true || islast == true || isemail == true || issex == true) {
                save.setEnabled(true);
                save.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                save.setTextColor(Color.parseColor("#5500ce"));
                save.setEnabled(false);
            }
        } else {
            save.setTextColor(Color.parseColor("#5500ce"));
            save.setEnabled(false);
        }


    }

    private void Update() {
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setTitle("Update?");
        b.setMessage("Are you sure ?");
        b.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mDatabase.child(otherID).child("fist").setValue(fist.getText().toString());
                mDatabase.child(otherID).child("last").setValue(last.getText().toString());
                mDatabase.child(otherID).child("email").setValue(email.getText().toString());
                if (male.isChecked()) {
                    mDatabase.child(otherID).child("sex").setValue(nam);
                }
                if (female.isChecked()) {
                    mDatabase.child(otherID).child("sex").setValue(nu);
                }
                startActivity(new Intent(getContext(), starup.class));
            }
        });
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //Tạo dialog
        AlertDialog al = b.create();
        //Hiển thị
        al.show();


    }


    private void setControl() {
        profileimg = view.findViewById(R.id.imageView);
        fist = view.findViewById(R.id.fist);
        last = view.findViewById(R.id.last);
        email = view.findViewById(R.id.email);
        male = view.findViewById(R.id.male);
        female = view.findViewById(R.id.female);
        cancel = view.findViewById(R.id.cancel);
        save = view.findViewById(R.id.save);
        radioGroup = view.findViewById(R.id.RadioGroup);
        btnlogout=view.findViewById(R.id.btnlogout);

//        Intent intent = getIntent();
        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        otherID =currentuser;
        Database = FirebaseDatabase.getInstance();
        mDatabase = Database.getReference("User");
        Query check = mDatabase.orderByChild("id").equalTo(otherID);

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.exists()) {
                        Fist = ds.child("fist").getValue(String.class);
                        fist.setText(Fist);
                        Last = ds.child("last").getValue(String.class);
                        last.setText(Last);
                        Email = ds.child("email").getValue(String.class);
                        email.setText(Email);
                        gioitinh = ds.child("sex").getValue(String.class);

                        if (gioitinh.equals(nam)) {
                            male.setChecked(true);
                        } else {
                            female.setChecked(true);
                        }
                        String img = ds.child("pic").getValue(String.class);
                        Picasso.get().load(img).into(profileimg);

                    } else {
                        Log.d("user", "khong ton tai");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (iscurrentuser() == true) {
            fist.setEnabled(true);
            last.setEnabled(true);
            email.setEnabled(true);
            female.setEnabled(true);
            male.setEnabled(true);
        }
    }

    private boolean iscurrentuser() {
        if (otherID.equals(currentuser)) {
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_profile,container,false);
        // Inflate the layout for this fragment
        setControl();
        setEnvet();
        return view;

    }
}