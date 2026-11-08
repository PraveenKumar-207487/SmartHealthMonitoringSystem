
package com.example.shms.dto;

public class PatientResponse {
    private Integer patientId;
    private String name;
    private String email;
    private String phone;
    private Integer age;
    private String gender;
    private String medicalHistory;

    public PatientResponse(Integer patientId, String name, String email,
                           String phone, Integer age, String gender,
                           String medicalHistory) {
        this.patientId = patientId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.medicalHistory = medicalHistory;
    }
    public Integer getPatientId() {
        return patientId;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public Integer getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }
    public String getMedicalHistory() {
        return medicalHistory;
    }
}
