package com.foivos.WOLpp.UtilClasses;

import com.angryelectron.thingspeak.Channel;

/**
 * Created by foivos on 27-Dec-16.
 */

public class ChannelFeedPair {
    private Channel channel;
    private int feedNo;

    public ChannelFeedPair(Channel channel, int feedNo) {
        this.channel = channel;
        this.feedNo = feedNo;
    }

    public Channel getChannel() {
        return channel;
    }

    public int getFeedNo() {
        return feedNo;
    }
}
