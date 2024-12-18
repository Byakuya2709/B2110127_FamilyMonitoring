package ctu.demo.fragment;

import static android.widget.Toast.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import ctu.demo.R;
import ctu.demo.utils.OtpUtils;
import ctu.demo.utils.SendMail;

public class RegisterFragment extends Fragment {
    private EditText etFullName, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;

    private FirebaseAuth mAuth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register, container, false);


        // Ánh xạ View
        etFullName = view.findViewById(R.id.et_fullname);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);


        // Khởi tạo Firebase Auth và Firestore
        mAuth = FirebaseAuth.getInstance();

        btnRegister = view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(v -> registerUser());

        Button registerButton = view.findViewById(R.id.btn_login_redirect);
        registerButton.setOnClickListener(v -> redicrectToLogin());

        return view;
    }

    public void redicrectToLogin(){
        getActivity().getSupportFragmentManager().popBackStack();
    }
    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Kiểm tra dữ liệu nhập vào
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getContext(), "Mật khẩu xác nhận không khớp", LENGTH_SHORT).show();
            return;
        }

        //send mail chỗ này
        String otp = sendVerificationEmail(email);
//        //xác thực otp
//        Toast.makeText(getContext(), "eeeee", LENGTH_SHORT).show();
        //tạo tài khoản
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        // Lấy người dùng hiện tại
//                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("userUid", firebaseUser.getUid());
//                        bundle.putString("fullName", fullName);
//                        bundle.putString("email",email);
////
////                        CreateUserFragment userInfoFragment = new CreateUserFragment();
////                        userInfoFragment.setArguments(bundle);
////                        getActivity().getSupportFragmentManager().beginTransaction()
////                                .replace(R.id.fragment_container, userInfoFragment)
////                                .addToBackStack(null) // Thêm fragment vào back stack để quay lại được
////                                .commit();
//                        OtpFragment otpFragment = OtpFragment.newInstance(email);;
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.fragment_container, otpFragment)
//                                .commit();
//                    } else {
//                        // Handle specific errors for registration failure
//                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
//                            Toast.makeText(getContext(), "Email này đã được sử dụng.", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getContext(), "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
    }

    private String sendVerificationEmail(String email) {
        String subject = "Xác thực email";
        OtpUtils otpUtils = new OtpUtils();
        String otp = otpUtils.generateOtp();
        String message = "Mã OTP của bạn là: "+ otp;

        //luu ma otp
        OtpUtils.saveOtpToFirestore(email, otp);
        // Sử dụng lớp SendMail để gửi email
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    SendMail.sendEmail(email, subject, message);
//                    // Cập nhật UI sau khi gửi email thành công
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getContext(), "Mã xác thực đã được gửi đến email của bạn.", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } catch (Exception e) {
//                    // Cập nhật UI nếu có lỗi xảy ra
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getContext(), "Có lỗi khi gửi email: " + e.getMessage(), LENGTH_LONG).show();
//                        }
//                    });
//                }
//            }
//        }).start();
        return otp;
    }
}
