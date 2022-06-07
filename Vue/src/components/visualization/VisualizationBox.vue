<template>
    <div>
        <b-container class="bv-example-row" style="padding: 1rem 0rem">
            <b-row align-h="center">
                <div>
                    <interval-modal @timeIntervalEmit = "handleTimeIntervalEmit" style="display: inline-block"></interval-modal>
                    <data-drop @emitOption="handleMeasurementTypeEmit" style="display: inline-block" 
                        optionName="type" :options="['BASAL','BOLUS','CGM','EXERCISE','MEALS']"/>
                </div>
            </b-row>
            <b-row>
                <b-col cols="3">
                    <stackedbar-chart :height="height" :doctorId="doctorId" :patientId="patientId" :timeInterval="timeInterval"></stackedbar-chart>
                </b-col>
                <b-col class="graph">
                     <line-chart :height="height" :doctorId="doctorId" 
                     :patientId="patientId" :timeInterval="timeInterval" 
                     :measurementType="measurementType"></line-chart>
                </b-col>
            </b-row>
            <b-row class="top-bar2">
                <key-values :doctorId="doctorId" 
                     :patientId="patientId" :timeInterval="timeInterval" 
                     :measurementType="measurementType"></key-values>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import LineChart from './LineChart.vue'
import StackedbarChart from './StackedbarChart.vue'
import IntervalModal from '../patientInfo/IntervalModal.vue'
import KeyValues from './KeyValues.vue'
import DataDrop from '../patientInfo/DataDrop.vue'

export default {
    data() {
        return {
            timeInterval: null,
            height: 440,
            measurementType: null
        }
    },

    mounted(){
        console.log(this.patientId)      

    },
    props: {
        doctorId: Number,
        patientId: Number,
        
    },
    components: {
        LineChart,
        StackedbarChart,
        IntervalModal,
        KeyValues,
        DataDrop

    },
    methods: {
        handleTimeIntervalEmit(value){
            this.timeInterval = value
        },
        handleMeasurementTypeEmit(value){
            this.measurementType = value
        }
    }
}
</script>

<style >
/*.graph {
    min-height: 500px;
}*/

    
</style>