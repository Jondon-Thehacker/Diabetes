<template>
  <div>
    <b-container class="grid-container" fluid>
      <b-row class="top-bar">
        <b-col>Doctor ID: {{ doctorId }}</b-col>
        <b-col cols="8"><h1>Diabetes Management System</h1></b-col>
        <b-col>Hospital: {{ hospital }}</b-col>
      </b-row>
      <b-row>
        <b-col cols="2" class="p-list shadow">
          <patient-list
            @patientClickEmit="recievePatientData"
            :doctor-id="doctorId"
          ></patient-list>
        </b-col>
        <b-col class="no-marg">
          <patient-info-box :patientEmail="actualPatientEmail" :patientId="actualPatientId" :doctorId="doctorId" :patientName="actualPatientName"/>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import PatientList from "./components/patientList/PatientList.vue";
import PatientInfoBox from "./components/patientInfo/PatientInfoBox.vue";

export default {
  name: "App",
  components: {
    PatientList,
    PatientInfoBox,
  },
  data() {
    return {
      doctorId: 0,
      hospital: "Rigshospitalet",
      actualPatientId: null,
      actualPatientEmail: null,
      actualPatientName: null
    };
  },

  methods: {
    //Change patient information based on emit from child
    recievePatientData(value) {
      this.actualPatientId = value[0];
      this.actualPatientEmail = value[1];
      this.actualPatientName = value[2];
    },
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.top-bar {
  background-color: white;
}

.p-list {
  background-color: whitesmoke;
  margin-left: 0px;
  padding-right: 0px !important;
  padding-left: 3px !important;
}

.no-marg {
  margin: 0;
  padding: 0;
}


body {
  /*Hide Scrollbar*/
  /*overflow: hidden;*/
  
}

</style>



