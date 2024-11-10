Проект Spring boot "Атомная станция".
Этап №1 Создание электричества. На данном этапе нам потребуется создать проект, создать IoC контейнер (заполнив бинами), и запустить приложение.

1. Создать maven проект, внедрить зависимость Spring boot.
2. Инициализировать Git репозиторий и отправить его в Github.
3. Создать класс Runner и метод psvm, который будет поднимать контекст.
4. Необходимо создать реакторный цех ReactorDepartment (bean). Данный цех отвечает за производство электроэнергии. Реактор запускается на 1 день и производит 10 миллионов киловатт/часов. У цеха должно быть состояние: работает сейчас или нет.
5. В реакторном цехе есть метод run (запустить реактор) - (проверяет, что реактор не включен, иначе выбрасывает ошибку ReactorWorkException, с текстом - "Реактор уже работает"). Метод меняет состояние реактора и возвращает 10 миллионов киловатт/часов. Каждый 100 запуск - выдает ошибку NuclearFuelIsEmptyException.
6. Второй метод реакторного цеха stop (остановить реактор) - (проверяет, что реактор включен, иначе выбрасывает ошибку ReactorWorkException, с текстом - "Реактор уже выключен"). Метод меняет состояние реактора и ничего не возвращает.
7. Создать bean NuclearStation (Атомная станция). Данный bean класс содержит зависимость (поле ReactorDepartment и конструктор на это поле) на реакторный цех. У атомной станции есть атрибут - общее количество выработанной энергии. При создании станции, количество выработанной энергии равно 0.
8. У атомной станции есть метод startYear().
   Выводит на экран: "Атомная станция начала работу".
   Запускает годовой цикл производства электричества. 365 раз запускает и выключает реакторный цех. В случае ошибки, цех в данный день электричества не дает. Публикуется сообщение: "Внимание! Происходят работы на атомной станции! Электричества нет!". Количество произведенной электроэнергии за год должно быть просуммированно в отдельной переменной.
   Выводит на экран: "Атомная станция закончила работу. За год Выработано n киловатт/часов".
9. У атомной станции есть метод start(int year) - запускает в цикле year раз метод startYear.
10. В классе Runner, внутри psvm достать из контекста NuclearStation, и запустить метод start(3).

Этап №2 Отслеживание аварий. В этом этапе нужно отследить количество ошибок в реакторном цеху и передать в отдел безопасности.

1. У Атомной станции создадим новое поле - accidentCountAllTime (количество инцидентов за все время). Для изменения данного поля нужен метод incrementAccident(int count) - прибавляет count к полю accidentCountAllTime.
2. Создадим SecutiryDepartment(отдел безопасности). Этот отдел будет фиксировать ошибки из реакторного цеха. Необходимо поле accidentCountPeriod (количество инцидентов за период) внутри данного бина. Так же нужна зависимость на атомную станцию.
3. У отдела безопасности есть метод addAccident, который увеличивает количество инцидентов за период на 1.
4. Внутри реакторного цеха вставляем новую зависимость на отдел безопасности.
5. Если при работе реактора мы получаем ошибку, то вызываем addAccident из SecutiryDepartment.
6. Сделать метод получения инцидентов за период внутри SecutiryDepartment - getCountAccidents().
7. Сделать сброс счетчика инцидентов внутри SecutiryDepartment, с помощью метода reset(): Прибавляет инциденты из отдела безопасности в количество инцидентов за все время внутри атомной станции. Ставит 0 в поле accidentCountPeriod.
8. Для вызова обнуления счетчика в SecutiryDepartment, нам нужно внутри атомной станции сделать зависимость на SecutiryDepartment. Метод SecutiryDepartment.reset мы будем вызывать в конце startYear.
9. Запустить проект и увидеть ошибку, связанную с циклической зависимостью: атомная станция ссылается на отдел безопасности, а отдел безопасности на атомную станцию. Необходимо сделать ленивую инициализацию одного из этих бинов.
10. В конце метода start из NuclearStation, добавить вывод на экран "Количество инцидентов за всю работу станции: " + количество инцидентов за все время.
11. Внутри метода startYear из NuclearStation, добавить вывод на экран "Количество инцидентов за год: " + количество инцидентов за период (SecutiryDepartment). Подумать, в каком месте добавить этот вывод.

Этап №3 Экономика станции. В этом этапе мы посчитаем доходы, в зависимости от страны, в которой будет построена атомная станция.

1. Необходимо создать абстрактный класс EconomicDepartment. В нем будет абстрактный метод расчета дохода от произведенной энергии BigDecimal computeYearIncomes(long countElectricity).
2. Создать реализацию EconomicDepartment - FranceEconomicDepartment. У данного бина будет профиль "france". Французы рассчитывают доход следующим образом: базовый доход 1 киловатт/часа - 0,5 евро. При каждом новом миллиарде киловатт/часов, стоимость уменьшается на 1%. Пример: за год было произведено 3 млрд киловатт/часов. Формула расчета: 1 000 000 000 * 0,5 + 1 000 000 000 * (0.5 * 0,99) + 1 000 000 000 * (0,5 * 0,99 * 0,99). Цена и валюта должны лежать в ресурных файлах проекта.
3. Создать реализацию EconomicDepartment - MoroccoEconomicDepartment. У данного бина будет профиль "morocco". Марокканцы рассчитывают доход так: базовый доход 1 киловатт/часа - 5 дирхам. Если было произведено больше 5 миллиардов киловатт/часов, т.е все остальные считаются по увеличенному доходу 6 дирхам. Пример: за год было произведено 6 млрд киловатт/часов. Формула расчета: 5 000 000 000 * 5 + 1 000 000 000 * 6. Цена, наименование страны и валюта должны лежать в ресурных файлах проекта и подставляться в создаваемые бины.
5. Сделать зависимость от EconomicDepartment внутри атомной станции.
6. Доработать метод start атомной станции так, чтобы перед работой печаталось: "Действие происходит в стране: " + имя страны из свойств.
7. Доработать метод startYear так, чтобы он выводил на экран "Доход за год составил " + доход из метода computeYearIncomes + Наименование валюты.
8. Запустить проект с профилем 'france', и увидеть, что работает корректно. То же самое проделать с профилем 'morocco'.

Этап №4: Тестирование

1. Написать тесты на классы с поднятием ТЕСТОВОГО контекста. Довести покрытие кода до 80%. Протестировать приложение под разными spring профилями.