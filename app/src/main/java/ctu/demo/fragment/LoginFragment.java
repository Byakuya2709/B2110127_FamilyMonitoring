package ctu.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ctu.demo.R;
import ctu.demo.activity.MainActivity;

public class LoginFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);

        Button loginButton = view.findViewById(R.id.btn_login);
        loginButton.setOnClickListener(v -> {
            // Kiểm tra đăng nhập
            if (isLoginSuccessful()) {
                // Nếu đăng nhập thành công, gọi phương thức trong MainActivity
                ((MainActivity) getActivity()).onLoginSuccess();
            }
        });

        TextView registerLink = view.findViewById(R.id.btn_register_redirect);
        registerLink.setOnClickListener(v -> {
            // Nếu người dùng muốn đăng ký, chuyển sang RegisterFragment
            ((MainActivity) getActivity()).showRegisterFragment();
        });

        return view;
    }

    private boolean isLoginSuccessful() {
        // Logic kiểm tra đăng nhập ở đây
        return true; // Giả sử đăng nhập luôn thành công
    }
}
