package com.andrukhiv.mynavigationdrawer.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.RingtonePreference;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.andrukhiv.mynavigationdrawer.AppController;
import com.andrukhiv.mynavigationdrawer.BuildConfig;
import com.andrukhiv.mynavigationdrawer.LocaleHelper;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.ThemeHelper;

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static boolean onBackButtonRecreateView = false;

//    @Override
//    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//
//        setPreferencesFromResource(R.xml.preferences, rootKey);
//        setHasOptionsMenu(true);
//
//
//        androidx.preference.ListPreference themePreference = findPreference("theme");
//        if (themePreference != null) {
//            themePreference.setOnPreferenceChangeListener(
//                    (preference, newValue) -> {
//                        String themeOption = (String) newValue;
//                        ThemeHelper.applyTheme(themeOption);
//                        return true;
//                    });
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//        if (null != mActionBar) {
//            mActionBar.setTitle(R.string.action_settings);
//        }

        // Загрузите настройки из XML-ресурса
        addPreferencesFromResource(R.xml.preferences);
        setHasOptionsMenu(true);

        int currentTheme = AppCompatDelegate.getDefaultNightMode();

        findPreference("theme").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                AppCompatDelegate.setDefaultNightMode(AppController.getNightModeInt((String) newValue));
                getActivity().recreate();
                return true;
            }
        });

        findPreference("language").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                updateView((String) newValue);
                return true;
            }
        });

        findPreference("notifications_new_message_ringtone").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();

                if (preference instanceof ListPreference) {
                    // Для предпочтений списка найдите правильное отображаемое значение
                    // в списке «записи» предпочтительности.
                    ListPreference listPreference = (ListPreference) preference;
                    int index = listPreference.findIndexOfValue(stringValue);

                    // Установите сводку, чтобы отобразить новое значение.
                    preference.setSummary(
                            index >= 0
                                    ? listPreference.getEntries()[index]
                                    : null);
                } else if (preference instanceof RingtonePreference) {
                    // Для настройки мелодии звонка найдите правильное отображаемое значение
                    // с помощью RingtoneManager.
                    if (TextUtils.isEmpty(stringValue)) {
                        // Пустые значения соответствуют «тихую» (без мелодии звонка).
                        preference.setSummary(R.string.pref_ringtone_silent);
                    } else {
                        Ringtone ringtone = RingtoneManager.getRingtone(
                                preference.getContext(), Uri.parse(stringValue));
                        if (ringtone == null) {
                            // Удалите резюме, если произошла ошибка поиска.
                            preference.setSummary(null);
                        } else {
                            // Задайте сводку, чтобы отобразить новое отображаемое имя мелодии звонка.
                            String name = ringtone.getTitle(preference.getContext());
                            preference.setSummary(name);
                        }
                    }
                } else {
                    // Для всех других настроек установите сводку в простое строковое
                    // представление значения.
                    preference.setSummary(stringValue);
                }
                return true;
            }

        });

        findPreference("license").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                license(getActivity());
                return true;
            }
        });

        findPreference("version").setSummary("v" + BuildConfig.VERSION_NAME);

        findPreference("description").setSummary(R.string.summary_description);
    }


    private void updateView(String languageCode) {
        onBackButtonRecreateView = true;
        LocaleHelper.setLocale(getActivity(), languageCode);
        setPreferenceScreen(null);
        addPreferencesFromResource(R.xml.preferences);
        //mActionBar.setTitle(R.string.action_settings);
    }


    public void license(Context context) {

        final Dialog dialog = new Dialog(context, R.style.DialogFullscreenWithTitle);
        dialog.setTitle(getString(R.string.title_licenses));
        dialog.setContentView(R.layout.licenses_dialog);

        final WebView webView = dialog.findViewById(R.id.web_source_licenses);

        int themeMode = AppCompatDelegate.getDefaultNightMode();
        if (themeMode == AppCompatDelegate.MODE_NIGHT_YES) {
            webView.loadUrl("file:///android_asset/licenses_night.html");
        } else {
            webView.loadUrl("file:///android_asset/licenses_day.html");
        }

        // встановлюю колір заднього фону діалогу ліцензій
        webView.getSettings();
        webView.setBackgroundColor(getResources().getColor(R.color.windowBackground));

        Button btn_source_licenses_close = dialog.findViewById(R.id.btn_source_licenses_close);
        btn_source_licenses_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        SharedPreferences.OnSharedPreferenceChangeListener sharedPrefListener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                        if ("my_switch_preference_key".equals(key)) {
                            boolean newValue = prefs.getBoolean(key, false);
                        }
                    }
                };
    }
}