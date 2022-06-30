package moa.moamore.domain;


public enum Budget_period {

    weeek(7), two_week(14), month(30);

    private final int period;

    Budget_period(int period) {
        this.period = period;
    }

    public int intValue() {
        return period;
    }

    public static Budget_period valueOf(int period) {
        switch (period) {
            case 7:
                return weeek;
            case 14:
                return two_week;
            case 30:
                return month;
            default:
                throw new AssertionError("Unknown value: " + period);
        }
    }

}
