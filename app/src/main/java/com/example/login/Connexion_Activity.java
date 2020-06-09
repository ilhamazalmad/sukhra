package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class Connexion_Activity extends AppCompatActivity {

    LinearLayout linear1,linearLayout;
    Animation animation;
    Button btn_incription,btn_conx,btn_connecter;
    ImageView img_logo;
    TextView text_logo;
    EditText e_username,e_pwd;
    validation validation;
    FirebaseFirestore db;
    Client client;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        linear1 = findViewById(R.id.linear1);
        animation = AnimationUtils.loadAnimation(Connexion_Activity.this,R.anim.conx_animation);
        linear1.setAnimation(animation);

        validation = new validation(this);
        db = FirebaseFirestore.getInstance();
        client =new Client();
        session=new Session(getApplicationContext());

        btn_incription =findViewById(R.id.btn_incription);
        btn_conx =findViewById(R.id.btn_conx);
        img_logo=findViewById(R.id.img_logo);
        text_logo=findViewById(R.id.text_logo);
        btn_connecter=findViewById(R.id.btn_connecter);
        e_username=findViewById(R.id.e_username);
        e_pwd=findViewById(R.id.e_pwd);
        linearLayout = findViewById(R.id.linearLayout);

        if(session.getLogin()){
            startActivity(new Intent(getApplicationContext(),Home_Activity.class));
            finish();
        }
        View.OnClickListener Listener_inscription = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empty();
                Intent intent1 = new Intent(Connexion_Activity.this,Inscription_Activity.class);
                Pair[] pairs=new Pair[3];
                pairs[0]=new Pair<View,String>(btn_conx,"trans_conx");
                pairs[1]=new Pair<View,String>(img_logo,"trans_img");
                pairs[2]=new Pair<View,String>(text_logo,"trans_logo");
                ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(Connexion_Activity.this,pairs);
                startActivity(intent1,activityOptions.toBundle());
                finish();
            }
        };
        View.OnClickListener Listener_connexion = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifier();
            }
        };


        btn_incription.setOnClickListener(Listener_inscription);
        btn_connecter.setOnClickListener(Listener_connexion);
    }
    public void verifier() {
        if (!validation.isInputEditText(e_username, getString(R.string.error_username))) {
            return;
        }
        if (!validation.isInputEditText(e_pwd, getString(R.string.error_message_password))) {
            return;
        } else {
            client.setUsername(e_username.getText().toString().trim());
            client.setPassword(e_pwd.getText().toString().trim());
            db.collection("Client").
                    whereEqualTo("nomcom", client.getUsername()).
                    whereEqualTo("password", client.getPassword()).
                    get().
                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (Objects.requireNonNull(task.getResult()).size() > 0) {
                                client.setPhone(Objects.requireNonNull(task.getResult()).getDocuments().get(0).getId());
                                //Snackbar.make(linearLayout, client.getPhone(), Snackbar.LENGTH_LONG).show();
                                session.setLogin(true);
                                session.setClient(client.getUsername());
                                empty();
                                Intent intent = new Intent(Connexion_Activity.this,Home_Activity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Snackbar.make(linearLayout, getString(R.string.error_valid_user_password), Snackbar.LENGTH_LONG).show();
                            }

                        }
                    });
        }
    }
    private void empty () {
        e_username.setText(null);
        e_pwd.setText(null);
    }
}