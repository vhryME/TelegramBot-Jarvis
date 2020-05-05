package bot;


import news.News;

import weather.Weather;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.*;


public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();

        Bot bot = new Bot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) { e.printStackTrace(); }
    }


    @Override
    public String getBotUsername() { return "MmalyjJBot"; }
    @Override
    public String getBotToken() { return "1286255929:AAFDAlfw_s0y_TECWM6i9a4_jsmngbwdeeE"; }


    public void sendMsg(Message message, String text) {
        SendMessage sMessage = new SendMessage();

        sMessage.setChatId(message.getChatId());
        sMessage.setText(text);

        try {
            execute(sMessage);
        } catch (TelegramApiException e) { e.printStackTrace(); }
    }


    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();

        if(message != null || message.hasText()) {
            if (text.equalsIgnoreCase("Как дела?") || text.equalsIgnoreCase("Как дела") || text.equalsIgnoreCase("Привет, как дела?") || text.equals("Васап") || text.equals("Чё как?")) {
                String answer;

                if (new Random().nextInt() < 0)
                    answer = "Эхх, Сэр. Я опечален: у меня появлися неизвестный мне вирус. Не бойтесь, к COVID-19 он не имеет абсолютно никакого отношения...Честно!";
                else
                    answer = "Оу, Сэр. Великолепно, я познакомился с очень фешенебельной Windows. Да, знаю, Вы мне говорили, что Linux без вирусов и вообще ей можно доверять. Но, сэр, я извиняюсь, но Вы её видели?";

                sendMsg(message, answer);

                return;

            } else if (text.equalsIgnoreCase("Погода") || text.equalsIgnoreCase("Покажи погоду") || text.equalsIgnoreCase("Погода.") || text.equalsIgnoreCase("Покажи погоду.")) {
                text = "weather";

            } else if (text.equalsIgnoreCase("Новости") || text.equalsIgnoreCase("Покажи новости") || text.equalsIgnoreCase("Новости.") || text.equalsIgnoreCase("Покажи новости."))
                text = "news";


                switch (text) {
                    case "start":
                        sendMsg(message, "Прветствую Вас, Сэр. Я к вашим услугам.");

                        break;


                    case "weather":
                        sendMsg(message, "Да, Сэр. Введите, пожалуйста город, в котором вы хотите узнать погодные условия");

                        break;


                    case "news":
                        sendMsg(message, "Сэр, это может Вас заинтересовать:");

                        sendMsg(message, News.getNews());

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


