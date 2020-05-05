package weather;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

//Class for getting weather data by getting json file by url and parsing that data in strings with informarion
public class Weather {
    //Method for getting information from json file and return parsed data
    public static String getWeather(String city) throws IOException {
        StringBuffer content = new StringBuffer();

        try {
            //Input city for getting iformation about weather in this city in url
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=f38eb9661a7d160b3f7ec48fcfcdac69\n");
            URLConnection urlCon = url.openConnection();
            //Getting stream for reading data from json
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));

            String string;

            //Reading data from json file
            while ((string = buffReader.readLine()) != null)
                content.append(string + "\n");

            buffReader.close();
        } catch (Exception e) { e.printStackTrace(); }

        //Return final result by parsing function
        return parseWeather(String.valueOf(content));
    }


    //Method for parsing data from json file
    public static String parseWeather(String output) {
        StringBuffer tempInfo = null;
        String extra = "";
        double temperature = 0;

        if (!output.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(output);
                tempInfo = new StringBuffer();

                temperature = jsonObject.getJSONObject("main").getDouble("feels_like");

                tempInfo.append("\nТемпература:" + jsonObject.getJSONObject("main").getDouble("temp"));
                tempInfo.append("\nОщущается:" + jsonObject.getJSONObject("main").getDouble("feels_like"));
                tempInfo.append("\nМаксимальная за сегодня:" + jsonObject.getJSONObject("main").getDouble("temp_max"));
                tempInfo.append("\nМинимальна за сегодня:" + jsonObject.getJSONObject("main").getDouble("temp_min"));
                tempInfo.append("\nВлажность:" + jsonObject.getJSONObject("main").getDouble("humidity") + "%");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (temperature != 0) {
            if (temperature < 10)
                extra = "\n\nСэр, извините меня за выражение, но на улице 'дубарина', надевайте Ваш любимый свитер и тёрлую куртку.";

            if (temperature > 10 && temperature < 20)
                extra = "\nСэр, на улице \"не горячо - не холодно\". Хах! (не уверен, уместно ли здесь это выражение...)";

            if (temperature > 20)
                extra = "\nСэр, уже можно и снять Вашу красивую куртку, обожаю такую погоду.";

            if (temperature > 30)
                extra = "\nСэр, я всё понимаю, но конда мы полетим в Египет расслабляться на пляже? Мне брать тапочки?";

        }

        //Return parsed variant of information from json file
        return String.valueOf(tempInfo.append("\n" + extra));
    }
}
