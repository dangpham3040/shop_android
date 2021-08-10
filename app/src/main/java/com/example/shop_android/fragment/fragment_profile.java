package com.example.shop_android.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
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


import com.example.shop_android.R;

import com.example.shop_android.data.StaticConfig;
import com.example.shop_android.ui.Login;
import com.example.shop_android.ui.starup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;
import java.util.zip.Inflater;

import static androidx.media.MediaBrowserServiceCompat.RESULT_OK;


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
    //khai bao
    private CircularImageView profileimg;
    private TextView save, cancel;
    private EditText fist, last, email;
    private RadioGroup radioGroup;
    private RadioButton male, female;
    private Button btnlogout;

    //Firebase

    private Uri filePath;



    //bien
    private String gioitinh;
    private String Fist, Last, Email;

    private String link;
    private String nam = "Nam", nu = "Nữ";
    private String fistname = "", lastname = "", emailuser = "";
    // dành để kiểm tra sự thay đổi
    private boolean isfist = false;
    private boolean islast = false;
    private boolean isemail = false;
    private boolean issex = false;
    private boolean isimg = false;


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
        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                isimg = true;
                checUpdate();
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Login.class));
                StaticConfig.mUser.child(StaticConfig.currentuser).child("status").setValue("offline");
                //firebase logout
                StaticConfig.fAuth.getInstance().signOut();
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
        //khi dang thay đổi edittext
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
                    //check if fistname is null
                    if (fistname.isEmpty()) {
                        fist.setError("fistname is require!!");
                        isfist = false;
                        checUpdate();
                    }
                    //check fist name is = fistname edittext
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
        //khi nhấp vào radio
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

    //mở hình
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), StaticConfig.PICK_IMAGE_REQUEST);
    }

    //gán dữ liệu vào filePath
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == StaticConfig.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                //thay đổi hình
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                profileimg.setImageBitmap(bitmap);
                //up hình lên firebase
                uploadImage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Up hình lên firebase
    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = StaticConfig.storageReference.child("Avatar/" + StaticConfig.currentuser + "/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //lấy link đã up lên firebase
                                    Log.d("downloadUrl:", "" + uri);
                                    link = uri.toString();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = 100.0 * (snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progressDialog.setMessage("Upload is " + progress + "% done");
                        }
                    });
        }
    }

    //kiểm tra người dùng khi thay đổi dữ liệu
    private void checUpdate() {
        if (!fistname.isEmpty() && !lastname.isEmpty() && !emailuser.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailuser).matches()) {
            if (isfist == true || islast == true || isemail == true || issex == true || isimg == true) {
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

    //kiểm tra trước khi thực hiện thay đổi
    private void Update() {
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setTitle("Update?");
        b.setMessage("Are you sure ?");
        b.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ///gán dữ liệu vào firebase
                StaticConfig.mUser.child(StaticConfig.currentuser).child("fist").setValue(fist.getText().toString());
                StaticConfig.mUser.child(StaticConfig.currentuser).child("last").setValue(last.getText().toString());
                StaticConfig.mUser.child(StaticConfig.currentuser).child("email").setValue(email.getText().toString());
                StaticConfig.mUser.child(StaticConfig.currentuser).child("pic").setValue(link);


                StaticConfig.updateUser.updateEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String TAG = "\"Update Email:\"";
                        if (task.isSuccessful()) {
                            Log.d(TAG, "ok");
                        } else {
                            Log.d(TAG, "fail");
                        }
                    }
                });
                if (male.isChecked()) {
                    StaticConfig.mUser.child(StaticConfig.currentuser).child("sex").setValue(nam);
                }
                if (female.isChecked()) {
                    StaticConfig.mUser.child(StaticConfig.currentuser).child("sex").setValue(nu);
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
        btnlogout = view.findViewById(R.id.btnlogout);

     

        //load dữ liệu của user hiện tại

        Query check = StaticConfig.mUser.orderByChild("id").equalTo(StaticConfig.currentuser);
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
                        link = ds.child("pic").getValue(String.class);
                        //gán link vào imgview

                        Picasso.get()
                                .load(link)
                                .fit()
//                                .transform(transformation)
                                .into(profileimg);
                        profileimg.setBorderColor(Color.BLUE);


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
            btnlogout.setVisibility(View.VISIBLE);
        }
    }

    //kiểm tra xem có đúng là user hiện tại không
    private boolean iscurrentuser() {
        if (StaticConfig.currentuser.equals(StaticConfig.currentuser)) {
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_profile, container, false);
        // Inflate the layout for this fragment

        setControl();
        setEnvet();
        return view;

    }
}