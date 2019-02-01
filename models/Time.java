package models;

public class Time {

    private static int day = 0;
    private static int hour = 0;
    private static int minute = 0;

    public static String getDate() {
        return getDayString() + " " + hour + ":" + minute;
    }

    public static String getDayString() {
        switch (day) {
            case 0: return "Maandag";
            case 1: return "Dinsdag";
            case 2: return "Woensdag";
            case 3: return "Donderdag";
            case 4: return "Vrijdag";
            case 5: return "Zaterdag";
            case 6: return "Zondag";
        }
        return "";
    }

    public static int getDay() {
        return day;
    }

    public static int getHour() {
        return hour;
    }

    public static int getMinute() {
        return minute;
    }

    public static void advanceTime() {
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }
    }
}
