package com.example.necoarctamagotchi.utils;

import java.util.Locale;

public class MoneyCountTextFormatter {
    public static String formatMoneyText(int money) {
        if (money < 1000) {
            return String.valueOf(money);
        } else if (money < 1_000_000) {
            return String.format(Locale.US, "%.2fk", money / 1000.0);
        } else if (money < 1_000_000_000) {
            return String.format(Locale.US,"%.2fm", money / 1_000_000.0);
        } else {
            return String.format(Locale.US, "%.2fb", money / 1_000_000_000.0);
        }
    }
}
