package ru.job4j.tracker.store;

import ru.job4j.tracker.model.Item;

import java.util.Arrays;
import java.util.Random;

/**
 * Система сбора заявок
 */
public class MemTracker {
    /**
     * Массив для хранения заявок.
     */
    private final Item[] items = new Item[100];

    /**
     * Указатель ячейки для новой заявки.
     */
    private int position = 0;

    /**
     * Метод добавления заявки в хранилище
     *
     * @param item новая заявка
     */
    public Item add(Item item) {
        item.setId(generateId());
        items[position++] = item;
        return item;
    }

    /**
     * Метод возвращает все хранящиеся в системе заявки
     * предполагается, что в массиве поддерживается
     * состояние с отсутствием "дырок"
     *
     * @return Item[]
     */
    public Item[] findAll() {
        return Arrays.copyOf(items, position);
    }

    /**
     * Возвращает массив заявок с именем равным параметру
     *
     * @param key - имя заявки, по которому осуществляется поиск
     * @return Item[]
     */
    public Item[] findByName(String key) {
        Item[] tmp = new Item[100];
        int tmpPosition = 0;
        for (int i = 0; i < position; i++) {
            if (key.equals(items[i].getName())) {
                tmp[tmpPosition++] = items[i];
            }
        }
        return Arrays.copyOf(tmp, tmpPosition);
    }

    /**
     * Возвращает заявку с Id переданным в параметре, если
     * поиск неудачен, возвращает null
     *
     * @param id - искомый Id
     * @return Item
     */
    public Item findById(Integer id) {
        int index = indexOf(id);
        // Если индекс найден возвращаем item, иначе null
        return index != -1 ? items[index] : null;
    }

    /**
     * Заменяет заяку по id
     * @param id - идентификатор заявки
     * @param item - заменяющая заяка
     * @return boolean - результат замены. True - заявка заменена. False - замена не произведена
     */
    public boolean replace(Integer id, Item item) {
        boolean rsl = false;
        int index = indexOf(id);
        if (index != -1) {
            item.setId(id);
            items[index] = item;
            rsl = true;
        }
        return rsl;
    }

    /**
     * Удаляет заявку по id
     * @param id - идентификатор заявки
     * @return boolean - результат удаления. True - успех. False - неудача
     */
    public boolean delete(Integer id) {
        boolean rsl = false;
        int index = indexOf(id);
        if (index != -1) {
            System.arraycopy(items, index + 1, items, index, position - 1 - index);
            items[position - 1] = null;
            position--;
            rsl = true;
        }
        return rsl;
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     *
     * @return Уникальный ключ.
     */
    private Integer generateId() {
        Random rm = new Random();
        return Integer.parseInt(String.valueOf(rm.nextInt()));
    }

    /**
     * Поиск индекса заявки по id
     * @param id - идентификатор заявки
     * @return int - индекс заявки
     */
    private int indexOf(Integer id) {
        int rsl = -1;
        for (int index = 0; index < position; index++) {
            if (items[index].getId().equals(id)) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }
}
