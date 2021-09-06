package bike.gymproject.viewlibray.bean;

public class HrlineBean {
    public int maxHr;
    public int minHr;
    public int color;

    public HrlineBean(){

    }
    public HrlineBean(int maxHr, int minHr, int color){
        this.maxHr=maxHr;
        this.minHr=minHr;
        this.color=color;
    }

    @Override
    public String toString() {
        return "HrlineBean{" +
                "maxHr=" + maxHr +
                ", minHr=" + minHr +
                ", color=" + color +
                '}';
    }
}
