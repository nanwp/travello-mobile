package com.example.travello_v2.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.travello_v2.Api.UserData;
import com.example.travello_v2.Interface.UserDataListener;
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
    Button btnLogout, btnLogin, btnRegister, btnChangeName, btnChangePassword;

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


        return view;
    }

    @Override
    public void onUserDataReceived(String name, String email) {
        etEmail.setText(email);
        etName.setText(name);
    }
}