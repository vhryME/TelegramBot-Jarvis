package news;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class News {
    public static String getNews() {
        String news;
        String tempInfo;
        StringBuffer result = new StringBuffer();

        try {
            URL url = new URL(" https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=e6568a1672fd47268ad9b16a6eaf5f0b");
            URLConnection urlCon = url.openConnection();
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));

            while ((tempInfo = buffReader.readLine()) != null)
                result.append(tempInfo + "\n");

            System.out.println(result);

            buffReader.close();
        } catch (Exception e) { e.printStackTrace(); }

        news = getNewsParsed(result.toString());

        return news;
    }

    public static String getNewsParsed(String output) {
        StringBuffer tempInfo = new StringBuffer();

        if (!output.isEmpty()) {
            JSONObject jsonObject = new JSONObject(output);
            JSONObject article;
            JSONArray articles = jsonObject.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++){
                article = articles.getJSONObject(i);

                tempInfo.append("\nАвтор:" + article.getString("author"));
                tempInfo.append("\nНазвание:" + article.getString("title"));
                tempInfo.append("\nОписание:" + article.getString("description"));
                tempInfo.append("\nИсточник:" + article.getString("url"));
                tempInfo.append("\nИсточник с изображением:" + article.getString("urlToImage") + "\n\n");
            }
        }

        return tempInfo.toString();
    }
}

