package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Inscription_Activity extends AppCompatActivity {

    private static final String TAG ="" ;
    LinearLayout linear1,linearLayout;
    Animation animation;
    Button  btn_conx ,btn_inscrire,btn_incription;
    ImageView img_logo;
    TextView text_logo;
    EditText e_username,e_pwd,e_cpwd,e_numero;
    validation validation;
    FirebaseFirestore db;
    Client client;
    Session session;
    //BD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        linear1 = findViewById(R.id.linear1);
        animation = AnimationUtils.loadAnimation(Inscription_Activity.this,R.anim.inscrip_animation);
        linear1.setAnimation(animation);

        validation = new validation(this);
        db = FirebaseFirestore.getInstance();
        client =new Client();
        session=new Session(getApplicationContext());
        //bd=new BD(db);


        btn_inscrire =findViewById(R.id.btn_inscrire);
        btn_incription =findViewById(R.id.btn_incription);
        btn_conx =findViewById(R.id.btn_conx);
        img_logo=findViewById(R.id.img_logo);
        text_logo=findViewById(R.id.text_logo);
        e_username=findViewById(R.id.e_username);
        e_pwd=findViewById(R.id.e_pwd);
        e_cpwd=findViewById(R.id.e_cpwd);
        e_numero=findViewById(R.id.e_numero);
        linearLayout=findViewById(R.id.linearLayout);

        if(session.getLogin()){
            startActivity(new Intent(getApplicationContext(),Home_Activity.class));
            finish();
        }

        View.OnClickListener Listener_connexion = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empty();
                Intent intent1 = new Intent(Inscription_Activity.this,Connexion_Activity.class);
                Pair[] pairs=new Pair[3];
                pairs[0]=new Pair<View,String>(btn_incription,"trans_conx");
                pairs[1]=new Pair<View,String>(img_logo,"trans_img");
                pairs[2]=new Pair<View,String>(text_logo,"trans_logo");
                ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(Inscription_Activity.this,pairs);
                startActivity(intent1,activityOptions.toBundle());
                finish();
            }
        };
        View.OnClickListener Listener_inscription = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifier();
            }
        };

        btn_conx.setOnClickListener(Listener_connexion);
        btn_inscrire.setOnClickListener(Listener_inscription);


    }
    private void verifier() {

        if (!validation.isInputEditText(e_username, getString(R.string.error_last_name))) {
            return;
        }
        if (!validation.isInputEditText(e_pwd, getString(R.string.error_message_password))) {
            return;
        }
        if (!validation.ismin(e_pwd,getString(R.string.error_password)) ){
            return;
        }
        if (!validation.isInputMatches(e_pwd, e_cpwd, getString(R.string.error_password_match))) {
            return;
        }

        if (!validation.isInputEditText(e_numero, getString(R.string.error_phone))) {
            return;
        }

        else {

            client.setUsername(e_username.getText().toString().trim());
            client.setPassword(e_pwd.getText().toString().trim());
            client.setPhone(e_numero.getText().toString().trim());
            db.collection("Client").
                    whereEqualTo("phone",client.getPhone()).
                    whereEqualTo("nomcom",client.getUsername()).
                    get().
                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (Objects.requireNonNull(task.getResult()).size() > 0)
                                Snackbar.make(linearLayout, getString(R.string.error_compte_exists), Snackbar.LENGTH_LONG).show();
                            else {
                                final Map<String, Object> Client = new HashMap<>();
                                Client.put("nomcom", client.getUsername());
                                Client.put("password", client.getPassword());
                                Client.put("phone", client.getPhone());

                                db.collection("Client")
                                        .document(client.getPhone())
                                        .set(Client)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(linearLayout, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
                                                session.setLogin(true);
                                                session.setClient(client.getUsername());
                                                empty();
                                                Intent intent = new Intent(Inscription_Activity.this,Home_Activity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Snackbar.make(linearLayout, getString(R.string.error_compte_exists), Snackbar.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        }
                    });
           /* boolean[] value=bd.compteExists(client);
           if(value[0])
           {
               Snackbar.make(linearLayout, getString(R.string.error_compte_exists), Snackbar.LENGTH_LONG).show();
           }
            else{
                boolean[] value2 =bd.inscription(client);
               if(value2[0])
               {
                   e_username.append(""+value2[0] );
               }
               else
               {
                   e_username.append(""+value2[0] );
               }
            }*/
        }
    }

    private void empty () {
        e_username.setText(null);
        e_pwd.setText(null);
        e_cpwd.setText(null);
        e_numero.setText(null);
    }


}
