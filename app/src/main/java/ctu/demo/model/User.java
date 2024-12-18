package ctu.demo.model;

import java.sql.Time;
import java.util.Date;

public class User {
    private String fullName;
    private Date birthDate;
    private String phoneNumber;
    private String province;
    private Gender gender;
    private String familyRole;
    private String email;  // Email người dùng
    private boolean isActive;  // Trạng thái tài khoản (active / blocked)
    private UserRole role;  // Vai trò của người dùng (admin, user, etc.)
    private String address;  // Địa chỉ cụ thể
    private Date accountCreated;  // Ngày tạo tài khoản

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }
    public enum UserRole {
        ADMIN,
        USER,
        MODERATOR
    }
    public User() {
    }
    public User(String fullName, Date birthDate, String phoneNumber, String province, Gender gender, String email, String familyRole) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.province = province;
        this.gender = gender;
        this.email = email;
        this.familyRole = familyRole;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFamilyRole() {
        return familyRole;
    }

    public void setFamilyRole(String familyRole) {
        this.familyRole = familyRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(Date accountCreated) {
        this.accountCreated = accountCreated;
    }
}
