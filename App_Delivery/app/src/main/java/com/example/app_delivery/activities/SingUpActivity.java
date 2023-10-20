package com.example.app_delivery;

import static androidx.constraintlayout.motion.widget.TransitionBuilder.validate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class SingUpActivity extends AppCompatActivity {
    TextView nuevoUsuario,bienvenidoLabel,continuarLabel;
    ImageView signUpImageView;
    TextInputLayout usuarioSignUpTextField,contraseñaTextField;
    MaterialButton inicioSesion;
    TextInputEditText emailEditText,passwordEditText,confirmPasswordEditText;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        signUpImageView = findViewById(R.id.singUpImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarLabel = findViewById(R.id.continuarLabel);
        usuarioSignUpTextField = findViewById(R.id.usuarioSignUpTextField);
        contraseñaTextField = findViewById(R.id.contraseñaTextField);
        inicioSesion = findViewById(R.id.inicioSesion);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionBack();
            }
        });
        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    public void validate(){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Correo valido");
            return;
        }else{
            emailEditText.setError(null);
        }

        if(password.isEmpty() || password.length()<8){
            passwordEditText.setError("Se necesitan mas de 8 caracteres");
            return;
        } else if (!Pattern.compile("[0-9]").matcher(password).find()) {
            passwordEditText.setError("Al menos un numero");
            return;
        }else{
            passwordEditText.setError(null);
        }

        if(!confirmPassword.equals(password)){
            confirmPasswordEditText.setError("Deben ser iguales");
            return;
        }else{
            registrar(email,password);
        }

    }

    public void registrar(String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(SingUpActivity.this,UserActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(SingUpActivity.this,"Fallo en registrarse",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @Override
    public void onBackPressed(){
        transitionBack();
    }
    public void transitionBack(){
        Intent intent = new Intent(SingUpActivity.this,LoginActivity.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(signUpImageView,"LogoImageTrans");
        pairs[1] = new Pair<View, String>(bienvenidoLabel,"textTrans");
        pairs[2] = new Pair<View, String>(continuarLabel,"iniciaSesionTextTrans");
        pairs[3] = new Pair<View, String>(usuarioSignUpTextField,"emailInputTextTrans");
        pairs[4] = new Pair<View, String>(contraseñaTextField,"passwordInputTextTrans");
        pairs[5] = new Pair<View, String>(inicioSesion,"buttonSignInTrans");
        pairs[6] = new Pair<View, String>(nuevoUsuario,"newUserTrans");

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SingUpActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }else{
            startActivity(intent);
            finish();
        }

    }
}