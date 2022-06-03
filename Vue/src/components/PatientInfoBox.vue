<template>
    <div>
        <b-container class="bv-example-row" style="padding: 0" fluid>
            <b-row class="top-bar2 shadow-sm">
                <b-col>
                    <div style="display: inline-block; vertical-align: middle; padding: 1rem">
                        {{patientName}}
                    </div>
                </b-col>
                <b-col>
                    <patient-email :patientEmail="patientEmail"></patient-email>
                </b-col>
                <b-col>
                    <visualization-options @emitVisualizationOption="handleVisualizationOptionEmit"/>
                </b-col>
                <b-col>
                    <note-view :patientName = "patientName" :patientId = "patientId" :doctorId = "doctorId"/>
                </b-col>
            </b-row>
            <b-row>
                <b-col>
                    <visualization-box v-if="visualizationOption === 'General Information'" :doctorId="doctorId" :patientId="patientId" />
                    <visualization-avg-graph v-if="visualizationOption === 'Average Linechart'" :doctorId="doctorId" :patientId="patientId" />
                    <visualization-bar-charts v-if="visualizationOption === 'TIR Barcharts'" :doctorId="doctorId" :patientId="patientId"></visualization-bar-charts> 
                    <!--TIR Barcharts -->
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import PatientEmail from './PatientEmail.vue'
import VisualizationBox from './VisualizationBox.vue'
import NoteView from './NoteView.vue'
import VisualizationOptions from './VisualizationOptions'
import VisualizationAvgGraph from './VisualizationAvgGraph'
import VisualizationBarCharts from './VisualizationBarCharts'

export default {
    data() {
        return {
            visualizationOption: "General Information"
        }
    },

    props:{
        patientEmail: String,
        patientId: Number,
        doctorId: Number,
        patientName: String
    },

    components: {
        VisualizationOptions,
        VisualizationBox,
        NoteView,
        PatientEmail,
        VisualizationAvgGraph,
        VisualizationBarCharts
    },

    methods: {
        handleVisualizationOptionEmit(value){
            this.visualizationOption = value
        }
    }
}
</script>

<style>

.notes {
    margin-top: 20px;
    margin-right: 0px;
    background-color:white;
}

.top-bar2 {
    background-color: whitesmoke;
}


    
</style>