/**
 * Copyright 2009 Joe LaPenna
 */

package com.joelapenna.foursquared.util;

import com.joelapenna.foursquared.Foursquared;
import com.joelapenna.foursquared.PreferenceActivity;
import com.joelapenna.foursquared.R;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Toast;

/**
 * @author Alex Volovoy (avolovoy@gmail.com) Collection of common functions
 *         which are called from the menu
 */
public class MenuUtils {
    // Common menu items
    private static final int MENU_PREFERENCES = -1;

    public static final int MENU_FEEDBACK = -2;

    private static final int MENU_GROUP_SYSTEM = 20;

    public static void SendFeedBack(Context context, Foursquared foursquared) {

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        final String[] mailto = {
            "foursquared-dev+feedback@googlegroups.com"
        };
        final String new_line = "\n";
        StringBuilder body = new StringBuilder();
        Resources res = context.getResources();
        body.append(new_line);
        body.append(new_line);
        body.append(res.getString(R.string.feedback_more));
        body.append(new_line);
        body.append(res.getString(R.string.feedback_question_how_to_reproduce));
        body.append(new_line);
        body.append(new_line);
        body.append(res.getString(R.string.feedback_question_expected_output));
        body.append(new_line);
        body.append(new_line);
        body.append(res.getString(R.string.feedback_question_additional_information));
        body.append(new_line);
        body.append(new_line);
        body.append("--------------------------------------");
        body.append(new_line);
        body.append("ver: ");
        body.append(foursquared.getVersion());
        body.append(new_line);
        body.append("user: ");
        body.append(foursquared.getUserId());
        body.append(new_line);
        body.append("p: ");
        body.append(Build.MODEL);
        body.append(new_line);
        body.append("os: ");
        body.append(Build.VERSION.RELEASE);
        body.append(new_line);
        body.append("build#: ");
        body.append(Build.DISPLAY);
        body.append(new_line);
        body.append(new_line);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.feedback_subject));
        sendIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
        sendIntent.putExtra(Intent.EXTRA_TEXT, body.toString());
        sendIntent.setType("message/rfc822");
        try {
            context.startActivity(Intent.createChooser(sendIntent, context
                    .getText(R.string.feedback_subject)));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, context.getText(R.string.feedback_error), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public static void addPreferencesToMenu(Context context, Menu menu) {
        Intent intent = new Intent(context, PreferenceActivity.class);
        menu.add(MENU_GROUP_SYSTEM, MENU_PREFERENCES, Menu.CATEGORY_SECONDARY,
                R.string.preferences_label).setIcon(android.R.drawable.ic_menu_preferences)
                .setIntent(intent);
    }

    public static void addSendFeedbackToMenu(final Foursquared foursquared,
            final Activity activity, final Menu menu) {
        menu
                .add(MENU_GROUP_SYSTEM, MENU_FEEDBACK, Menu.CATEGORY_SECONDARY,
                        R.string.feedback_label).setIcon(android.R.drawable.ic_menu_send)
                .setOnMenuItemClickListener(new OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        MenuUtils.SendFeedBack(activity, foursquared);
                        return true;
                    }
                });
    }
}
