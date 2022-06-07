<!--<template>
    <div>
        <b-card title="Card Title"
                img-src="https://picsum.photos/600/300/?image=25"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem;"
                class="mb-2">
            <b-card-text>
                Some quick example text to build on the card title and make up the bulk of the card's content.
            </b-card-text>

            <b-button @click="testAxios()" variant="primary">Go somewhere</b-button>
        </b-card>
    </div>
</template>-->

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
//import HelloWorld from './components/HelloWorld.vue'
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
      //Make a getDoctor method
      doctorId: 0,
      hospital: "Rigshospitalet",
      actualPatientId: null,
      actualPatientEmail: null,
      actualPatientName: null
    };
  },

  methods: {

    testAxios() {
      this.axios({
        method: "get",
        url: "http://localhost:8080/api/v1/Doctors/4/patients",
      }).then((res) => {
        console.log(res.data);
      });
    },

    recievePatientData(value) {
      console.log(value[0]);
      console.log(value[1]);
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

  /*margin-top: 10px;*/
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
/*   HIDE SCROLLBAR
body {
  overflow: hidden;
  
}*/
</style>



