package android.foivos.WOLpp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.foivos.WOLpp.UtilClasses.ChannelFeedCommandSet;
import android.foivos.WOLpp.UtilClasses.ChannelFeedPair;
import android.foivos.WOLpp.android.foivos.WOLpp.AsyncTasks.ActionRequester;
import android.foivos.WOLpp.android.foivos.WOLpp.AsyncTasks.IndicationUpdater;
import android.foivos.WOLpp.enums.Command;
import android.foivos.WOLpp.enums.LedIndication;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.angryelectron.thingspeak.Channel;

public class MainActivity extends AppCompatActivity {
    private final int commandFeedNo = 1;
    private final int ledFeedNo = 2;

    private SharedPreferences sp;
    private TextView mainTextview, statusTextview, statusLabelTextview, commandTextview, commandLabelTextview;
    private Button powerShortButton, powerLongButton, resetButton, updateButton, exitButton, clearCommandButton;
    private Channel thingspeakChannel;

    private static final int SETTINGS_INFO = 1;
    private static final String PREFERENCESTRINGNOTEXIST = "__PREFERENCESTRINGNOTEXIST__";

    //#########################################################

    public void UIinteractionSetEnabled(boolean enable) {
        powerShortButton.setEnabled(enable);
        powerLongButton.setEnabled(enable);
        resetButton.setEnabled(enable);
        updateButton.setEnabled(enable);
        clearCommandButton.setEnabled(enable);
    }

    public void updateMainTextView(String message) {
        mainTextview.setText(message);
    }

    public void updateLedStatus(LedIndication.indication indication) {
        statusTextview.setText(indication.toString());
        switch (indication) {
            case OFF:
                statusTextview.setBackgroundColor(getResources().getColor(R.color.status_off));
                statusLabelTextview.setBackgroundColor(getResources().getColor(R.color.status_off));
                break;
            case ON:
                statusTextview.setBackgroundColor(getResources().getColor(R.color.status_on));
                statusLabelTextview.setBackgroundColor(getResources().getColor(R.color.status_on));
                break;
            case UNKNOWN:
                statusTextview.setBackgroundColor(getResources().getColor(R.color.status_unknown));
                statusLabelTextview.setBackgroundColor(getResources().getColor(R.color.status_unknown));
                break;
        }
    }

    public void updateCommandStatus(Command.command command) {
        commandTextview.setText(command.toString().toLowerCase() + " (" + Command.command2int(command) + ")");
    }

    public void notifyUser(String notification) {
        Toast.makeText(getApplicationContext(), notification, Toast.LENGTH_SHORT).show();
    }

    private void printSettings() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        System.out.println("#######################");
        System.out.println(getString(R.string.channel_id_preference) + " = " + sp.getString(getString(R.string.channel_id_preference), PREFERENCESTRINGNOTEXIST));
        System.out.println(getString(R.string.write_api_key_preference) + " = " + sp.getString(getString(R.string.write_api_key_preference), PREFERENCESTRINGNOTEXIST));
        System.out.println(getString(R.string.read_api_key_preference) + " = " + sp.getString(getString(R.string.read_api_key_preference), PREFERENCESTRINGNOTEXIST));
        System.out.println(getString(R.string.is_publicly_readable_preference) + " = " + sp.getBoolean(getString(R.string.is_publicly_readable_preference), true));
        System.out.println("#######################");

    }


    private void updateChannelVar() {
        notifyUser("channel var refreshed");
        try {
            if (getIsPubliclyReadablePreference()) {
                thingspeakChannel = new Channel(Integer.parseInt(getChannelIDPreference()), getWriteAPIKeyPreference());
            } else
                thingspeakChannel = new Channel(Integer.parseInt(getChannelIDPreference()), getWriteAPIKeyPreference(), getReadAPIKeyPreference());
        } catch (NumberFormatException e) {
            this.thingspeakChannel = new Channel(0);
            notifyUser("error: channel ID pref: cannot parse int.");
        }
    }

    //#########################################################

    private String getChannelIDPreference() {
        return sp.getString(getString(R.string.channel_id_preference), PREFERENCESTRINGNOTEXIST);
    }

    private String getReadAPIKeyPreference() {
        return sp.getString(getString(R.string.read_api_key_preference), PREFERENCESTRINGNOTEXIST);
    }

    private String getWriteAPIKeyPreference() {
        return sp.getString(getString(R.string.write_api_key_preference), PREFERENCESTRINGNOTEXIST);
    }

    private boolean getIsPubliclyReadablePreference() {
        return sp.getBoolean(getString(R.string.is_publicly_readable_preference), true);
    }

    //#########################################################
    private void attemptUpdate() {
        new IndicationUpdater(this).execute(new ChannelFeedPair(thingspeakChannel, ledFeedNo));
        mainTextview.setText(R.string.sending_request);
    }

    private void attemptClearCommand() {
        new ActionRequester(this).execute(new ChannelFeedCommandSet(thingspeakChannel, commandFeedNo, Command.command.IDLE));
    }

    private void attemptReset() {
        new ActionRequester(this).execute(new ChannelFeedCommandSet(thingspeakChannel, commandFeedNo, Command.command.RST_MOMENTARY_PUSH));

    }

    private void attemptPowerLong() {
        new ActionRequester(this).execute(new ChannelFeedCommandSet(thingspeakChannel, commandFeedNo, Command.command.PWR_LONG_PUSH));

    }

    private void attemptPowerShort() {
        new ActionRequester(this).execute(new ChannelFeedCommandSet(thingspeakChannel, commandFeedNo, Command.command.PWR_MOMENTARY_PUSH));
    }

    public void exitClicked(View view) {
        finish();
    }

    //#########################################################
    public void powerShortClicked(View view) {
        attemptPowerShort();
    }

    public void powerLongClicked(View view) {
        attemptPowerLong();
    }

    public void resetClicked(View view) {
        attemptReset();
    }

    public void clearCommandClicked(View view) {
        attemptClearCommand();
    }

    public void updateClicked(View view) {
        attemptUpdate();
    }

    //##########################################################

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainTextview = (TextView) findViewById(R.id.main_textView);
        statusTextview = (TextView) findViewById(R.id.status_textView);
        statusLabelTextview = (TextView) findViewById(R.id.status_label_textView);
        commandTextview = (TextView) findViewById(R.id.command_textView);
        commandLabelTextview = (TextView) findViewById(R.id.command_label_textView);

        powerShortButton = (Button) findViewById(R.id.power_short_button);
        powerLongButton = (Button) findViewById(R.id.power_long_button);
        resetButton = (Button) findViewById(R.id.reset_button);
        updateButton = (Button) findViewById(R.id.update_button);
        exitButton = (Button) findViewById(R.id.exit_button);
        clearCommandButton = (Button) findViewById(R.id.clear_command_button);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        if (getChannelIDPreference().equals(PREFERENCESTRINGNOTEXIST)) {//preferences do not exist. force user to settings menu
            notifyUser("please make initial setup!");
            Intent intentPreferences = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivityForResult(intentPreferences, SETTINGS_INFO);
        } else updateChannelVar();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                System.out.println("SETTINGS PUSHED");
                Intent intentPreferences = new Intent(getApplicationContext(), SettingsActivity.class);

                startActivityForResult(intentPreferences, SETTINGS_INFO);
                return true;
            case R.id.action_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SETTINGS_INFO) {// returned from settings

            updateChannelVar(); //settings updated. refresh channel
        }


    }

}
