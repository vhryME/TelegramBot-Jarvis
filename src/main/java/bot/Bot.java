package bot;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import weather.Weather;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;


public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();

        Bot bot = new Bot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) { e.printStackTrace(); }
    }


    //Return name of Bot and his token
    @Override
    public String getBotUsername() { return "MmalyjJBot"; }
    @Override
    public String getBotToken() { return "1286255929:AAFDAlfw_s0y_TECWM6i9a4_jsmngbwdeeE"; }


    //Method for update your message and that sending
    //Params: chatId - ID of chat, string - your message
    public void sendMsg(Message message, String text) {
        SendMessage sMessage = new SendMessage();

        sMessage.setChatId(message.getChatId());
        sMessage.setText(text);

        try {
            execute(sMessage);
        } catch (TelegramApiException e) { e.printStackTrace(); }
    }


    //Meehod for receiving message
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();

        if(message != null || message.hasText()) {
            switch (text) {
                case "/start":
                    sendMsg(message, "Здравствуйте, Сэр. Меня зовут Джарвис. Я Ваш будущий помощник. Чем могу помочь?");

                case "/help":
                    sendMsg(message, "Конечно, Сэр." +
                            "\n\n1)Кнопка 'Погода' показывает погоду на данный момент в вашем регионе." +
                            "\n\n2)Кнопка 'Дни рождения' показывает у кого из ваших друзей в ближайшую неделю будет день рождения." +
                            "\n\n3)Кнопка 'Новости' показывает текущие новости.");
                    break;

                case "/weather":
                    sendMsg(message, "Да, Сэр. Введите, пожалуйста город, в котором вы хотите узнать погодные условия");
                    break;

                case "/news":
                    sendMsg(message, "Сэр, это то, что может Вас заинтересовать:");

                    //...

                    break;

                case "/covid":

                    //...

                    break;

                default:
                    String location = update.getMessage().getText();
                    String output = null;
                    StringBuffer tempInfo = null;
                    String weather;
                    double temperature = 0;

                    try {
                        output = Weather.getWeather(location);
                    } catch (IOException e) { e.printStackTrace(); }

                    if(!output.isEmpty()) {
                        try {
                            JSONObject jsonObject = new JSONObject(output);
                            tempInfo = new StringBuffer();

                            temperature = jsonObject.getJSONObject("main").getDouble("temp");

                            tempInfo.append("\nТемпература:" + jsonObject.getJSONObject("main").getDouble("temp"));
                            tempInfo.append("\nОщущается:" + jsonObject.getJSONObject("main").getDouble("feels_like"));
                            tempInfo.append("\nМаксимальная за сегодня:" + jsonObject.getJSONObject("main").getDouble("temp_max"));
                            tempInfo.append("\nМинимальна за сегодня:" + jsonObject.getJSONObject("main").getDouble("temp_min"));
                            tempInfo.append("\nВлажность:" + jsonObject.getJSONObject("main").getDouble("humidity") + "%");
                        } catch (JSONException e) { e.printStackTrace(); }
                    }

                    weather = String.valueOf(tempInfo);

                    sendMsg(message, weather);

                    if(temperature < 10)
                        sendMsg(message, "\nСэр, извините меня за выражение, но на улице 'дубарина', надевайте Ваш любимый свитер и тёрлую куртку.");

                    if(temperature > 10 || temperature < 20)
                        sendMsg(message, "\nСэр, на улице 'не горячо - не холодно', не уверен, уместно ли здесь это выражение...");

                    if(temperature > 20)
                        sendMsg(message, "\nСэр, уже можно и снять Вашу красивую куртку, обожаю такую погоду.");

                    if(temperature > 30)
                        sendMsg(message, "\nСэр, я всё понимаю, но конда мы полетим в Египет расслабляться на пляже? Мне брать тапочки?");

                    break;
            }
        }
    }
}
