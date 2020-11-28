package com.example.fyp4;

public class parking {
    private String carPlat,status;
    private int parkingno;

    public parking() {
    }

    public parking(String carPlat, String status, int parkingno) {
        this.carPlat = carPlat;
        this.status = status;
        this.parkingno = parkingno;
    }

    public String getCarPlat() {
        return carPlat;
    }

    public int getParkingno() {
        return parkingno;
    }

    public void setParkingno(int parkingno) {
        this.parkingno = parkingno;
    }

    public void setCarPlat(String carPlat) {
        this.carPlat = carPlat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
