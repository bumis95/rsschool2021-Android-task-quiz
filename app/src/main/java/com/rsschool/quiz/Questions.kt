package com.rsschool.quiz

object Questions {

    data class Question(
        val text: String,
        val answers: List<String>
    )

    val questions = listOf(
        Question(
            text = "Земля какая по счету планета?",
            answers = listOf("1", "2", "3", "4", "5")
        ),
        Question(
            text = "Какой элемент периодической системы обозначается Ag?",
            answers = listOf("Золото", "Серебро", "Аргон", "Ртуть", "Марганец")
        ),
        Question(
            text = "Сколько будет 0,2 км в дециметрах?",
            answers = listOf("2", "20", "200", "2000", "20000")
        ),
        Question(
            text = "Зеленый пигмент, окрашивающий листья растений, называется:",
            answers = listOf("Хлорофилл", "Хлорофиллипт", "Хлоропласт", "Хлоропирамин", "Хлороформ")
        ),
        Question(
            text = "Самая длинная река в мире - это",
            answers = listOf("Волга", "Нил", "Днепр", "Миссисипи", "Амазонка")
        )
    )
}