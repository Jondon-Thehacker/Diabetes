<template>
  <div>
    <Bar 
      :chart-data="chartData" 
      :chart-options="chartOptions"
      :width="width" 
      :height="height"/>
  </div>
</template>

<script>
import { Bar } from "vue-chartjs";
import {
  Chart as Chart,
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
  SubTitle,
} from "chart.js";

import annotationPlugin from "chartjs-plugin-annotation";

Chart.register(
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
  SubTitle,
  annotationPlugin
);

export default {
  name: "BarChart",
  components: { Bar },
  props: {
    width: {
      type: Number,
      default: 400
    },
    height: {
      type: Number,
      default: 600
    }
  },
  data() {
    return {
      chartData: {
        labels: ["1990-03-03T23:00:0 to 1990-03-10T23:00:00"],
        datasets: [
          {
            label: "Under normal level",
            backgroundColor: "#b77b82",
            data: [30],
            hoverBorderWidth: 0,
          },
          {
            label: "Normal level",
            backgroundColor: "#bbdcd3",
            data: [40],
            hoverBorderWidth: 0,
          },
          {
            label: "over normal level",
            backgroundColor: "#f8de7e",
            data: [30],
            hoverBorderWidth: 0,
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            stacked: true,
             },
          y: {
             stacked: true
      }
    },
      },
    };
  },
};
</script>

<style>
/*.container {
  width: 500;
  height: 500;
  margin-left: auto;
  margin-right: auto;
  padding-top: 10%;
}*/
</style>