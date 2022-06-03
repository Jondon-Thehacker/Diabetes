<template>
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
import zoomPlugin from 'chartjs-plugin-zoom';

import {
  Chart as Chart,
  ArcElement,
  LineElement,
  PointElement,
  LineController,
  LinearScale,
  LogarithmicScale,
  Legend,
  Title,
  Tooltip,
  SubTitle
} from "chart.js";


import annotationPlugin from "chartjs-plugin-annotation";

Chart.register(
  ArcElement,
  LineElement,
  PointElement,
  LineController,
  LinearScale,
  LogarithmicScale,
  Legend,
  Title,
  Tooltip,
  SubTitle,
  annotationPlugin,
  zoomPlugin
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
      default: 500
    },

    doctorId: Number,
    patientId: Number,
    //measurementType: String,
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
   /* getTimeLabels(){
        let timeLabels = []
        let time = "00:00"
        let minutes, hours
        while(time != "24:00"){
            minutes = time.slice(3,5)
            hours = time.slice(0,2)
            minutes = minutes + 5
        }
    },*/
  

    getDataInTimeInterval(){
      if(this.patientId != null && this.timeInterval != null) {
        
        this.axios({
          method: 'get',
            url: 'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/' + this.patientId + '/CGM/' + this.timeInterval + '/summary/lineChart/5',
              }).then(res => {
                console.log(res.data)
                console.log(Object.keys(res.data).map(m=>m.slice(0,5)))
                console.log(Object.values(res.data))
                this.measurements = res.data
                  this.chartData = {
                      datasets: [
                                            {
                                              label: "Min",
                                              backgroundColor: 'rgba(132, 212, 240, 0.2)',
                                              data: Object.values(res.data).map(m => m.Min),
                                              pointRadius: 0,
                                              lineTension: 0.1,
                                              fill: {
                                                  target: { value: 3 },
                                                  below: 'rgba(255, 100, 100, 255)',
                                                  above: 'rgba(0, 0, 0, 0)'
                                              },
                                              borderDash: [5, 15]
                                            },
                                            {
                                              label: "Q1",
                                              backgroundColor: 'rgba(132, 212, 240, 0.4)',
                                              data: Object.values(res.data).map(m => m.Q1),
                                              pointRadius: 0,
                                              lineTension: 0.1,
                                              fill: '+1',
                                              borderDash: [5, 5]
                                            },
                                            {
                                              label: "Median",
                                              backgroundColor: 'rgba(100, 180, 255, 1)',
                                              data: Object.values(res.data).map(m => m.Median),
                                              pointRadius: 1.5,
                                              lineTension: 0.1,
                                              fill: false
                                            },
                                            {
                                              label: "Q3",
                                              backgroundColor: 'rgba(132, 212, 240, 0.4)',
                                              data: Object.values(res.data).map(m => m.Q3),
                                              pointRadius: 0,
                                              lineTension: 0.1,
                                              fill: '-1',
                                              borderDash: [5, 5]
                                            },
                                            {
                                              label: "Max",
                                              backgroundColor: 'rgba(132, 212, 240, 0.2)',
                                              data: Object.values(res.data).map(m => m.Max),
                                              pointRadius: 0,
                                              lineTension: 0.1,
                                              fill: {
                                                  target: { value: 13.9 },
                                                  above: 'rgba(255, 100, 100, 255)',
                                                  below: 'rgba(0, 0, 0, 0)'
                                              },
                                              borderDash: [5, 15]
                                            }
                                            ], labels: Object.keys(this.measurements).map(m=>m.slice(0,5))} //object.keys
                console.log(this.chartData.datasets.data)
                console.log(this.chartData.labels)
              })
      }
    }
  
  },

  watch: {
    patientId() {
      this.getDataInTimeInterval()
    },

    measurementType() {
      this.getDataInTimeInterval()
    },

    timeInterval() {
      this.getDataInTimeInterval()
    }
  },
  
  data() {
    return {
      measurements: [],
      
      chartData: {
        labels: [],
        datasets: [
          {
            
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
          zoom: {
            pan: {
              enabled: true,
              mode: 'x',
            },
            zoom: {
              wheel: {
                enabled: true,
              },
              mode: 'x',
            },
            limits: {
              x: {minRange: 50},
            }
          },  
        },
        legend: {
                  display: false
        },
      },
    };
  }
};
</script>

<style>

</style>