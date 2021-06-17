package com.rsschool.quiz

data class Question(
    val text: String,
    val answers: List<String>,
    val correct: Int
)

val questions = listOf(
    Question(
        text = "Планета Земля по счету ...",
        answers = listOf("1", "2", "3", "4", "5"),
        correct = 2
    ),
    Question(
        text = "Какой элемент периодической системы обозначается Ag?",
        answers = listOf("Золото", "Серебро", "Аргон", "Ртуть", "Марганец"),
        correct = 1
    ),
    Question(
        text = "Сколько будет 0,2 км в дециметрах?",
        answers = listOf("2", "20", "200", "2000", "20000"),
        correct = 3
    ),
    Question(
        text = "Зеленый пигмент, окрашивающий листья растений, называется:",
        answers = listOf("Хлорофилл", "Хлорофиллипт", "Хлоропласт", "Хлоропирамин", "Хлороформ"),
        correct = 0
    ),
    Question(
        text = "Самая длинная река в мире - это",
        answers = listOf("Волга", "Нил", "Днепр", "Миссисипи", "Амазонка"),
        correct = 4
    )
)