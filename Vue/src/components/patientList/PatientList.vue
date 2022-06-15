<template>
    <b-form-input id="searchPatient" v-model="searchString" type="search" placeholder="Search for patient name" style="margin-bottom: 3px">
    </b-form-input>
    <div style="overflow-y: scroll; height: 620px;">
        <b-list-group style="max-width: 300px;">
            <!--Create a PatientItem for each patient in the list, which is filtered based on search-->
            <patient-item v-for="(patient, index) in filteredPatientList" :key="index"
                @patientClick = "handlePatientClickEmit"
                :patientName="patient.patientName" :patientId="patient.patientId" :patientEmail="patient.email"
                :badgeNr="patient.badgeNr" 
                :patientImage="patient.patientName"
                >
            </patient-item>
        </b-list-group>
    </div>
</template>

<script>
import PatientItem from './PatientItem.vue'


export default {
    props: {
            doctorId: Number
    },
    mounted() {
        this.getPatients()
    },
    data() {
        return {
            searchString: "",
            patients: []
        }
    },
     emits: ['patientClickEmit'],

    methods: {
        //GET-call to retrieve all patients
        getPatients() {
            this.axios({
                method: 'get',
                url: 'http://localhost:8080/api/v1/Doctors/' + this.doctorId +'/patients',
            }).then(res => {
                this.patients = res.data
            })
        },

        //Emits the patient information to the parent
        handlePatientClickEmit(value){
            this.$emit('patientClickEmit', value)
        }

    },
    components: {
        PatientItem
    },
    computed: {
        //Filters the list of patients based on the search string
        filteredPatientList() {
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