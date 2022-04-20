<template>
    <div>
        <b-container class="bv-example-row" style="padding: 0">
            <b-row>
                <b-col cols="4">
                    <p>Bar Charts</p>
                    <div>
                        <b-img :src="require('@/assets/barCharts.jpg')" fluid alt="Responsive image"></b-img>
                    </div>
                </b-col>
                <b-col class="graph">
                     <graph-view></graph-view>
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
import GraphView from './GraphView.vue'

export default {
    components: {
        GraphView 
    }
}
</script>

<style >
.graph {
    min-height: 500px;
}
    
</style>