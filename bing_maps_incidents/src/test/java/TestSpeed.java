public class TestSpeed {
    public static void main(String[] args) {
        SpeedLimit sl = new SpeedLimit();
        int res = sl.getTrafficIncidents(43.725435, -79.330538, 50);
        System.out.println(res);
    }
}
