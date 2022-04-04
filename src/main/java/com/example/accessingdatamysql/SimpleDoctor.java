package com.example.accessingdatamysql;

import java.util.Objects;

public class SimpleDoctor {
    private Long doctorId;
    private String doctorName;

    public SimpleDoctor(Long doctorId, String doctorName){
        this.doctorId = doctorId;
        this.doctorName = doctorName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleDoctor that = (SimpleDoctor) o;
        return doctorId.equals(that.doctorId) && doctorName.equals(that.doctorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, doctorName);
    }
}
