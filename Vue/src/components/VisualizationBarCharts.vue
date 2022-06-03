<template>
    <interval-modal hideTime></interval-modal>
</template>

<script>
import IntervalModal from './IntervalModal.vue'
export default {

    props:{
        patientId: Number,
        doctorId: Number,
        timeInterval: String
    },
     components: {
        IntervalModal,
    },
    
}
</script>

<style>
    
</style>