package android.foivos.WOLpp.android.foivos.WOLpp.AsyncTasks;


import android.foivos.WOLpp.MainActivity;
import android.foivos.WOLpp.R;
import android.foivos.WOLpp.UtilClasses.ChannelFeedPair;
import android.foivos.WOLpp.UtilClasses.IndicationUpdaterResponse;
import android.foivos.WOLpp.enums.Command;
import android.foivos.WOLpp.enums.LedIndication;
import android.os.AsyncTask;

import com.angryelectron.thingspeak.Channel;
import com.angryelectron.thingspeak.Entry;
import com.angryelectron.thingspeak.ThingSpeakException;

/**
 * Created by foivos on 08-Dec-16.
 */

public class IndicationUpdater extends AsyncTask<ChannelFeedPair, Void, IndicationUpdaterResponse> {

    private MainActivity mActivity;


    public IndicationUpdater(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mActivity.UIinteractionSetEnabled(false);
        mActivity.updateMainTextView("updating...");
    }

    @Override
    protected IndicationUpdaterResponse doInBackground(ChannelFeedPair... pairs) {

        Channel channel = pairs[0].getChannel();
        int feedNo = pairs[0].getFeedNo();


        LedIndication.indication indication;
        Command.command command;
        try {
            Entry entry = channel.getLastFieldEntry(feedNo);
            String indicationAsString = entry.getField(feedNo).toString();
            int indicationAsInt = Integer.parseInt(indicationAsString);
            indication = LedIndication.int2indication(indicationAsInt);

            entry = channel.getLastFieldEntry(feedNo - 1); //TODO fix this hardcoded feed No
            String commandAsString = entry.getField(feedNo - 1).toString();
            int commandAsInt = Integer.parseInt(commandAsString);
            command = Command.int2command(commandAsInt);

            return new IndicationUpdaterResponse(true, indication, command);
        } catch (NullPointerException | ThingSpeakException e) {
            e.printStackTrace();
            return new IndicationUpdaterResponse(false, mActivity.getString(R.string.cannot_connect) + " : " + e.getMessage());
        }


    }


    @Override
    protected void onPostExecute(IndicationUpdaterResponse response) {
        super.onPostExecute(response);
        mActivity.UIinteractionSetEnabled(true);
        mActivity.updateMainTextView(mActivity.getString(R.string.ready));

        if (response.isValid()) {
            mActivity.updateLedStatus(response.getIndication());
            mActivity.updateCommandStatus(response.getCommand());
        } else {
            //mActivity.updateLedStatus(LedIndication.indication.UNKNOWN);
            mActivity.notifyUser(response.getMessage());
        }

    }
}
