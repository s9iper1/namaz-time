/*
 *
 *  * (C) Copyright 2015 byteShaft Inc.
 *  *
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the GNU Lesser General Public License
 *  * (LGPL) version 2.1 which accompanies this distribution, and is available at
 *  * http://www.gnu.org/licenses/lgpl-2.1.html
 *  
 */

package com.byteshaft.namaztime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RingtoneRestoreReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Notifications notification = new Notifications(context);
        WidgetReceiver widgetReceiver = new WidgetReceiver();
        WidgetHelpers widgetHelpers = new WidgetHelpers(context);
        widgetHelpers.setRingtoneMode(WidgetGlobals.getRingtoneModeBackup());
        widgetHelpers.createToast("Phone ringer setting restored");
        notification.clearPhoneSilentNotification();
        WidgetGlobals.resetRingtoneBackup();
        widgetReceiver.removeAlarm(context);
        WidgetGlobals.setIsPhoneSilent(false);
    }
}
