package ctu.demo.utils;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class OtpUtils {
    private static final int OTP_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

    public static String generateOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10)); // Sinh số ngẫu nhiên từ 0 đến 9
        }
        return otp.toString();
    }

    // Lưu OTP vào Firestore
    public static void saveOtpToFirestore(String email, String otp) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        long expirationTime = System.currentTimeMillis() + 5 * 60 * 1000; // 5 phút
        deleteOtp(email); // Xóa OTP cũ nếu có

        Map<String, Object> otpData = new HashMap<>();
        otpData.put("email", email);
        otpData.put("otp", otp);
        otpData.put("expirationTime", expirationTime);

        db.collection("otps")
                .document(email)
                .set(otpData)
                .addOnSuccessListener(aVoid -> Log.d("OTP", "OTP saved successfully."))
                .addOnFailureListener(e -> Log.e("OTP", "Error saving OTP: ", e));
    }
    public static void verifyOtp(String email, String enteredOtp, OnOtpVerificationListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("otps")
                .document(email)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String storedOtp = documentSnapshot.getString("otp");
                        long expirationTime = documentSnapshot.getLong("expirationTime");

                        if (storedOtp != null && storedOtp.equals(enteredOtp)) {
                            if (System.currentTimeMillis() < expirationTime) {
                                listener.onSuccess();
                            } else {
                                listener.onFailure("Mã OTP đã hết hạn.");
                            }
                        } else {
                            listener.onFailure("Mã OTP không hợp lệ.");
                        }
                    } else {
                        listener.onFailure("Không tìm thấy mã OTP.");
                    }
                })
                .addOnFailureListener(e -> listener.onFailure("Lỗi khi xác thực OTP: " + e.getLocalizedMessage()));
    }


    public interface OnOtpVerificationListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }
    public static void deleteOtp(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("otps")
                .document(email)
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("OTP", "OTP deleted successfully."))
                .addOnFailureListener(e -> Log.e("OTP", "Error deleting OTP: ", e));
    }
}
