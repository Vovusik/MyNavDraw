package com.andrukhiv.mynavigationdrawer.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceFragmentCompat;

import com.andrukhiv.mynavigationdrawer.AppController;
import com.andrukhiv.mynavigationdrawer.BuildConfig;
import com.andrukhiv.mynavigationdrawer.Constant;
import com.andrukhiv.mynavigationdrawer.LocaleHelper;
import com.andrukhiv.mynavigationdrawer.MyBottomSheetDialogFragment;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.ThemeHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static com.andrukhiv.mynavigationdrawer.Constant.APP_PACKAGE_NAME;
import static com.andrukhiv.mynavigationdrawer.Constant.GOOGLE_PLAY_MARKET_ANDROID;
import static com.andrukhiv.mynavigationdrawer.Constant.GOOGLE_PLAY_MARKET_WEB;

public class SettingsFragment extends PreferenceFragmentCompat {

    private Intent intent;
    public static BottomSheetDialogFragment myBottomSheet;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.preferencess, rootKey);
        setHasOptionsMenu(true);


        androidx.preference.ListPreference themePreference = findPreference("theme");
        if (themePreference != null) {
            themePreference.setOnPreferenceChangeListener(
                    (preference, newValue) -> {
                        String themeOption = (String) newValue;
                        ThemeHelper.applyTheme(themeOption);
                        return true;
                    });
        }

        findPreference("rate").setOnPreferenceClickListener(preference -> {
            rate(getActivity());
            return true;
        });

        findPreference("share").setOnPreferenceClickListener(preference -> {
            share(getActivity());
            return true;
        });

        findPreference("email").setOnPreferenceClickListener(preference -> {
            email(getActivity());
            return true;
        });

        findPreference("license").setOnPreferenceClickListener(preference -> {
            license(getActivity());
            return true;
        });

        findPreference("description").setOnPreferenceClickListener(preference -> {
            information(getActivity());
            return true;
        });

        findPreference("version").setSummary("v" + BuildConfig.VERSION_NAME);
    }


    private void rate(FragmentActivity activity) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(GOOGLE_PLAY_MARKET_ANDROID + APP_PACKAGE_NAME)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(GOOGLE_PLAY_MARKET_WEB + APP_PACKAGE_NAME + "&hl")));
        }

    }

    private void share(FragmentActivity activity) {
        intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, Constant.SHARE_CONTENT);
        intent.setType("text/plain");
        startActivity(intent);
    }

    private void email(FragmentActivity activity) {
        String to = Constant.EMAIL;// Адресат повідомлення
        String subject = getString(R.string.message_subject); // Тема повідомлення
        String body = getString(R.string.message_text); // Текст повідомлення

        intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        String[] toArr = new String[]{to};
        intent.putExtra(Intent.EXTRA_EMAIL, toArr);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(intent);
    }

    private void information(FragmentActivity activity) {
        FragmentManager fm = getChildFragmentManager();
        myBottomSheet = MyBottomSheetDialogFragment.newInstance("Modal Bottom Sheet");
        myBottomSheet.show(fm, myBottomSheet.getTag());
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
        webView.setBackgroundColor(getResources().getColor(R.color.rowBackground));

        Button btn_source_licenses_close = dialog.findViewById(R.id.btn_source_licenses_close);
        btn_source_licenses_close.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

}