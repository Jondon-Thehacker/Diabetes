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
    components: { Bar },
    props: {
        patientId: Number,
        doctorId: Number,
        timeInterval: String,

        width: {
            type: Number,
            default: 400,
        },
        height: {
            type: Number,
            default: 400,
        },
    },
    data() {
        return {
          chartData: {
            //The bar charts are always for the following time intervals:
            labels: ['00-02','02-04','04-06','06-08','08-10','10-12','12-14','14-16','16-18','18-20','20-22','22-24'],
            datasets: [
              {
                label: "G < 3 mmol/l",
                backgroundColor: "#FF160C",
                data: [],
                hoverBorderWidth: 0,
              },
              {
                label: "3 <= G < 3.9 mmol/l       ",
                backgroundColor: "#b77b82",
                data: [],
                hoverBorderWidth: 0,
              },
              {
                label: "3.9 <= G < 10 mmol/l",
                backgroundColor: "#bbdcd3",
                data: [],
                hoverBorderWidth: 0,
              },
              {
                label: "10 <= G < 13.9 mmol/l",
                backgroundColor: "#f8de7e",
                data: [],
                hoverBorderWidth: 0,
              },
              {
                label: "G > 13.9 mmol/l",
                backgroundColor: "#e7bd98",
                data: [],
                hoverBorderWidth: 0,
              },
            ],
          },
          chartOptions: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {
              align: "center",
              position: "top",
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
            
        }
    },

    methods: {
        //GET-call to retrieve data in the given interval
        getDataInTimeInterval() {
        if(this.patientId != null && this.timeInterval != null) {
          this.axios({
              method: "get",
              url: "http://localhost:8080/api/v1/Doctors/" + this.doctorId + "/" + this.patientId + "/CGM/" + this.timeInterval + "/summary/barChart/120",
          }).then((res) => {
                  this.chartData = {
            labels: ['00-02','02-04','04-06','06-08','08-10','10-12','12-14','14-16','16-18','18-20','20-22','22-24'],
            datasets: [
              {
                label: "G < 3 mmol/l \n",
                backgroundColor: "#FF160C",
                //Extracting the measurements below normal levels
                data: Object.values(res.data).map(m => m.Below),
                hoverBorderWidth: 0,
              },
              {
                label: "3 <= G < 3.9 mmol/l \n", 
                backgroundColor: "#b77b82",
                //Extracting the measurements slightly below normal levels
                data: Object.values(res.data).map(m => m.SlightlyBelow),
                hoverBorderWidth: 0,
              },
              {
                label: "3.9 <= G < 10 mmol/l \n",
                backgroundColor: "#bbdcd3",
                //Extracting the measurements in range of normal levels
                data: Object.values(res.data).map(m => m.InRange),
                hoverBorderWidth: 0,
              },
              {
                label: "10 <= G < 13.9 mmol/l \n",
                backgroundColor: "#f8de7e",
                //Extracting the measurements slightly above normal levels
                data: Object.values(res.data).map(m => m.SlightlyAbove),
                hoverBorderWidth: 0,
              },
              {
                label: "G > 13.9 mmol/l",
                backgroundColor: "#e7bd98",
                //Extracting the measurements above normal levels
                data: Object.values(res.data).map(m => m.Above),
                hoverBorderWidth: 0,
              },
            ],
          };
          });
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
  
}
</script>

<style>
    
</style>