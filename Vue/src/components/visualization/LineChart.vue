<template>
  <div>
    <!--chart options are either with or without the line marking dangerous GCM levels -->
    <Line
      :chart-data="chartData"
      :chart-options="hasLines ? chartOptions : chartOptions2"
      :width="width"
      :height="height"
    />
  </div>
</template>

<script>
import { Line } from "vue-chartjs";
import zoomPlugin from "chartjs-plugin-zoom";

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
  SubTitle,
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
      default: 600,
    },
    height: {
      type: Number,
      default: 600,
    },

    doctorId: Number,
    patientId: Number,
    measurementType: String,
    timeInterval: String,
  },

  methods: {
    //Formats the date to our desired string format
    formatDate(date) {
      let year = date.slice(0, 4);
      let day = date.slice(8, 10);
      let month = date.slice(5, 7);
      let time = date.slice(11, 16);
      return day + "/" + month + "-" + year + " " + time;
    },

    //GET-call retrieving 
    getDataInTimeInterval(){
      if(this.patientId != null && this.measurementType != null && this.timeInterval != null) {
        this.axios({
          method: "get",
          url: "http://localhost:8080/api/v1/Doctors/" + this.doctorId + "/" + this.patientId + "/" + this.measurementType + "/" + this.timeInterval,
        }).then((res) => {
          this.measurements = res.data
          //Changing the chart data to the retrieved measurements
          this.chartData = {
            datasets: [
              {
                label: this.measurementType + this.unit(this.measurementType),
                backgroundColor: "#bbdcd3",
                data: this.measurements.map((m) => m.value),
                pointRadius: 2,
              },
            ],
            labels: this.measurements.map((m) => this.formatDate(m.time)),
          };
        });
      }
    },
    //Change the chart options to contain two horizontal lines if type is CGM
    changeChartOptions() {
      if (this.measurementType != "CGM") {
        this.hasLines = false;
      } else {
        this.hasLines = true;
      }
    },
    //Finds the correct unit for a measurement type
    unit(m) {
      switch (m) {
        case "EXERCISE":
          return " (%)";
        case "CGM":
          return " (mmol/L)";
        case "MEALS":
          return " (g CHO)";
        case "BASAL":
          return " (mU/min)";
        case "BOLUS":
          return " (U)";
        default:
          return "";
      }
    },
  },
  watch: {
    patientId() {
      this.getDataInTimeInterval()
    },

    measurementType() {
      this.getDataInTimeInterval()
      this.changeChartOptions();
    },

    timeInterval() {
      this.getDataInTimeInterval()
    }
  },
  
  data() {
    return {
      hasLines: true,
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
              //Displays two horizontal lines
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
              mode: "x",
            },
            zoom: {
              wheel: {
                enabled: true,
              },
              mode: "x",
            },
            limits: {
              x: { minRange: 50 },
            },
          },
        },
      },
      chartOptions2: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          //Enable zoom and pan
          zoom: {
            pan: {
              enabled: true,
              mode: "x",
            },
            zoom: {
              wheel: {
                enabled: true,
              },
              mode: "x",
            },
            limits: {
              x: { minRange: 50 },
            },
          },
        },
      },
    };
  },
};
</script>

<style>
</style>