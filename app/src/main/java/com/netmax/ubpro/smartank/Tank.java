package com.netmax.ubpro.smartank;

/**
 * Created by Jagdeep on 27/11/16.
 */

public class Tank {


    String status;
    String litre;
    String seconds;

    public Tank() {
    }

    public Tank(String status, String litre, String seconds) {
        this.status = status;
        this.litre = litre;
        this.seconds = seconds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLitre() {
        return litre;
    }

    public void setLitre(String litre) {
        this.litre = litre;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }
}
