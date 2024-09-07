# PS5 Rental Hub - Оренда консолей PS5

## Опис програми

Це Java-додаток для керування орендою консолей PlayStation 5. Додаток дозволяє:
- Переглядати наявність вільних консолей на певні дати.
- Додавати нові оренди консолей.
- Зберігати дані про оренди у файли CSV.
- Працювати з орендою, переглядати актуальні дані.

Програма працює на Java 19 і зберігає дані у форматі CSV.


## Налаштування

Перед використанням програми, необхідно налаштувати конфігураційний файл `app.properties`. Він має містити наступні параметри:

- **csv.file.path**: Шлях до папки, де зберігатимуться файли з даними про оренди.
- **console.serial-numbers**: Список серійних номерів консолей, доступних для оренди, розділених крапкою з комою.

### Приклад конфігурації:

- csv.file.path=E:/JavaMykola/MykolaDevYouTube/others
- console.serial-numbers=KRO123456789F54;HGF009SFSFD000S;GHJ000POI000


## Функціональні можливості

### 1. Вибір вільної консолі
Користувач може вибрати дати оренди через вікно вибору дати. Програма перевіряє наявність вільної консолі та пропонує заповнити дані для замовлення (ім'я, номер телефону, паспортні дані). Якщо вільна консоль є, вона резервується на вказаний період.

### 2. Додавання нової оренди
При виборі вільної консолі, відкривається вікно для введення даних замовника. Оренда записується в файл `actual-rentals.csv`.

### 3. Збереження даних
Дані про актуальні оренди зберігаються в файл `actual-rentals.csv`. Коли термін оренди закінчується, дані переміщуються до файлу `history.csv`.

### 4. Перегляд актуальних оренд
Користувач може переглянути всі актуальні оренди у вигляді таблиці. Дані включають:
- Початок і кінець оренди.
- Ім'я клієнта.
- Номер телефону.
- Серійний номер консолі.


## Автор
Розробник: Микола Литвинов
