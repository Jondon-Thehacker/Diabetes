<template>
  <p> Graph </p>
  <div>
    <Line 
      :chart-data="chartData"
      :chart-options="chartOptions" 
      :width="width" 
      :height="height" />
  </div>
</template>

<script>

import { Line } from "vue-chartjs";
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
  components: { Line },
  props: {
    width: {
      type: Number,
      default: 600
    },
    height: {
      type: Number,
      default: 600
    },
    
    doctorId: Number,
    patientId: Number,
    measurementType: String,
    timeInterval: String
  },

  methods: {

    formatDate(date) {
      let year = date.slice(0,4)
      let day = date.slice(8,10)
      let month = date.slice(5,7)
      let time = date.slice(11,16)
      return day + '/' + month + '-' + year + ' ' +  time
    },

    getDataTimeInterval(){
      if(this.patientId != null && this.measurementType != null && this.timeInterval != null) {
        console.log("timeINterval is: " + this.timeInterval)
        this.axios({
          method: 'get',
            url: 'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/' + this.patientId + '/' + this.measurementType + '/' + this.timeInterval,
         /* url: 'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/' + 'Patients/' + '0' + '/Measurements' +'/CGM',*/
           /* url: 'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/' + '0' + '/CGM' + '/2022-01-01 00:00/2022-01-02 00:30',*/
              }).then(res => {
                console.log(res.data)
                this.measurements = res.data
                /*this.chartData.labels = this.measurements.map(m => m.time)
                this.chartData.datasets.data = this.measurements.map(m => m.value)*/
                this.chartData = {datasets:[{
                                              label: this.measurementType,
                                              backgroundColor: "#bbdcd3",
                                              data: this.measurements.map(m => m.value),
                                              pointRadius:2
                                            },], labels: this.measurements.map(m => this.formatDate(m.time))}
                console.log(this.chartData.datasets.data)
                console.log(this.chartData.labels)
              })
      }
    }
  
  },

  watch: {
    patientId() {
      this.getDataTimeInterval()
    },

    measurementType() {
      this.getDataTimeInterval()
    },

    timeInterval() {
      this.getDataTimeInterval()
    }

  },
  
  computed: {
    chartValues() {
      return {labels: this.measurements.map(m => m.time), data: this.measurements.map(m => m.value)}
    }
  },

  data() {
    return {
      measurements: [],
      
      chartData: {
        labels: [],
        datasets: [
          {
            label: "CGM Levels",
            backgroundColor: "#f87979",
            data: [5]
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          autocolors: false,
          annotation: {
            annotations: {
              line1: {
                type: "line",
                yMin: 3,
                yMax: 3,
                borderColor: "rgb(145, 172, 203)",
                borderWidth: 2,
              },
              line2: {
                type: "line",
                yMin: 13.9,
                yMax: 13.9,
                borderColor: "rgb(145, 172, 203)",
                borderWidth: 2,
              },
            },
          },
          
        },
      },
    };
  }
};
</script>

<style>

</style>