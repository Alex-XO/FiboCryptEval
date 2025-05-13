package app.service

import app.model.EvaluationResult
import org.knowm.xchart.CategoryChartBuilder
import org.knowm.xchart.XChartPanel
import org.knowm.xchart.style.Styler
import javax.swing.JPanel

object ChartDrawer {
    fun buildChart(results: List<EvaluationResult>): JPanel {
        val chart = CategoryChartBuilder()
            .width(600)
            .height(400)
            .title("Оценка генераторов")
            .xAxisTitle("Генератор")
            .yAxisTitle("Баллы")
            .build()

        chart.styler.legendPosition = Styler.LegendPosition.InsideNE

        val names = results.map { it.name }
        val scores = results.map { it.score.toDouble() }

        chart.addSeries("Результаты", names, scores)

        return XChartPanel(chart)
    }
}