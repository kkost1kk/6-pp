import java.util.ListResourceBundle;

class Messages_ru_RU extends ListResourceBundle {
    public Object[][] getContents() {
        return new Object[][]{
                {"title", "Награждение победителей олимпиады"},
                {"congratulator", "Выберите поздравителя:"},
                {"dean", "Декан (50)"},
                {"rector", "Ректор (100)"},
                {"sponsor", "Спонсор (80)"},
                {"gift", "Выберите подарки:"},
                {"concert", "Нужен ли концерт?"},
                {"yes", "Да"},
                {"no", "Нет"},
                {"client", "Постоянный клиент (10%):"},
                {"locale", "Локализация:"},
                {"total", "Стоимость: "},
                {"save", "Сохранить в файл"},
                {"saved", "Сохранено в order.txt"},
                {"fail", "Не удалось сохранить"}
        };
    }
}
