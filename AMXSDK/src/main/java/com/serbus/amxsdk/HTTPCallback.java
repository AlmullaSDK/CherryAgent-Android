package com.serbus.amxsdk;

public interface HTTPCallback {
    void processFinish(String output);
    void processFailed(int responseCode, String output);
}
