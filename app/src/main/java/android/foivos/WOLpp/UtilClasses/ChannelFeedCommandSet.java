package android.foivos.WOLpp.UtilClasses;

import android.foivos.WOLpp.enums.Command;

import com.angryelectron.thingspeak.Channel;

/**
 * Created by foivos on 27-Dec-16.
 */

public class ChannelFeedCommandSet extends ChannelFeedPair {
    private Command.command command;


    public ChannelFeedCommandSet(Channel channel, int feedNo, Command.command command) {
        super(channel, feedNo);
        this.command = command;
    }

    public Command.command getCommand() {
        return command;
    }
}
