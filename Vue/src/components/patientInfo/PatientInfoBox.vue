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
                    <data-drop @emitOption="handleVisualizationOptionEmit" style="display: inline-block" 
                        optionName="Visualization" :options="['General Information','Average Linechart','TIR Barcharts']"/>
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
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import PatientEmail from './PatientEmail.vue'
import VisualizationBox from '../visualization/VisualizationBox.vue'
import NoteView from '../notes/NoteView.vue'
import DataDrop from './DataDrop'
import VisualizationAvgGraph from '../visualization/VisualizationAvgGraph'
import VisualizationBarCharts from '../visualization/VisualizationBarCharts'

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
        DataDrop,
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