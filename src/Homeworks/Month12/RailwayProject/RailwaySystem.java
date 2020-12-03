package Homeworks.Month12.RailwayProject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

class Test {
    public static void main(String[] args) {
        RailwaySystem system = new RailwaySystem();
        system.start();
        
    }
}

public class RailwaySystem {
    TimeHandler timeHandler = new TimeHandler();



    class Handler {

    }

    static class TimeHandler {
        private final GregorianCalendar calendar;
        private final SimpleDateFormat formatter;
        private String formattedDate;

        private TimeHandler() {
            Calendar currentDate = Calendar.getInstance();
            calendar = new GregorianCalendar(
                    currentDate.get(Calendar.YEAR),
                    currentDate.get(Calendar.MONTH),
                    currentDate.get(Calendar.DAY_OF_MONTH));
            formatter = new SimpleDateFormat("HH 'ч.' dd MMMM yyyy", Locale.getDefault());
            updateFormattedDate();
        }

        private void nextHour() {
            calendar.add(Calendar.HOUR, 1);
            updateFormattedDate();
        }

        private void updateFormattedDate() {
            formattedDate = formatter.format(calendar.getTime());
        }

        private int getHour() {
            return calendar.get(Calendar.HOUR);
        }

        private int getDay() {
            return calendar.get(Calendar.MONTH);
        }

        @Override
        public String toString() {
            return formattedDate;
        }
    }
}
