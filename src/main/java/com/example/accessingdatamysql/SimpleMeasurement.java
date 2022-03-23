package com.example.accessingdatamysql;

import java.util.Objects;

public class SimpleMeasurement {
    private int time;
    private int value;

    public SimpleMeasurement(int time, int value) {
        this.time = time;
        this.value = value;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleMeasurement that = (SimpleMeasurement) o;
        return time == that.time && value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
