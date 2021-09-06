package bike.gymproject.viewlibray.chart;

public class HourMinuteData {
    public HourMinuteData() {

    }

    public HourMinuteData(int color, String value, String hourVal, String minVal, String minUnitl) {
        this.color = color;
        this.value = value;
        this.hourVal = hourVal;
        this.minVal = minVal;
        this.minUnitl = minUnitl;
    }

    public HourMinuteData(int color, String value, String hourVal, String minVal) {
        this.color = color;
        this.value = value;
        this.hourVal = hourVal;
        this.minVal = minVal;
        this.minUnitl = "";
    }

    public int color;
    public String value;
    public String hourVal;
    public String minVal;
    public String minUnitl;

}
