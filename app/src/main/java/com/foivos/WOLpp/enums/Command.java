package com.foivos.WOLpp.enums;

/**
 * Created by foivos on 27-Dec-16.
 */

public class Command {

    public static enum command {IDLE, PWR_MOMENTARY_PUSH, PWR_LONG_PUSH, RST_MOMENTARY_PUSH}

    public static Integer command2int(Command.command com) {
        switch (com) {
            case IDLE:
                return 0;
            case PWR_MOMENTARY_PUSH:
                return 1;
            case PWR_LONG_PUSH:
                return 2;
            case RST_MOMENTARY_PUSH:
                return 3;
        }
        return -1;
    }


    public static command int2command(int i) {
        switch (i) {
            case 0:
                return command.IDLE;
            case 1:
                return command.PWR_MOMENTARY_PUSH;
            case 2:
                return command.PWR_LONG_PUSH;
            case 3:
                return command.RST_MOMENTARY_PUSH;
            default:
                return null;
        }
    }

}
