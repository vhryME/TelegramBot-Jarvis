package bot;


import news.News;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weather.Weather;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;


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
            if(text.equals("Как дела?") || text.equals("как дела?") || text.equals("Как дела") ||text.equals("как дела") || text.equals("Привет, как дела?") || text.equals("привет, как дела?") || text.equals("Привет как дела?") || text.equals("првет как дела?") || text.equals("Васап")) {
                String answer;

                if (new Random().nextInt() < 0)
                    answer = "Эхх, Сэр. Я опечален: у меня появлися неизвестный мне вирус. Не бойтесь, к COVID-19 он не имеет абсолютно никакого отношения...Честно!";
                else
                    answer = "Оу, Сэр. Великолепно, я познакомился с очень фешенебельной Windows. Да, знаю, Вы мне говорили, что Linux без вирусов и вообще ей можно доверять. Но, сэр, я извиняюсь, но Вы её видели?";

                sendMsg(message, answer);

                return;

            } else {

                switch (text) {
                    case "/start":
                        sendMsg(message, "Прветствую Вас, Сэр. Я к вашим услугам.");
                        break;


                    case "/weather":
                        sendMsg(message, "Да, Сэр. Введите, пожалуйста город, в котором вы хотите узнать погодные условия");

                        break;


                    case "/news":
                        sendMsg(message, "Сэр, это может Вас заинтересовать:");

                        sendMsg(message, News.getNews());

                        break;


                    case "/covid":

                        //...

                        break;


                    default:
                        String location = update.getMessage().getText();

                        sendMsg(message, "Итак, Сэр. В городе " + location + " вот такие погодные условия:");

                        try {
                            sendMsg(message, Weather.getWeather(location));
                        } catch (Exception e) { e.printStackTrace(); }

                        break;
                }
            }

        }
    }
}

