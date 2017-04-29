package com.foivos.WOLpp.enums;

/**
 * Created by foivos on 27-Dec-16.
 */

public class LedIndication {
    public static enum indication {OFF, ON, UNKNOWN}

    public static int indication2int(LedIndication.indication indication) {
        switch (indication) {
            case OFF:
                return 0;
            case ON:
                return 1;
            case UNKNOWN:
                return -1;
        }
        return -1;
    }

    public static LedIndication.indication int2indication(int i) {
        switch (i) {
            case 0:
                return indication.OFF;
            case 1:
                return indication.ON;
            default:
                return indication.UNKNOWN;
        }
    }
}
