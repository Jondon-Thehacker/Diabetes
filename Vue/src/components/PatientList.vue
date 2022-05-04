<template>
    <div style="overflow-y: scroll; height: 650px;">
        <b-list-group style="max-width: 300px;">
            <patient-item v-for="(patient,index) in patients" :key="index" :patientName="patient.patientName" :badgeNr="patient.badgeNr" :patientImage="patient.patientName"></patient-item>
        </b-list-group>
    </div>
</template>

<script>
import PatientItem from '../components/PatientItem.vue'


export default {
    data() {
        return {
            patients: [
                {
                    patientName: "John Doe",
                    badgeNr: 0
                },
                {
                    patientName: "Simon Stampe Jensen",
                    badgeNr: 2
                },
                {
                    patientName: "Jonathan Max Michelsen",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Pontoppidan Rasmussen",
                    badgeNr: 4
                },
                {
                    patientName: "Emil Løvstrand Mortensen",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Løvstrand Mortensen",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Løvstrand Mortensen",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Løvstrand Mortensen",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Løvstrand Mortensen",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Løvstrand Mortensen",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Løvstrand Mortensen",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Løvstrand Mortensen",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Løvstrand Mortensen",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Løvstrand Mortensen",
                    badgeNr: 0
                }
            ]
        }
    },
    components: {
        PatientItem
    }
}
</script>

<style>

.clickable-patient:hover {
    background-color: lightgray;
}
    
</style>