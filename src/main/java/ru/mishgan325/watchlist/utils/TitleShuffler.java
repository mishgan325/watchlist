package ru.mishgan325.watchlist.utils;

import ru.mishgan325.watchlist.entities.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TitleShuffler {
    public static Title getRandomTitle(List<Title> titles) {
        Random random = new Random();

        if (titles.isEmpty()) {
            return null;
        }
        // Сумма всех приоритетов
        int totalPriority = titles.stream().mapToInt(Title::getPriority).sum();
        // Случайное число в диапазоне от 0 до суммы приоритетов
        int randomNumber = random.nextInt(totalPriority);
        // Находим тайтл, у которого кумулятивная сумма приоритетов будет больше или равна randomNumber
        int cumulativePriority = 0;
        for (Title title : titles) {
            cumulativePriority += title.getPriority();
            if (cumulativePriority >= randomNumber) {
                return title;
            }
        }
        // Если не найдено, что не должно произойти, возвращаем последний элемент
        return titles.get(titles.size() - 1);
    }

//    public void adjustPriority(Title title, boolean watchNow) { TODO
//        // Пример логики изменения приоритета в зависимости от выбора пользователя
//        if (watchNow) {
//            // Если пользователь хочет посмотреть сейчас, можно увеличить приоритет
//            title.setPriority(title.getPriority() + 1);
//        } else {
//            // Если пользователь откладывает на потом, можно уменьшить приоритет или оставить без изменений
//            // В данном примере просто уменьшим приоритет на единицу
//            title.setPriority(title.getPriority() - 1);
//        }
//    }
}
