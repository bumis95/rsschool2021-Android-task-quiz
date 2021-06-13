package com.rsschool.quiz

class ResultMaker {

    companion object {

        fun getResult(list: ArrayList<Int>): String = """
            Your result: ${getPercent(list)}%
            
            1) ${questions[0].text}
            Your answer: ${questions[0].answers[list[0]]}
            
            2) ${questions[1].text}
            Your answer: ${questions[1].answers[list[1]]}
            
            3) ${questions[2].text}
            Your answer: ${questions[2].answers[list[2]]}
                        
            4) ${questions[3].text}
            Your answer: ${questions[3].answers[list[3]]}
            
            5) ${questions[4].text}
            Your answer: ${questions[4].answers[list[4]]}      
        """.trimIndent()

        private fun getPercent(list: ArrayList<Int>): Int {
            var res = 0f
            list.forEachIndexed { index, answer ->
                when (answer) {
                    questions[index].correct -> {
                        res++
                    }
                }
            }
            return (res.div(5) * 100).toInt()
        }
    }
}