<!--<template>
  <table class="table">
    <thead>
      <tr>
        <th scope="col">Standard_Deviation</th>
        <th scope="col">Glucose_variation</th>
        <th scope="col">min</th>
        <th scope="col">max</th>
        <th scope="col">GMI</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <th>{{ stddiv }}</th>
        <td>4</td>
        <td>3.9</td>
        <td>12.1</td>
        <td>5.9</td>
      </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  props: {
    doctorId: Number,
    patientId: Number,
    measurementType: String,
    timeInterval: String
  },

  mounted(){
    this.getStdDiv()
  },

  data(){
      return {
        stddiv: null
      }
  },

  methods: {
      getStdDiv(){
        this.axios({
            method: 'get',
            url: 'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/' + this.patientId + '/CGM/2022-01-01%2000%3A00/2022-01-01%2000%3A30/standardDeviation'
        }).then(res => {
            console.log(res.data)
            this.stddiv = res.data
        })  
      }
  }
};
</script>
<style></style>-->