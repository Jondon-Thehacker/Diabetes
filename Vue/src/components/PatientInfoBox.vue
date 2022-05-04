<template>
    <div>
        <b-container class="bv-example-row" style="padding: 0" fluid>
            <b-row class="top-bar2">
                <b-col style="padding-top: 1rem">Patient Name</b-col>
                <b-col>
                    <patient-email/>
                </b-col>
                <b-col>
                    <data-drop/>
                </b-col>
            </b-row>
            <b-row>
                <b-col>
                    <visualization-box/>
                </b-col>
                <b-col cols="1" class="notes">
                    <note-view/>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import PatientEmail from './PatientEmail.vue'
import VisualizationBox from './VisualizationBox.vue'
import NoteView from './NoteView.vue'
import DataDrop from './DataDrop.vue'

export default {
    components: {
        VisualizationBox,
        NoteView,
        PatientEmail,
        DataDrop
    }
}
</script>

<style>

.notes {
    margin-top: 20px;
    margin-right: 0px;
    background-color:cornflowerblue;
}

.top-bar2 {
    background-color: rgb(66, 117, 119);
}


    
</style>