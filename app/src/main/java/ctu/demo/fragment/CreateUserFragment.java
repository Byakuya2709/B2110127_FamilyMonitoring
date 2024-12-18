package ctu.demo.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import ctu.demo.R;

public class CreateUserFragment extends Fragment {
    private EditText etBirthdate;
    private Button btnSave;
    private FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userdetail_fragment, container, false);

        etBirthdate = view.findViewById(R.id.et_birthdate);
        etBirthdate.setOnClickListener(v -> getCalendar());
        btnSave = view.findViewById(R.id.btn_submit_user_info);

        String userUid = getArguments().getString("userUid");
        String fullname = getArguments().getString("fullName");



        return view;
    }
    public void getCalendar(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view1, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    // Cập nhật ngày đã chọn vào EditText
                    etBirthdate.setText(selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear);
                }, year, month, day);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }
}
