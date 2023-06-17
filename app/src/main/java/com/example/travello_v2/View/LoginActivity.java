package com.example.travello_v2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travello_v2.Api.LoginData;
import com.example.travello_v2.Interface.LoginDataListener;
import com.example.travello_v2.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity{

    private Button loginButton, backButton, createButton;

    private EditText emailEditText;
    private EditText passwordEditText;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);
        backButton = findViewById(R.id.button_back);
        createButton = findViewById(R.id.button_create);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                LoginData loginData = new LoginData(email, password, new LoginDataListener() {
                    @Override
                    public void onLoginDataReceived(String token, int statusCode, String message) {
                        if (statusCode == 200){
                            TokenManager tokenManager = new TokenManager(getApplicationContext());
                            tokenManager.saveToken(token);
                            initCostumeDialog(getApplicationContext(),"Success", message, true);
                            dialog.show();
                        }else {
                            initCostumeDialog(getApplicationContext(),"Failed", message, false);
                            dialog.show();
                        }
                    }
                });
                loginData.execute();

            }
        });

    }

    private void initCostumeDialog(Context context, String title, String message, boolean success){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_message);
        dialog.setCancelable(!success);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageView = dialog.findViewById(R.id.icon);

        TextView etTitle = dialog.findViewById(R.id.title);
        TextView etMessage = dialog.findViewById(R.id.message);
        Button etButton = dialog.findViewById(R.id.btnInput);

        etTitle.setText(title);
        etMessage.setText(message);

        if (success){
            etTitle.setTextColor(ContextCompat.getColor(this, R.color.success));
            imageView.setImageResource(R.drawable.truee);
            etButton.setBackgroundColor(ContextCompat.getColor(this, R.color.success));
            etButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DashboardActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                    finish();
                }
            });
            new CountDownTimer(6000, 1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    etButton.setText(String.format(
                            Locale.getDefault(), "continue (%d)", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
                    ));
                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(context, DashboardActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                    finish();
                }
            }.start();
        } else  {
            etTitle.setTextColor(ContextCompat.getColor(this, R.color.fail));
            imageView.setImageResource(R.drawable.falsee);
            etButton.setBackgroundColor(ContextCompat.getColor(this, R.color.fail));
            etButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            new CountDownTimer(6000, 1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    etButton.setText(String.format(
                            Locale.getDefault(), "try again (%d)", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
                    ));
                }

                @Override
                public void onFinish() {
                    dialog.dismiss();
                }
            }.start();
        }

    }

}