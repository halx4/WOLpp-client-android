package com.foivos.WOLpp.UtilClasses;

import com.angryelectron.thingspeak.Channel;
import com.foivos.WOLpp.enums.Command;

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
