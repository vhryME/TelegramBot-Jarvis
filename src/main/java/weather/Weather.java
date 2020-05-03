package weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Weather {
    public static String getWeather(String city) throws IOException {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=f38eb9661a7d160b3f7ec48fcfcdac69\n");
            URLConnection urlCon = url.openConnection();
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));

            String string;

            while((string = buffReader.readLine()) != null)
                content.append(string + "\n");

            System.out.println(content);

            buffReader.close();
        } catch (Exception e) { System.out.println("Сэр, такого города не существует."); }

        return content.toString();
    }
}
