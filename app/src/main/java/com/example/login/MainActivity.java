package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.varunest.sparkbutton.SparkButton;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    Animation right,left,bottom,top;
    ImageView img_logo,img_packet;
    TextView text_logo,text_desc;
    SparkButton spark_button;
    static int nbr=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //pour l'action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        right = AnimationUtils.loadAnimation(this, R.anim.right_animation);
        left = AnimationUtils.loadAnimation(this, R.anim.left_animation);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        top = AnimationUtils.loadAnimation(this, R.anim.inscrip_animation);

        img_logo = findViewById(R.id.img_logo);
        text_logo = findViewById(R.id.text_logo);
        text_desc = findViewById(R.id.text_desc);
        img_packet = findViewById(R.id.img_packet);
        spark_button = findViewById(R.id.spark_button);

        img_logo.setAnimation(left);
        text_logo.setAnimation(right);
        text_desc.setAnimation(bottom);
        //img_packet.setAnimation(top);
        img_packet.animate().alpha(1).setDuration(500).setStartDelay(1000);
        img_packet.animate().translationY(95).setDuration(800).setStartDelay(1200);
        View.OnClickListener Listener_start = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spark_button . playAnimation ();
                Handler h =new Handler() ;
                h.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, Connexion_Activity.class);
                        Pair[] pairs = new Pair[3];
                        pairs[0] = new Pair(img_logo, "trans_img");
                        // pairs[1]=new Pair(img_packet,"trans_img");
                        pairs[1] = new Pair(text_logo, "trans_logo");
                        pairs[2] = new Pair(text_desc, "trans_desc");
                        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                        startActivity(intent, activityOptions.toBundle());
                        finish();
                    }

                }, 1000);
            }

        };
        spark_button.setOnClickListener(Listener_start);

    }

}