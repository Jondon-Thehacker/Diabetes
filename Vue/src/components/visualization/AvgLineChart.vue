<!--Emil Løvstrand Mortensen, s204483 and Simon Stampe Jensen, s204488-->
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
    timeInterval: String
  },

  methods: {
    //GET-call to retrieve summary data within time interval
    getDataInTimeInterval(){
      if(this.patientId != null && this.timeInterval != null) {
        this.axios({
          method: 'get',
            url: 'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/' + this.patientId + '/CGM/' + this.timeInterval + '/summary/lineChart/5',
              }).then(res => {
                this.measurements = res.data
                this.chartData = {
                      datasets: [
                                            {
                                              label: "Median",
                                              backgroundColor: 'rgba(100, 180, 255, 1)',
                                              //Extract median from data
                                              data: Object.values(res.data).map(m => m.Median),
                                              pointRadius: 1.5,
                                              lineTension: 0.1,
                                              fill: false
                                            },
                                            {
                                              //Label is null, since this line is only used to change color of graph to red
                                              label: 'null',
                                              //Extract max from data
                                              data: Object.values(res.data).map(m => m.Max),
                                              pointRadius: 0,
                                              lineTension: 0.1,
                                              fill: {
                                                      target: { value: 13.9 },
                                                      above: 'rgba(255, 100, 100, 0.8)',
                                                      below: 'rgba(0, 0, 0, 0)'
                                                    }
                                              },
                                              {
                                              //Label is null, since this line is only used to change color of graph to red
                                              label: 'null',
                                              //Extract min from data
                                              data: Object.values(res.data).map(m => m.Min),
                                              pointRadius: 0,
                                              lineTension: 0.1,
                                              fill: {
                                                      target: { value: 3 },
                                                      below: 'rgba(255, 100, 100, 0.8)',
                                                      above: 'rgba(0, 0, 0, 0)'
                                                    }
                                            },
                                            {
                                              label: "Min",
                                              backgroundColor: 'rgba(132, 212, 240, 0.2)',
                                              //Extract min from data
                                              data: Object.values(res.data).map(m => m.Min),
                                              pointRadius: 0,
                                              lineTension: 0.1,
                                              fill: '+1',
                                              borderDash: [5, 15]
                                            },
                                            {
                                              label: "Q1",
                                              backgroundColor: 'rgba(132, 212, 240, 0.8)',
                                              //Extract first quartile from data
                                              data: Object.values(res.data).map(m => m.Q1),
                                              pointRadius: 0,
                                              lineTension: 0.1,
                                              fill: '-4',
                                              borderDash: [5, 5]
                                            },
                                            {
                                              label: "Q3",
                                              backgroundColor: 'rgba(132, 212, 240, 0.8)',
                                              //Extract third quartile from data
                                              data: Object.values(res.data).map(m => m.Q3),
                                              pointRadius: 0,
                                              lineTension: 0.1,
                                              fill: '-5',
                                              borderDash: [5, 5]
                                            },
                                            {
                                              label: "Max",
                                              backgroundColor: 'rgba(132, 212, 240, 0.2)',
                                              //Extract max from data
                                              data: Object.values(res.data).map(m => m.Max),
                                              pointRadius: 0,
                                              lineTension: 0.1,
                                              fill: '-1',
                                              borderDash: [5, 15]
                                            }
                                             //Set the labels to be the time of the measurement (The keys of the JSON object)
                                          ], labels: Object.keys(this.measurements).map(m=>m.slice(0,5))}
              })
      }
    }
  
  },
  mounted() {
    this.getDataInTimeInterval()
  },
  watch: {
    patientId() {
      this.getDataInTimeInterval()
    },
    
    timeInterval() {
      this.getDataInTimeInterval()
    }
  },
  
  data() {
    return {
      measurements: [],
      
      //Object for initial chart data, before any data is retrieved
      chartData: {
        labels: [],
        datasets: [
          {
            
          },
        ],
      },
      //Object for different chart options
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          autocolors: false,
          annotation: {
            //The two lines that mark the dangerious levels of CGM
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
          //Enable zoom and pan
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
          //Remove labels with title "null"
          legend: {
            labels: {
              filter: function (item) {
                  return !(item.text === 'null');
              }

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