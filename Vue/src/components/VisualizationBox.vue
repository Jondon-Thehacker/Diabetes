<template>
    <div>
        <b-container class="bv-example-row" style="padding: 1rem 0rem">
            <b-row>
                <b-col cols="5">
                    <interval-modal></interval-modal>
                    <stackedbar-chart height="440"></stackedbar-chart>
                </b-col>
                <b-col class="graph">
                     <line-chart height="440"></line-chart>
                </b-col>
            </b-row>
            <b-row class="top-bar2">
                <b-col cols="4">Something</b-col>
                <b-col>
                    <p>Key Values</p>
                    <ul>
                        <li>Something important</li>
                        <li>More important</li>
                        <li>Also important</li>
                    </ul>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import LineChart from './LineChart.vue'
import StackedbarChart from './StackedbarChart.vue'
import IntervalModal from './IntervalModal.vue'

export default {
    components: {
        LineChart,
        StackedbarChart,
        IntervalModal

    }
}
</script>

<style >
/*.graph {
    min-height: 500px;
}*/
    
</style>