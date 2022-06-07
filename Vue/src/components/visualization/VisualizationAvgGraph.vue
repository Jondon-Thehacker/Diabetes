<template>
<b-container class="bv-example-row" style="padding: 1rem 0rem">
    <b-row align-h="center">
        <interval-modal @timeIntervalEmit = "handleTimeIntervalEmit" hideTime></interval-modal>    
    </b-row>
    <b-row>
        <avg-line-chart :doctorId="doctorId" :patientId="patientId" :timeInterval="timeInterval">
                        
        </avg-line-chart>
    </b-row>
</b-container>
</template>

<script>
import IntervalModal from '../patientInfo/IntervalModal.vue'
import AvgLineChart from './AvgLineChart.vue'

export default {
    props: {
        doctorId: Number,
        patientId: Number
    },
    data() {
        return {
            timeInterval: null
        }
    },
    components: {
        IntervalModal,
        AvgLineChart
    },
    methods: {
        handleTimeIntervalEmit(value){
            this.timeInterval = value
        },
    }
    
}
</script>

<style>

</style>