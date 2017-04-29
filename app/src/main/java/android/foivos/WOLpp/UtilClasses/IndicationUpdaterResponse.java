package android.foivos.WOLpp.UtilClasses;

import android.foivos.WOLpp.enums.Command;
import android.foivos.WOLpp.enums.LedIndication;

/**
 * Created by foivos on 27-Dec-16.
 */

public class IndicationUpdaterResponse {
    private Command.command command;
    private boolean validResponse;
    private LedIndication.indication indication;
    private String message;


    public IndicationUpdaterResponse(boolean validResponse, LedIndication.indication indication, Command.command command, String message) {
        this.validResponse = validResponse;
        this.indication = indication;
        this.message = message;
        this.command = command;
    }

    public IndicationUpdaterResponse(boolean validResponse, LedIndication.indication indication, Command.command command) {
        this(validResponse, indication, command, "");
    }

    public IndicationUpdaterResponse(boolean validResponse, String message) {
        this(validResponse, null, null, message);
    }


    public boolean isValid() {
        return validResponse;
    }

    public LedIndication.indication getIndication() {
        return indication;
    }

    public Command.command getCommand() {
        return command;
    }

    public String getMessage() {
        return message;
    }

}
