/*
 * Copyright 2013-2016 John Persano
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.leebameyang.toasthelper.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

public class AccessibilityUtil {

    @SuppressWarnings("UnusedReturnValue")
    public static boolean sendAccessibilityEvent(View view) {

        final AccessibilityManager manager = (AccessibilityManager)
                view.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);

        if (!manager.isEnabled()) return false;

        final AccessibilityEvent event = AccessibilityEvent
                .obtain(AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED);
        event.setClassName(view.getClass().getName());
        event.setPackageName(view.getContext().getPackageName());

        view.dispatchPopulateAccessibilityEvent(event);
        manager.sendAccessibilityEvent(event);

        return true;
    }
}