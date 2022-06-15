<template>
  <div>
    <Bar
      :chart-data="chartData"
      :chart-options="chartOptions"
      :width="width"
      :height="height"
    />
  </div>
</template>

<script>
import { Bar } from "vue-chartjs";
import {
  Chart as Chart,
  BarElement,
  BarController,
  CategoryScale,
  Decimation,
  Filler,
  Legend,
  Title,
  Tooltip,
  SubTitle,
} from "chart.js";

import annotationPlugin from "chartjs-plugin-annotation";

Chart.register(
  BarElement,
  BarController,
  CategoryScale,
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
    doctorId: Number,
    patientId: Number,
    timeInterval: String,

    width: {
      type: Number,
      default: 400,
    },
    height: {
      type: Number,
      default: 600,
    },
  },
  methods: {
    //Formats the date string
    formatDate(date) {
      let year1 = date.slice(0, 4);
      let day1 = date.slice(8, 10);
      let month1 = date.slice(5, 7);
      let time1 = date.slice(11, 16);

      let year2 = date.slice(17, 21);
      let day2 = date.slice(25, 27);
      let month2 = date.slice(22, 24);
      let time2 = date.slice(28, 33);
      return (
       day1 + "/" + month1 + "-" + year1 + " " + time1 + " to " + day2 + "/" + month2 + "-" + year2 + " " + time2
      );
    },
    //Updates the values displayed in the bar chart
    async updateBarChart() {
      //Null check
      if (this.patientId != null && this.timeInterval != null) {
        //Initializing promises for each of the 5 GET-calls
        const promiseInRange = this.axios({
          method: "get",
          url: "http://localhost:8080/api/v1/Doctors/" + this.doctorId + "/" + this.patientId + "/CGM/" + this.timeInterval + "/countInRange",
        });

        const promiseAbove = this.axios({
          method: "get",
          url: "http://localhost:8080/api/v1/Doctors/" + this.doctorId + "/" + this.patientId + "/CGM/" + this.timeInterval + "/countAbove",
        });

        const promiseBelow = this.axios({
          method: "get",
          url: "http://localhost:8080/api/v1/Doctors/" + this.doctorId + "/" + this.patientId + "/CGM/" + this.timeInterval + "/countBelow",
        });

        const promiseSlightlyAbove = this.axios({
          method: "get",
          url: "http://localhost:8080/api/v1/Doctors/" + this.doctorId + "/" + this.patientId + "/CGM/" + this.timeInterval + "/countSlightlyAbove",
        });

        const promiseSlightlyBelow = this.axios({
          method: "get",
          url: "http://localhost:8080/api/v1/Doctors/" + this.doctorId + "/" + this.patientId + "/CGM/" + this.timeInterval + "/countSlightlyBelow",
        });

        //Executing all promises and wait for their responses
        await Promise.all([
          promiseInRange,
          promiseAbove,
          promiseBelow,
          promiseSlightlyAbove,
          promiseSlightlyBelow,
        ]).then((values) => {
          this.timeInRange = values[0].data;
          this.timeAbove = values[1].data;
          this.timeBelow = values[2].data;
          this.timeSlightlyAbove = values[3].data;
          this.timeSlighltyBelow = values[4].data;
        });

        //When all responses have been collected, we insert the data in the chart
        this.chartData = {
          labels: [this.formatDate(this.timeInterval)],
          datasets: [
            {
              label: "G < 3 mmol/l \n",
              backgroundColor: "#887694",
              data: [this.timeBelow],
              hoverBorderWidth: 0,
            },
            {
              label: "3 <= G < 3.9 mmol/l \n", 
              backgroundColor: "#b77b82",
              data: [this.timeSlighltyBelow],
              hoverBorderWidth: 0,
            },
            {
              label: "3.9 <= G < 10 mmol/l \n",
              backgroundColor: "#bbdcd3",
              data: [this.timeInRange],
              hoverBorderWidth: 0,
            },
            {
              label: "10 <= G < 13.9 mmol/l \n",
              backgroundColor: "#f8de7e",
              data: [this.timeSlightlyAbove],
              hoverBorderWidth: 0,
            },
            {
              label: "G > 13.9 mmol/l",
              backgroundColor: "#e7bd98",
              data: [this.timeAbove],
              hoverBorderWidth: 0,
            },
          ],
        };
      }
    },
  },

  watch: {
    patientId() {
      this.updateBarChart();
    },

    measurementType() {
      this.updateBarChart();
    },

    timeInterval() {
      this.updateBarChart();
    },
  },

  data() {
    return {
      timeAbove: 0,
      timeBelow: 0,
      timeInRange: 0,
      timeSlightlyAbove: 0,
      timeSlighltyBelow: 0,

      //The initial chart data, before selecting any values
      chartData: {
        labels: [],
        datasets: [
          {
            label: "G < 3 mmol/l",
            backgroundColor: "#887694",
            data: [0],
            hoverBorderWidth: 0,
          },
          {
            label: "3 <= G < 3.9 mmol/l       ",
            backgroundColor: "#b77b82",
            data: [0],
            hoverBorderWidth: 0,
          },
          {
            label: "3.9 <= G < 10 mmol/l",
            backgroundColor: "#bbdcd3",
            data: [0],
            hoverBorderWidth: 0,
          },
          {
            label: "10 <= G < 13.9 mmol/l",
            backgroundColor: "#f8de7e",
            data: [0],
            hoverBorderWidth: 0,
          },
          {
            label: "G > 13.9 mmol/l",
            backgroundColor: "#e7bd98",
            data: [0],
            hoverBorderWidth: 0,
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            align: "start",
            position: "bottom",
            reverse: true,
          },
        },
        //Setting the bar chart to stacked
        scales: {
          x: {
            stacked: true,
          },
          y: {
            stacked: true,
          },
        },
      },
    };
  },
};
</script>

<style>
</style>