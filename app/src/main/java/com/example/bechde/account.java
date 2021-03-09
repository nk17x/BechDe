package com.example.bechde;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class account extends AppCompatActivity {
    EditText namestext, emailtext, mobiletext;
    ImageView imageprofile;
    Button updatebt, backbt;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference,fileReference;
    String protemail,protphone,protfullname;
    String username;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri mImageURi;
    ImageButton homebutton,chatbutton,adbutton,newadbutton,accountbutton;
    Context context;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        toolbar=findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(account.this,MainActivity.class);
                startActivity(i2);
                finish();
            }
        });
        namestext = findViewById(R.id.namestext);
        emailtext = findViewById(R.id.emailtext);
        mobiletext = findViewById(R.id.mobiletext);
        imageprofile = findViewById(R.id.imageprofile);
        updatebt = findViewById(R.id.updatebt);
        backbt = findViewById(R.id.backbt);
        homebutton=findViewById(R.id.homebutton);
        chatbutton=findViewById(R.id.chatbutton);
        accountbutton=findViewById(R.id.accountbutton);
        adbutton=findViewById(R.id.adbutton);
        newadbutton=findViewById(R.id.newadbutton);
        mAuth=FirebaseAuth.getInstance();
        this.context=context;
        String email=mAuth.getCurrentUser().getEmail();
        String str = email;
        if(str.indexOf(".")!=-1){
            String [] twoStringArray2= str.split("@",2);
            String username2= twoStringArray2[0];
            String [] twoStringArray= username2.split("\\.",2);
            username= twoStringArray[0];
        }else{
            String [] twoStringArray2= str.split("@",2);
            username= twoStringArray2[0];}
        imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("users");
        Query userdetails2 = databaseReference.orderByChild("username").equalTo(username);
        userdetails2.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String fullnameFromDb=dataSnapshot.child(username).child("fullname").getValue(String.class);
                    String phoneFromDb=dataSnapshot.child(username).child("phone").getValue(String.class);
                    String emailFromDb=dataSnapshot.child(username).child("email").getValue(String.class);
                    String imgurlfromdb=dataSnapshot.child(username).child("imgurl").getValue(String.class);
                    emailtext.setText(emailFromDb);
                    namestext.setText(fullnameFromDb);
                    mobiletext.setText(phoneFromDb);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Glide.with(imageprofile).load(imgurlfromdb).fitCenter().circleCrop().into(imageprofile);
                        }
                    }, 300);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }});
        backbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(account.this,login.class);
                startActivity(i);
                finish();
            }
        });

        updatebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                protphone=mobiletext.getText().toString();
                protfullname =namestext.getText().toString();
                protemail=emailtext.getText().toString();
                storageReference = FirebaseStorage.getInstance().getReference("users");
                uploadfile();

                if(TextUtils.isEmpty(protemail)){
                    emailtext.setError("enter email");
                    return;
                }
                if(TextUtils.isEmpty(protphone)){
                    mobiletext.setError("enter phone no");
                    return;
                }
                if(TextUtils.isEmpty(protfullname)){
                    namestext.setError("enter fullname");
                    return;}
            }
        });

        newadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(account.this,newad.class);
                startActivity(i2);
                finish();
            }
        });

        adbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(account.this,myad.class);
                startActivity(i2);
                finish();
            }
        });

        chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(account.this, "new ad pressed", Toast.LENGTH_SHORT).show();
            }
        });

        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenta=new Intent(account.this,account.class);
                startActivity(intenta);
                finish();
            }
        });

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(account.this,MainActivity.class);
                startActivity(i2);
                finish();
            }
        });

    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadfile() {
        if (mImageURi != null) {
            fileReference = storageReference.child(username+ "." + getFileExtension(mImageURi).toString());
            fileReference.putFile(mImageURi).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imgurl = uri.toString();
                            UserHelperClass helperClass= new UserHelperClass(protfullname,protphone,protemail,username,imgurl);
                            databaseReference.child(username).setValue(helperClass);
                        }
                    });
                    Toast.makeText(account.this, "new ad added Succesfully", Toast.LENGTH_SHORT).show();
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(account.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "image not selected", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageURi = data.getData();
            Glide.with(this).load(mImageURi).fitCenter().circleCrop().into(imageprofile);
/*
            Picasso.get().load(mImageURi).transform(new RoundedCornersTransformation(10,10)).resize(500,400).into(imageprofile);
*/

        }
    }
    @Override
    public void onBackPressed() {
        Intent i2 =new Intent(account.this,MainActivity.class);
        startActivity(i2);
        finish();

    }
}
