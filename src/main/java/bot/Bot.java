package bot;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;


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
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    //Meehod for receiving message
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();

        if(message != null || message.hasText()) {
            switch (text) {
                case "/weather":
                    sendMsg(message, "Да, Сэр. Сейчас посмотрю, какова погода в вашем регионе...");
                    //...
                    break;

                case "/help":
                    sendMsg(message, "Конечно, Сэр." +
                            "\n1)Кнопка 'Погода' показывает погоду на данный момент в вашем регионе. " +
                            "\n2)Кнопка 'Дни рождения' показывает у кого из ваших друзей в ближайшую неделю будет день рождения." +
                            "\n3)Кнопка 'Новости' показывает текущие новости.");
                    break;

                case "/news":
                    sendMsg(message, "Сэр, это то, что может Вас заинтересовать:");
                    //...
                    break;

                default:
                    sendMsg(message, "Сэр, Я затрудняюсь Вам ответить.");

            }
        }
    }
}
