<!--Emil Løvstrand Mortensen, s204483-->
<template>
    <b-container class="bv-example-row" style="padding: 1rem 0rem">
        <b-row align-h="center">
            <interval-modal @timeIntervalEmit = "handleTimeIntervalEmit" hideTime></interval-modal>
        </b-row>
        <b-row>
            <!--Displays only the selected chart, based on the "chartType" prop-->
            <multiple-bar-charts v-if="chartType==='TIR Barcharts'" :timeInterval="timeInterval" :doctorId="doctorId" :patientId="patientId" height="500"></multiple-bar-charts>
            <avg-line-chart v-if="chartType==='Average Linechart'" :timeInterval="timeInterval" :doctorId="doctorId"   :patientId="patientId" height="500"></avg-line-chart>
        </b-row>

    </b-container>
</template>

<script>
import IntervalModal from '../patientInfo/IntervalModal.vue'
import MultipleBarCharts from './MultipleBarCharts.vue'
import AvgLineChart from './AvgLineChart.vue'

export default {

    props:{
        patientId: Number,
        doctorId: Number,
        chartType: String
    },
    data() {
        return {
            timeInterval: null
        }
    },
     components: {
        IntervalModal,
        MultipleBarCharts,
        AvgLineChart
    },

    methods: {
        //Change time interval when emitted from child
        handleTimeIntervalEmit(value){
            this.timeInterval = value
        },

    }
    
}
</script>

<style>
    
</style>