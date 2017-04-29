package com.foivos.WOLpp.UtilClasses;

/**
 * Created by foivos on 27-Dec-16.
 */

public class ActionRequesterResponse {
    private boolean success;
    private String message;

    public ActionRequesterResponse(boolean success) {
        this(success, "");
    }

    public ActionRequesterResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccesful() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
