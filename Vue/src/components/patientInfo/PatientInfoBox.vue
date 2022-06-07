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
                    <visualization-single-chart v-if="visualizationOption != 'General Information'" :doctorId="doctorId" :patientId="patientId" :chartType="visualizationOption" />
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import PatientEmail from './PatientEmail.vue'
import VisualizationBox from '../visualization/VisualizationBox.vue'
import NoteView from '../notes/NoteView.vue'
import DataDrop from './DataDrop.vue'
import VisualizationSingleChart from '../visualization/VisualizationSingleChart.vue'


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
        VisualizationSingleChart
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