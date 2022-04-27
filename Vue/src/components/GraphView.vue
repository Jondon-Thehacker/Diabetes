<template>
  <p>Graphs</p>
  <div>
    <Line :chart-data="chartData" />
  </div>
</template>

<script>
import { Line } from 'vue-chartjs'
import { Chart as   Chart,
  ArcElement,
  LineElement,
  BarElement,
  PointElement,
  BarController,
  BubbleController,
  DoughnutController,
  LineController,
  PieController,
  PolarAreaController,
  RadarController,
  ScatterController,
  CategoryScale,
  LinearScale,
  LogarithmicScale,
  RadialLinearScale,
  TimeScale,
  TimeSeriesScale,
  Decimation,
  Filler,
  Legend,
  Title,
  Tooltip,
  SubTitle } from 'chart.js'


Chart.register(ArcElement,
  LineElement,
  BarElement,
  PointElement,
  BarController,
  BubbleController,
  DoughnutController,
  LineController,
  PieController,
  PolarAreaController,
  RadarController,
  ScatterController,
  CategoryScale,
  LinearScale,
  LogarithmicScale,
  RadialLinearScale,
  TimeScale,
  TimeSeriesScale,
  Decimation,
  Filler,
  Legend,
  Title,
  Tooltip,
  SubTitle)

export default {
  name: 'BarChart',
  components: { Line },
  data() {
    return {
      chartData: {
        labels: [ '1990-03-03T23:00:00', '1990-03-04T23:00:00', '1990-03-05T23:00:00','1990-03-06T23:00:00','1990-03-07T23:00:00','1990-03-08T23:00:00','1990-03-09T23:00:00','1990-03-10T23:00:00'],
        datasets: [
          {
            label: 'CGM Levels',
            backgroundColor: '#f87979',
            data: [70, 30, 49,135,80,90,100,200]
          }
        ]
      }
    }
  }
}
</script>

<style>
  .container {
      width: 900px ;
      margin-left: auto ;
      margin-right: auto ;
      padding-top: 10%;
      }
</style>