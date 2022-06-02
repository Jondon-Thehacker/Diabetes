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

  mounted(){
  },

  data(){
      return {
        stddiv: null,
        gmi: null,
        min: null,
        max: null,
        gcv: null,
        avg: null,
        x: null
      }
  },

  methods: {
      getStdDiv(){
        this.axios({
            method: 'get',
            url: 'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/' + this.patientId +'/' + this.measurementType + '/' + this.timeInterval +'/standardDeviation'
        }).then(res => {
          switch(this.measurementType){
            case 'EXERCISE':
              this.stddiv = res.data.toPrecision(4) + ' %';
              break;
            case 'CGM':
              this.stddiv = res.data.toPrecision(4) + ' mmol/L';
              break;
            case 'MEALS':
              this.stddiv = res.data.toPrecision(4) + ' g CHO';
              break;
            case 'BASAL':
              this.stddiv = res.data.toPrecision(4) + ' mU/min';
              break;
            case 'BOLUS':
              this.stddiv = res.data.toPrecision(4) + ' U';
              break;
            default:
              this.stddiv = res.data.toPrecision(4);
          }
        })  
      },
      getGMI() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/CGM/' + this.timeInterval + '/GMI'
        }).then(res =>{
          this.gmi = res.data.toPrecision(3) + ' mmol/L'
        })
      },
      getMin() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/' + this.measurementType + '/' + this.timeInterval + '/min'
        }).then(res => {
          switch(this.measurementType){
            case 'EXERCISE':
              this.min = res.data.toPrecision(4) + ' %';
              break;
            case 'CGM':
              this.min = res.data.toPrecision(4) + ' mmol/L';
              break;
            case 'MEALS':
              this.min = res.data.toPrecision(4) + ' g CHO';
              break;
            case 'BASAL':
              this.min = res.data.toPrecision(4) + ' mU/min';
              break;
            case 'BOLUS':
              this.min = res.data.toPrecision(4) + ' U';
              break;
            default:
              this.min = res.data.toPrecision(4);
          }
        })
      },
      getMax() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/' + this.measurementType + '/' + this.timeInterval + '/max'
        }).then(res => {
          switch(this.measurementType){
            case 'EXERCISE':
              this.max = res.data.toPrecision(4) + ' %';
              break;
            case 'CGM':
              this.max = res.data.toPrecision(4) + ' mmol/L';
              break;
            case 'MEALS':
              this.max = res.data.toPrecision(4) + ' g CHO';
              break;
            case 'BASAL':
              this.max = res.data.toPrecision(4) + ' mU/min';
              break;
            case 'BOLUS':
              this.max = res.data.toPrecision(4) + ' U';
              break;
            default:
              this.max = res.data.toPrecision(4);
          }
        })
      },
      getGCV() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/CGM/' + this.timeInterval + '/glucoseVariability'
        }).then(res => {
          this.gcv = res.data.toPrecision(4) + ' mmol/L'
        })
      },
      getAVG(){
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/' + this.measurementType + '/' + this.timeInterval + '/average'
        }).then(res => {
          switch(this.measurementType){
            case 'EXERCISE':
              this.avg = res.data.toPrecision(4) + ' %';
              break;
            case 'CGM':
              this.avg = res.data.toPrecision(4) + ' mmol/L';
              break;
            case 'MEALS':
              this.avg = res.data.toPrecision(4) + ' g CHO';
              break;
            case 'BASAL':
              this.avg = res.data.toPrecision(4) + ' mU/min';
              break;
            case 'BOLUS':
              this.avg = res.data.toPrecision(4) + ' U';
              break;
            default:
              this.avg = res.data.toPrecision(4);
          }
        })
      }
  },

  watch:{
    patientId(){
      this.getStdDiv()
      this.getAVG()
      this.getGCV()
      this.getMin()
      this.getMax() 
      this.getGMI()
    },
    measurementType() {
      this.getStdDiv()
      this.getAVG()
      this.getMin()
      this.getMax()
    },
    timeInterval() {
      this.getStdDiv()
      this.getAVG()
      this.getGCV()
      this.getMin()
      this.getMax() 
      this.getGMI()
    }
  }
};
</script>
<style></style>