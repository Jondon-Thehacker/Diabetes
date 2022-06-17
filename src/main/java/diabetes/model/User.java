package diabetes.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

//Jonathan Max Michelsen, s204437
@Entity
@Table(name = "doctor_user")
public class User implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    private String password;

    public User(Doctor doctor, String password){
        this.doctor = doctor;
        this.password = password;
    }

    public User(){

    }

    //Overriding equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return doctor.equals(user.doctor) && password.equals(user.password);
    }

    //Overriding equals and hashCode
    @Override
    public int hashCode() {
        return Objects.hash(doctor, password);
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
