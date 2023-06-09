package com.example.travello_v2.View;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.travello_v2.Api.UpdataPasswordTask;
import com.example.travello_v2.Api.UpdateUserTask;
import com.example.travello_v2.Api.UserData;
import com.example.travello_v2.Interface.UserDataListener;
import com.example.travello_v2.Models.User;
import com.example.travello_v2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements UserDataListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout profileLayout, loginLayout;
    TextView etName, etEmail;
    private Dialog customDialog;
    Button btnLogout, btnLogin, btnRegister, btnChangeName, btnChangePassword, btnEditName, btnEditPassword;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment



        TokenManager tokenManager = new TokenManager(getContext());
        boolean isLogin = tokenManager.hasToken();

        profileLayout = view.findViewById(R.id.profile_layout);
        loginLayout = view.findViewById(R.id.login_layout);
        btnLogin = view.findViewById(R.id.login_button);
        btnLogout = view.findViewById(R.id.logout_button);
        btnRegister = view.findViewById(R.id.regist_button);
        btnChangeName = view.findViewById(R.id.change_name);
        btnChangePassword = view.findViewById(R.id.change_password);
        etEmail = view.findViewById(R.id.email);
        etName = view.findViewById(R.id.name);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokenManager.deleteToken();
                Intent intent = new Intent(getContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnEditName = view.findViewById(R.id.change_name);
        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCustomDialogChangeName(tokenManager.getToken());
                customDialog.show();
            }
        });

        if (isLogin){
            UserData userData = new UserData(tokenManager.getToken(), this);
            userData.execute();
            profileLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
        }else {
            profileLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);
        }

        Button loginBtn = view.findViewById(R.id.login_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnEditPassword = view.findViewById(R.id.change_password);
        btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCustomDialogChangePassword(tokenManager.getToken());
                customDialog.show();
            }
        });

        return view;
    }
    private void initCustomDialogChangePassword(String token) {
        customDialog = new Dialog(requireContext());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_edit_password);
        customDialog.setCancelable(true);

        EditText inOldPassword, inNewPassword;
        inOldPassword = customDialog.findViewById(R.id.txtOldPassword);
        inNewPassword = customDialog.findViewById(R.id.txtNewPassword);

        Button btnOKEditPassword;
        btnOKEditPassword = customDialog.findViewById(R.id.btnChangePassword);
        btnOKEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = inOldPassword.getText().toString();
                String newPassword = inNewPassword.getText().toString();

                UpdataPasswordTask task = new UpdataPasswordTask(requireContext(), token, oldPassword, newPassword);
                task.execute();

                customDialog.dismiss();
            }
        });

    }

    private void initCustomDialogChangeName(String token) {
        customDialog = new Dialog(requireContext());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_edit_user);
        customDialog.setCancelable(true);

        TextView txtTitle = customDialog.findViewById(R.id.title_change_name);
        txtTitle.setText("change name");
        TextView textSubTitle = customDialog.findViewById(R.id.txt_SubTitle);
        textSubTitle.setText("New name : ");

        EditText txtInputEditUser;
        Button btnOKEditUser;
        txtInputEditUser = customDialog.findViewById(R.id.txt_input_new_name);
        txtInputEditUser.setHint("name");
        btnOKEditUser = customDialog.findViewById(R.id.btn_input_new_name);
        btnOKEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtInputEditUser.getText().toString();
                UpdateUserTask task = new UpdateUserTask(requireContext(), token, name);
                task.execute();
                customDialog.dismiss();
            }
        });

    }

    @Override
    public void onUserDataReceived(String name, String email) {
        etEmail.setText(email);
        etName.setText(name);
    }
}