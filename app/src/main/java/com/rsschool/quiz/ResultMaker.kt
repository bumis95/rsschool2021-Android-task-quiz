package com.rsschool.quiz

class ResultMaker {

    companion object {

        fun getResult(list: ArrayList<Int>): String = """
            ${getPercent(list)}
            
            1) ${questions[0].text}
            Твой ответ: ${questions[0].answers[list[0]]}
            
            2) ${questions[1].text}
            Твой ответ: ${questions[1].answers[list[1]]}
            
            3) ${questions[2].text}
            Твой ответ: ${questions[2].answers[list[2]]}
                        
            4) ${questions[3].text}
            Твой ответ: ${questions[3].answers[list[3]]}
            
            5) ${questions[4].text}
            Твой ответ: ${questions[4].answers[list[4]]}      
        """.trimIndent()

        fun getPercent(list: ArrayList<Int>): String {
            var res = 0f
            list.forEachIndexed { index, answer ->
                when (answer) {
                    questions[index].correct -> res++
                }
            }
            return "Твой результат: ${(res.div(5) * 100).toInt()}%"
        }
    }
}