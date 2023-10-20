package com.example.app_delivery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.app_delivery.R;
import com.example.app_delivery.UserActivity1;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //Agregamos Animaciones
        Animation animacion1 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);

        TextView FastFooTextView = findViewById(R.id.FastFood);
        TextView ConTodoCremasTextView = findViewById(R.id.ConTodoCremas);
        ImageView LogoImageView = findViewById(R.id.LogoImageView);

        FastFooTextView.setAnimation(animacion2);
        ConTodoCremasTextView.setAnimation(animacion2);
        LogoImageView.setAnimation(animacion1);

        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                if(user != null && account !=null){
                    Intent intent = new Intent(MainActivity.this, UserActivity1.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View,String>(LogoImageView,"LogoImageTrans");
                    pairs[1] = new Pair<View,String>(FastFooTextView,"textTrans");

                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                        startActivity(intent,options.toBundle());
                    }else{
                        startActivity(intent);
                        finish();
                    }

                    startActivity(intent);
                    finish();
                }

            }
        },5000);{

        }
    }
}