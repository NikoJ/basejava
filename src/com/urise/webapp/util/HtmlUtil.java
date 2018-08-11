package com.urise.webapp.util;

import com.urise.webapp.model.Role;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Role role) {
        return DateUtil.format(role.getDateStart()) + " - " + DateUtil.format(role.getDateEnd());
    }
}
