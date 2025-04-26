package app.service

import org.knowm.xchart.CategoryChartBuilder
import org.knowm.xchart.SwingWrapper

// Класс для отображения результатов на графике
object ChartDrawer {

    fun drawScores(generatorNames: List<String>, scores: List<Double>) {
        val chart = CategoryChartBuilder()
            .width(800)
            .height(600)
            .title("Результаты тестирования генераторов")
            .xAxisTitle("Генераторы")
            .yAxisTitle("Баллы")
            .build()

        chart.addSeries("Оценка качества", generatorNames, scores)

        // Отобразить окно с графиком
        SwingWrapper(chart).displayChart()
    }
}