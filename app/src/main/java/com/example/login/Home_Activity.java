package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button button;
    TextView m_username;
    Session session;
    LinearLayout linearLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView img_menu;
    final static float END_SCALE=0.7f;
    //ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        //setContentView(R.layout.entete);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigationView);
        img_menu=findViewById(R.id.img_menu);
        linearLayout = findViewById(R.id.linearLayout);

       // m_username = findViewById(R.id.m_username);

       session= new Session(getApplicationContext());
       // Toast.makeText(Home_Activity.this,session.getClient(),Toast.LENGTH_SHORT).show();
        //m_username.setText(session.getClient());




        Drawer();



    }

    // Drawer
    private void Drawer() {
       navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.Catégories);

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else
                {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        animate();
    }

    private void animate() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.gris1));
       // drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                linearLayout.setScaleX(offsetScale);
                linearLayout.setScaleY(offsetScale);

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = linearLayout.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                linearLayout.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Profil:
                Toast.makeText(Home_Activity.this,"Profil",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Panier:
                Toast.makeText(Home_Activity.this,"Panier",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Catégories:
                Intent intent = new Intent(Home_Activity.this,Home_Activity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.Commandes_en_attente:
                Toast.makeText(Home_Activity.this,"Commande en attente",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Réclamation:
                Toast.makeText(Home_Activity.this,"reclamation",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Déconnexion:
                Deconnexion();

                return true;
        }
        return true;
    }

        private void Deconnexion() {
            AlertDialog.Builder builder = new AlertDialog.Builder(linearLayout.getContext());
            builder.setTitle("Deconnexion");
            builder.setMessage("êtes-vous sûr de vous déconnecter?");
            builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    session.setLogin(false);
                    session.setClient("");
                    startActivity(new Intent(getApplicationContext(),Connexion_Activity.class));
                    finish();
                }
            });
            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }

}