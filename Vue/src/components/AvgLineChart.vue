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

    getDataInTimeInterval(){
      if(this.patientId != null && this.timeInterval != null) {
        
        this.axios({
          method: 'get',
            url: 'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/' + this.patientId + '/CGM/' + this.timeInterval,
              }).then(res => {
                console.log(res.data)
                this.measurements = res.data
                this.chartData = {datasets:[{
                                              label: this.measurementType,
                                              backgroundColor: "#bbdcd3",
                                              data: this.measurements.map(m => m.value+1),
                                              pointRadius:2
                                            },
                                            {
                                              label: this.measurementType,
                                              backgroundColor: "#bbdcd3",
                                              data: this.measurements.map(m => m.value),
                                              pointRadius:2
                                            },
                                            {
                                              label: this.measurementType,
                                              backgroundColor: "#bbdcd3",
                                              data: this.measurements.map(m => m.value-1),
                                              pointRadius:2
                                            }], labels: this.measurements.map(m => this.formatDate(m.time))}
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
      },
    };
  }
};
</script>

<style>

</style>