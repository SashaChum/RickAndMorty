> **Примечание: Основные ветки - это develop и Compose-Fragments**.

\
Проект представляет собой приложение по работе с API [Рик и Морти](https://rickandmortyapi.com/),
состоящее из трёх экранов:
1. Список всех персонажей.
2. Детальная информация о выбранном персонаже.
3. Список эпизодов, в которых данный персонаж появлялся.

&nbsp; &nbsp; На первом экране отображаются персонажи с их фотографией и именем. Реализована поэтапная пагинация
(paging 3) с нижним футером при подгрузке или ошибке. При клике на любом персонаже осуществляется переход
на второй экран с более детальной информацией о выбранном персонаже, а также имеется кнопка перехода на 
третий экран, где отображается список всех эпизодов с их датами выхода, в которых персонаж встречался.\
&nbsp; &nbsp; На каждом экране учтены состояния загрузки и ошибки, а также есть возможность перейти на предыдущий
экран с помощью стрелки верхнего toolbar.

&nbsp; &nbsp; Приложение реализовано по типу Single Activity на фрагментах и view binding (ветка develop), но также
имеется ветка Compose-Fragments, где второй и третий экраны сделаны на Compose и в качестве DI использован
Hilt (в ветке develop использован Dagger) для практики различных технологий.\
&nbsp; &nbsp; Основной паттерн для UI - MVVM. Также, по-возможности, соблюдены принципы чистой архитектуры.

