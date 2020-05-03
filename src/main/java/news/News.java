package news;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class News {
    public static String getNews() {
        String tempInfo;
        StringBuffer result = new StringBuffer();

        try {
            URL url = new URL(" https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=7fb685746d324a21a8fb66e2739789cd");
            URLConnection urlCon = url.openConnection();
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));

            while ((tempInfo = buffReader.readLine()) != null)
                result.append(tempInfo + "\n");

            System.out.println(result);

            buffReader.close();
        } catch (Exception e) { e.printStackTrace(); }

        return parseNews(String.valueOf(result));
    }

    public static String parseNews(String output) {
        StringBuffer tempInfo = new StringBuffer();

        if (!output.isEmpty()) {
            JSONObject jsonObject = new JSONObject(output);
            JSONObject article;
            JSONArray articles = jsonObject.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++){
                article = articles.optJSONObject(i);

                tempInfo.append("\nАвтор:" + article.optString("author"));
                tempInfo.append("\nНазвание:" + article.optString("title"));
                tempInfo.append("\nОписание:" + article.optString("description"));
                tempInfo.append("\nИсточник:" + article.optString("url"));
                tempInfo.append("\nИсточник с изображением:" + article.optString("urlToImage"));
                tempInfo.append("\nОпубликовано в:" + article.optString("publishedAt"));
                tempInfo.append("\n\nСодержимое:" + article.optString("content"));
            }
        }

        return String.valueOf(tempInfo);
    }
}

