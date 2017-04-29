package com.foivos.WOLpp.android.foivos.WOLpp.AsyncTasks;


import android.os.AsyncTask;

import com.angryelectron.thingspeak.Channel;
import com.angryelectron.thingspeak.Entry;
import com.angryelectron.thingspeak.ThingSpeakException;
import com.foivos.WOLpp.MainActivity;
import com.foivos.WOLpp.R;
import com.foivos.WOLpp.UtilClasses.ActionRequesterResponse;
import com.foivos.WOLpp.UtilClasses.ChannelFeedCommandSet;
import com.foivos.WOLpp.enums.Command;

import java.io.IOException;


/**
 * Created by foivos on 08-Dec-16.
 */

public class ActionRequester extends AsyncTask<ChannelFeedCommandSet, Void, ActionRequesterResponse> {

    private MainActivity mActivity;


    public ActionRequester(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mActivity.UIinteractionSetEnabled(false);
        mActivity.updateMainTextView("sending request...");
    }

    @Override
    protected ActionRequesterResponse doInBackground(ChannelFeedCommandSet... pairs) {

        Channel channel = pairs[0].getChannel();
        int feedNo = pairs[0].getFeedNo();
        Command.command command = pairs[0].getCommand();
        ActionRequesterResponse response;

        Entry entry = new Entry();
        entry.setField(feedNo, Command.command2int(command).toString());
        try {
            channel.update(entry);
            response = new ActionRequesterResponse(true);
        } catch (IOException e) {
            e.printStackTrace();
            response = new ActionRequesterResponse(false, mActivity.getString(R.string.cannot_connect));
        } catch (ThingSpeakException e) {
            e.printStackTrace();
            response = new ActionRequesterResponse(false, "too fast, please wait 10 seconds.");
        }

        return response;
    }


    @Override
    protected void onPostExecute(ActionRequesterResponse response) {
        super.onPostExecute(response);
        mActivity.UIinteractionSetEnabled(true);
        mActivity.updateMainTextView(mActivity.getString(R.string.ready));

        if (response.isSuccesful()) {
            mActivity.updateMainTextView(mActivity.getString(R.string.ready));
        } else {

            mActivity.notifyUser(response.getMessage());
        }

    }
}
