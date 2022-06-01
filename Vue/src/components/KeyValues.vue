<template>
  <table class="table">
    <thead>
      <tr>
        <th scope="col">Glucose Variation</th>
        <th scope="col">GMI</th>
        <th scope="col">Standard Deviation</th>
        <th scope="col">Min</th>
        <th scope="col">Max</th>
        <th scope="col">Avg</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>{{ gcv }}</td>
        <td>{{ gmi }}</td>
        <td>{{ stddiv }}</td>
        <td>{{ min }}</td>
        <td>{{ max }}</td>
        <td>{{ avg }}</td>
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

  data(){
      return {
        stddiv: null,
        gmi: null,
        min: null,
        max: null,
        gcv: null,
        avg: null
      }
  },

  methods: {
      getStdDiv(){
        this.axios({
            method: 'get',
            url: 'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/' + this.patientId +'/' + this.measurementType + '/' + this.timeInterval +'/standardDeviation'
        }).then(res => {
            this.stddiv = res.data.toPrecision(4)
        })  
      },
      getGMI() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/CGM/' + this.timeInterval + '/GMI'
        }).then(res =>{
          this.gmi = res.data.toPrecision(4)
        })
      },
      getMin() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/' + this.measurementType + '/' + this.timeInterval + '/min'
        }).then(res => {
          this.min = res.data.toPrecision(2)
        })
      },
      getMax() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/' + this.measurementType + '/' + this.timeInterval + '/max'
        }).then(res => {
          this.max = res.data.toPrecision(2)
        })
      },
      getGCV() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/CGM/' + this.timeInterval + '/glucoseVariability'
        }).then(res => {
          this.gcv = res.data.toPrecision(4)
        })
      },
      getAVG(){
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/' + this.measurementType + '/' + this.timeInterval + '/average'
        }).then(res => {
          this.avg = res.data.toPrecision(2)
        })
      }
  },

  watch:{
    patientId(){
      if (this.patientId!=null && this.measurementType!=null && this.timeInterval!=null) {
        this.getStdDiv()
        this.getAVG()
        this.getGCV()
        this.getMin()
        this.getMax() 
        this.getGMI()
      }
    },
    measurementType() {
      if(this.patientId!=null && this.measurementType!=null && this.timeInterval!=null){
        this.getStdDiv()
        this.getAVG()
        this.getMin()
        this.getMax() 
      }
    },
    timeInterval(){
      if(this.patientId!=null && this.measurementType!=null && this.timeInterval!=null){
      this.getAVG()
      this.getGCV()
      this.getMin()
      this.getMax() 
      this.getGMI()
      }
    }
  }
};
</script>
<style></style>