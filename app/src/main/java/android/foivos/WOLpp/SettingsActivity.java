package android.foivos.WOLpp;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by foivos on 26-Dec-16.
 */

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }


}
