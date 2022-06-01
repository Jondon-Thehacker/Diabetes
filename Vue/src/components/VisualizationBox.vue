<template>
    <div>
        <b-container class="bv-example-row" style="padding: 1rem 0rem">
            <b-row>
                <b-col cols="3">
                    <interval-modal @timeIntervalEmit = "handleTimeIntervalEmit" ></interval-modal>
                    <stackedbar-chart :height="height" :doctorId="doctorId" :patientId="patientId" :timeInterval="timeInterval"></stackedbar-chart>
                </b-col>
                <b-col class="graph">
                     <line-chart :height="height" :doctorId="doctorId" 
                     :patientId="patientId" :timeInterval="timeInterval" 
                     :measurementType="measurementType"></line-chart>
                </b-col>
            </b-row>
            <b-row class="top-bar2">
                <b-col cols="4">Something</b-col>
                <b-col>
                    <p>Key Values for patient  {{patientId}} </p>
                    <!--<ul> 
                        <li>Glucose Management Index</li>
                        <li>Glucose Variation</li>
                        <li>Other aggregate functions</li>
                    </ul>-->
                    <!--<key-values :doctorId="doctorId" 
                     :patientId="patientId" :timeInterval="timeInterval" 
                     :measurementType="measurementType"></key-values>-->
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import LineChart from './LineChart.vue'
import StackedbarChart from './StackedbarChart.vue'
import IntervalModal from './IntervalModal.vue'
//import KeyValues from './KeyValues.vue'

export default {
    data() {
        return {
            timeInterval: null,
            height: 440
        }
    },

    mounted(){
        console.log(this.patientId)
    },
    props: {
        doctorId: Number,
        patientId: Number,
        measurementType: String
    },
    components: {
        LineChart,
        StackedbarChart,
        IntervalModal,
        //KeyValues

    },
    methods: {
        handleTimeIntervalEmit(value){
            this.timeInterval = value
        }
    }
}
</script>

<style >
/*.graph {
    min-height: 500px;
}*/
    
</style>