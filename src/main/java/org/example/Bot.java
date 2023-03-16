package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {
    Service service = new Service();

    @Override
    public String getBotUsername() {
        return "BotBanderoGusGoitBot";
    }

    @Override
    public String getBotToken() {
        return "6209800573:AAE4ocU2hyFSE0arjIrM4BTrbDfiXk1tuz8";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = service.getChatId(update);

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            sendImage("level-1", chatId);

            SendMessage message = service.createMessage("Ґа-ґа-ґа!\n" +
                    "Вітаємо у боті біолабораторії «Батько наш Бандера».\n" +
                    "\n" +
                    "Ти отримуєш гусака №71\n" +
                    "\n" +
                    "Цей бот ми створили для того, щоб твій гусак прокачався з рівня звичайної свійської худоби до рівня біологічної зброї, здатної нищити ворога. \n" +
                    "\n" +
                    "Щоб звичайний гусак перетворився на бандерогусака, тобі необхідно:\n" +
                    "✔️виконувати завдання\n" +
                    "✔️переходити на наступні рівні\n" +
                    "✔️заробити достатню кількість монет, щоб придбати Джавеліну і зробити свєрхтра-та-та.\n" +
                    "\n" +
                    "*Гусак звичайний.* Стартовий рівень.\n" +
                    "Бонус: 5 монет.\n" +
                    "Обери завдання, щоб перейти на наступний рівень");
            message.setChatId(chatId);

            List<String> buttons = Arrays.asList("Сплести маскувальну сітку (+15 монет)",
                    "Зібрати кошти патріотичними піснями (+15 монет)",
                    "Вступити в Міністерство Мемів України (+15 монет)",
                    "Запустити волонтерську акцію (+15 монет)",
                    "Вступити до лав тероборони (+15 монет)");

            buttons = service.getRandom3(buttons);

            service.attachButtons(message, Map.of(
                    buttons.get(0), "level_1_task",
                    buttons.get(1), "level_1_task",
                    buttons.get(2), "level_1_task"));

            sendApiMethodAsync(message);
        }
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("level_1_task") && service.getLevel(chatId) == 1) {
                service.setLevel(chatId, 2);
                sendImage("level-2", chatId);

                SendMessage message = service.createMessage("*Вітаємо на другому рівні! Твій гусак - біогусак.*\n" +
                        "Баланс: 20 монет. \n" +
                        "Обери завдання, щоб перейти на наступний рівень");
                message.setChatId(chatId);

                List<String> buttons = Arrays.asList(
                        "Зібрати комарів для нової біологічної зброї (+15 монет)",
                        "Пройти курс молодого бійця (+15 монет)",
                        "Задонатити на ЗСУ (+15 монет)",
                        "Збити дрона банкою огірків (+15 монет)",
                        "Зробити запаси коктейлів Молотова (+15 монет)");

                buttons = service.getRandom3(buttons);

                service.attachButtons(message, Map.of(
                        buttons.get(0), "level_2_task",
                        buttons.get(1), "level_2_task",
                        buttons.get(2), "level_2_task"
                ));

                sendApiMethodAsync(message);
            }

            if (update.getCallbackQuery().getData().equals("level_2_task") && service.getLevel(chatId) == 2) {
                service.setLevel(chatId, 3);
                sendImage("level-3", chatId);

                SendMessage message = service.createMessage("*Вітаємо на третьому рівні! Твій гусак - бандеростажер.*\n" +
                        "Баланс: 35 монет. \n" +
                        "Обери завдання, щоб перейти на наступний рівень");
                message.setChatId(chatId);

                List<String> buttons = Arrays.asList(
                        "Злітати на тестовий рейд по чотирьох позиціях (+15 монет)",
                        "Відвезти гуманітарку на передок (+15 монет)",
                        "Знайти зрадника та здати в СБУ (+15 монет)",
                        "Навести арту на орків (+15 монет)",
                        "Притягнути танк трактором (+15 монет)");

                buttons = service.getRandom3(buttons);

                service.attachButtons(message, Map.of(
                        buttons.get(0), "level_3_task",
                        buttons.get(1), "level_3_task",
                        buttons.get(2), "level_3_task"
                ));
                sendApiMethodAsync(message);
            }


            if (update.getCallbackQuery().getData().equals("level_3_task") && service.getLevel(chatId) == 3) {
                service.setLevel(chatId, 4);
                sendImage("level-4", chatId);

                SendMessage message = service.createMessage("*Вітаємо на останньому рівні! Твій гусак - готова біологічна зброя - бандерогусак.*\n" +
                        "Баланс: 50 монет. \n" +
                        "Тепер ти можеш придбати Джавелін і глушити чмонь");
                message.setChatId(chatId);

                service.attachButtons(message, Map.of(
                        "Купити Джавелін (50 монет)", "level_4_task"
                ));
                sendApiMethodAsync(message);
            }
            if (update.getCallbackQuery().getData().equals("level_4_task") && service.getLevel(chatId) == 4) {
                service.setLevel(chatId, 5);

                SendMessage message = service.createMessage("*Джавелін твій. Повний вперед!*");
                message.setChatId(chatId);
                sendImage("final", chatId);

                sendApiMethodAsync(message);
            }
        }
    }

    public void sendImage(String name, Long chatId) {
        SendAnimation animation = new SendAnimation();

        InputFile inputFile = new InputFile();
        inputFile.setMedia(new File("images/" + name + ".gif"));

        animation.setAnimation(inputFile);
        animation.setChatId(chatId);
        executeAsync(animation);
    }
}
