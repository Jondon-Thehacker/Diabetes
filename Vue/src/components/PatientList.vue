<template>
    <b-form-input v-model="searchString" type="search" placeholder="Search for patient name" style="margin-bottom: 3px"></b-form-input>
    <div style="overflow-y: scroll; height: 620px;">
        <b-list-group style="max-width: 300px;">
            <patient-item v-for="(patient,index) in filteredPatientList" :key="index" 
                        :patientName="patient.patientName" 
                        :badgeNr="patient.badgeNr" 
                        :patientImage="patient.patientName"></patient-item>
        </b-list-group>
    </div>
</template>

<script>
import PatientItem from '../components/PatientItem.vue'


export default {
    data() {
        return {
            searchString: "",
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
                    patientName: "Emil Lorem Ipsum",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Dolor Sit Amet",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Consectetur",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Adipisicing Elit",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Suscipit Tempore",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Vitae Est",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Dicta Totam",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Aliquid Aut Architecto",
                    badgeNr: 0
                },
                {
                    patientName: "Emil Doloribus Deserunt",
                    badgeNr: 0
                }
            ]
        }
    },
    components: {
        PatientItem
    },
    computed: {
        filteredPatientList(){
            return this.patients.filter(p => p.patientName.toLowerCase().includes(this.searchString.toLowerCase()))
    }
} 
}
</script>

<style>

.clickable-patient:hover {
    background-color: lightgray;
}
    
</style>