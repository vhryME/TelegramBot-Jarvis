package news;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


//Class for geting news data by getting json file by url and parsing that data in strings with informarion
public class News {
    //Method for getting information from json file and return parsed data
    public static String getNews() {
        String news;
        String tempInfo;
        StringBuffer result = new StringBuffer();

        try {
            URL url = new URL(" http://newsapi.org/v2/top-headlines?country=ru&apiKey=e6568a1672fd47268ad9b16a6eaf5f0b");
            URLConnection urlCon = url.openConnection();
            //Getting stream for reading data from json
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));

            //Reading data from json file
            while ((tempInfo = buffReader.readLine()) != null)
                result.append(tempInfo + "\n");

            buffReader.close();
        } catch (Exception e) { e.printStackTrace(); }

        //Return final result by parsing function
        return getNewsParsed(result.toString());
    }


    //Method for parsing data from json file
    public static String getNewsParsed(String output) {
        StringBuffer tempInfo = new StringBuffer();

        if (!output.isEmpty()) {
            JSONObject jsonObject = new JSONObject(output);
            JSONObject article;
            JSONArray articles = jsonObject.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++){
                article = articles.getJSONObject(i);

                tempInfo.append("\nОпубликовано:" + article.getString("publishedAt"));
                tempInfo.append("\nЗаголовок:" + article.getString("title"));
                tempInfo.append("\nОписание:" + article.getString("description"));
                tempInfo.append("\nИсточник:" + article.getString("url"));
                tempInfo.append("\nИсточник с изображением:" + article.getString("urlToImage") + "\n\n");
            }
        }

        //Return parsed variant of information from json file
        return tempInfo.toString();
    }
}

