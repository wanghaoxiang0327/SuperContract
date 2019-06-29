package com.sskj.mine.data;

public class GoogleInfo {

    /**
     * command : 7O23C4EQVBZKI2IJ
     * url : https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth%3A%2F%2Ftotp%2F384111%3Fsecret%3D7O23C4EQVBZKI2IJ%26issuer%3Dwhale
     * local_url : http://www.mobidiex.com/Uploads/google/384111.png
     */

    private String command;
    private String local_url;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getLocal_url() {
        return local_url;
    }

    public void setLocal_url(String local_url) {
        this.local_url = local_url;
    }
}
