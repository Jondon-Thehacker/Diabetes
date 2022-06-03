<template>
    <b-container class="bv-example-row" style="padding: 1rem 0rem">
        <b-row align-h="center">
            <interval-modal @timeIntervalEmit = "handleTimeIntervalEmit" hideTime></interval-modal>
        </b-row>
        <b-row>
            <multiple-bar-charts :timeInterval="timeInterval" :doctorId="doctorId" :patientId="patientId"></multiple-bar-charts>
        </b-row>

    </b-container>
</template>

<script>
import IntervalModal from './IntervalModal.vue'
import MultipleBarCharts from './MultipleBarCharts.vue'

export default {

    props:{
        patientId: Number,
        doctorId: Number
    },
    data() {
        return {
            timeInterval: null
        }
    },
     components: {
        IntervalModal,
        MultipleBarCharts
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