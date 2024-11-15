ВАРІАНТ №1


У програмі реалізовано 2 підходи до вирішення завдання - Work stealing та Work dealing.
За вимогами до завдання сума масиву повинна була знаходитися попарно.

У підході з Work dealing створено Thread pool з 10 потоками. Кожна операція попарної суми додається як окреме завдання. Час роботи цієї версії програми перевищує підхід з Work stealing, це пов’язано з тим що більша частина часу йде на створення завдань і синхронізацію потоків для виконання завдання. Такий підхід є демонстративним і дозволяє продемонструвати що використання асинхронного програмування потребує ресурсів для синхронізації та не завжди є доречним.

У підході з Work stealing створено клас який наслідує клас RecursiveTask, для його використання з Fork Join фреймворком, та отримує посилання на масив та крайні індекси для підрахування суми. За замовчуванням задача буде розділюється на 2 рівних підзавдання якщо ділянка масиву для обрахування більше за 10000.
