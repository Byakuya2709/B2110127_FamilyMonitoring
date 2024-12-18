package ctu.demo.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import ctu.demo.R;
import ctu.demo.utils.OtpUtils;
import ctu.demo.utils.SendMail;

public class OtpFragment extends Fragment {

    private EditText etOtp;
    private Button btnVerifyOtp, btnResendOtp;
    private TextView tvTimer,etEmailVerify;
    private String email; // Email cần được chuyển qua Bundle từ RegisterFragment
    private CountDownTimer countDownTimer;

    public OtpFragment() {
        // Required empty public constructor
    }

    public static OtpFragment newInstance(String email) {
        OtpFragment fragment = new OtpFragment();
        Bundle args = new Bundle();
        args.putString("email", email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otp, container, false);

        // Lấy email từ Bundle
        if (getArguments() != null) {
            email = getArguments().getString("email");
        }
        // Khởi tạo các thành phần giao diện
        etEmailVerify = view.findViewById(R.id.et_email_verify);
        etEmailVerify.setText(email);

        etOtp = view.findViewById(R.id.et_otp);
        btnVerifyOtp = view.findViewById(R.id.btn_verify_otp);
        btnResendOtp = view.findViewById(R.id.btn_resend_otp);
        tvTimer = view.findViewById(R.id.tv_timer);

        // Xử lý xác thực OTP
        btnVerifyOtp.setOnClickListener(v -> verifyOtp());

        // Xử lý gửi lại mã OTP
        btnResendOtp.setOnClickListener(v -> resendOtp());

        // Bắt đầu đếm ngược để gửi lại mã
        startResendOtpTimer();

        return view;
    }

    private void startResendOtpTimer() {
        // Thời gian đếm ngược 60 giây
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Hiển thị thời gian còn lại
                tvTimer.setText("Bạn có thể gửi lại mã sau " + millisUntilFinished / 1000 + " giây.");
                tvTimer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                // Kết thúc đếm ngược, cho phép gửi lại mã
                tvTimer.setVisibility(View.GONE);
                btnResendOtp.setEnabled(true);
            }
        }.start();
    }

    private void verifyOtp() {
        String enteredOtp = etOtp.getText().toString().trim();

        if (enteredOtp.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra OTP với mã đã gửi
        OtpUtils.verifyOtp(email, enteredOtp, new OtpUtils.OnOtpVerificationListener() {
            @Override
            public void onSuccess() {
                // OTP hợp lệ, có thể tiếp tục tạo tài khoản
                Toast.makeText(getContext(), "Xác thực thành công", Toast.LENGTH_SHORT).show();
                // Tiếp tục thực hiện tạo tài khoản hoặc chuyển hướng
            }

            @Override
            public void onFailure(String errorMessage) {
                // OTP không hợp lệ
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resendOtp() {
        // Gửi lại mã OTP
        String otp = sendVerificationEmail(email);

        // Bắt đầu lại thời gian đếm ngược
        startResendOtpTimer();
        btnResendOtp.setEnabled(false);
    }

    private String sendVerificationEmail(String email) {
        String subject = "Xác thực email";
        OtpUtils otpUtils = new OtpUtils();
        String otp = otpUtils.generateOtp();
        String message = "Mã OTP của bạn là: " + otp;

        OtpUtils.saveOtpToFirestore(email, otp);
        // Sử dụng lớp SendMail để gửi email
        SendMail.sendEmail(email, subject, message);
        Toast.makeText(getContext(), "Mã xác thực đã được gửi đến email của bạn.", Toast.LENGTH_SHORT).show();
        return otp;
    }
}
