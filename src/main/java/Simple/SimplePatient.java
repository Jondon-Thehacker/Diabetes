package Simple;

import java.util.Objects;

public class SimplePatient  {
    private Long patientId;
    private String patientName;

    public SimplePatient(Long patientId, String patientName){
        this.patientId = patientId;
        this.patientName = patientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimplePatient that = (SimplePatient) o;
        return patientId.equals(that.patientId) && patientName.equals(that.patientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, patientName);
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
