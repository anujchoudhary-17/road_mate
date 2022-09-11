import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpeedLimit {
    public int getTrafficIncidents(double latitude, double longitude, int speedLimit){
        String address = "http://dev.virtualearth.net/REST/v1/Traffic/Incidents/";
        // Personal microsoft maps API key
        String key = "Ap1aDPqyones1nXvDTT4vqcLEgd9i8tEkARtNHdt07s0-xt6BGcHoRyWHbeOBN8e";
        // Check incidents in a square 100m by 100m centred around the current coordinates
        double delta = 50.0 / 111193.0;
        double south = latitude - delta;
        double north = latitude + delta;
        double east = longitude - delta;
        double west = longitude + delta;
        address += south + "," + west + "," + north + "," + east;
        address += "?key=" + key;
        System.out.println(address);
        try {
            URL url = new URL(address);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            String line = "";
            StringBuilder response = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line=bufferedReader.readLine())!=null){
                response.append(line);
            }
            bufferedReader.close();
            JSONObject json = new JSONObject(response.toString());
            JSONObject temp = (JSONObject) json.getJSONArray("resourceSets").get(0);
            JSONArray jsonArray = temp.getJSONArray("resources");
            System.out.println(jsonArray);
            // If there is one or more incidents within 2 delta by 2 delta area, reduce the speed limit by 10
            if (jsonArray.length() != 0){
                return speedLimit - 10;
            }
            else {
                return speedLimit;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
