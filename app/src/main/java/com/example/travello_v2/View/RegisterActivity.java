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

import com.example.travello_v2.Api.RegistData;
import com.example.travello_v2.Interface.StatusMessageDataListener;
import com.example.travello_v2.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    private Button btnCreate, btnLogin;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    Dialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.first_name_regist);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email_regist);
        password = findViewById(R.id.password_regist);
        btnCreate = findViewById(R.id.tombol_regist);
        btnLogin = findViewById(R.id.move_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valFirstName = firstName.getText().toString();
                String valLastName = lastName.getText().toString();
                String valEmail = email.getText().toString();
                String valPassword = password.getText().toString();

                RegistData registData = new RegistData(valFirstName, valLastName, valEmail, valPassword, new StatusMessageDataListener() {
                    @Override
                    public void onStatusMessageDataReceived(int statusCode, String message) {
                        if (statusCode == 201){
                            initCostumeDialog(getApplicationContext(),"Success", message, true);
                            dialog.show();
                        }else {
                            initCostumeDialog(getApplicationContext(),"Failed", message, false);
                            dialog.show();
                        }
                    }
                });
                registData.execute();

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
            final boolean[] buttonClicked = {false};
            etTitle.setTextColor(ContextCompat.getColor(this, R.color.success));
            imageView.setImageResource(R.drawable.truee);
            etButton.setBackgroundColor(ContextCompat.getColor(this, R.color.success));
            etButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClicked[0] = true;
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            new CountDownTimer(6000, 1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    etButton.setText(String.format(
                            Locale.getDefault(), "login (%d)", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
                    ));
                }

                @Override
                public void onFinish() {
                    if(!buttonClicked[0]){
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        dialog.dismiss();
                    }
                }
            }.start();
        } else {
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