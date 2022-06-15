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
        avg: null,
        x: null
      }
  },

  methods: {
    //GET-call for retrieving standard deviation and displaying the correct unit
      getStdDiv(){
        this.axios({
            method: 'get',
            url: 'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/' + this.patientId +'/' + this.measurementType + '/' + this.timeInterval +'/standardDeviation'
        }).then(res => {
          //Checking if any data is in the time interval
          if(res.data != "NaN") {
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

          }
        })  
      },
      //GET-call for retrieving GMI and displaying the correct unit
      getGMI() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/CGM/' + this.timeInterval + '/GMI'
        }).then(res =>{
          //Checking if any data is in the time interval
          if(res.data != "NaN"){
            this.gmi = res.data.toPrecision(4) + ' mmol/mol'
          }
        })
      },
      //GET-call for retrieving minimum value and displaying the correct unit
      getMin() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/' + this.measurementType + '/' + this.timeInterval + '/min'
        }).then(res => {
          //Checking if any data is in the time interval
          if(res.data != -1){
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

          }
        })
      },
      //GET-call for retrieving maximum value and displaying the correct unit
      getMax() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/' + this.measurementType + '/' + this.timeInterval + '/max'
        }).then(res => {
          //Checking if any data is in the time interval
          if(res.data != -1) {
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

          }
        })
      },
      //GET-call for retrieving glucose variation and displaying the correct unit
      getGCV() {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/CGM/' + this.timeInterval + '/glucoseVariability'
        }).then(res => {
          //Checking if any data is in the time interval
          if(res.data != "NaN") {
            this.gcv = res.data.toPrecision(4) + ' mmol/L'
          }
        })
      },
      //GET-call for retrieving average and displaying the correct unit
      getAVG(){
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/'+ this.doctorId +'/'+ this.patientId +'/' + this.measurementType + '/' + this.timeInterval + '/average'
        }).then(res => {
          //Checking if any data is in the time interval
          if (res.data != "NaN") {
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

          }
        })
      }
  },

  watch:{
    patientId(){
      //Null check
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
      //Null check
      if(this.patientId!=null && this.measurementType!=null && this.timeInterval!=null){
        this.getStdDiv()
        this.getAVG()
        this.getGCV()
        this.getMin()
        this.getMax() 
        this.getGMI() 
      }
    },
    timeInterval(){
      //Null check
      if(this.patientId!=null && this.measurementType!=null && this.timeInterval!=null){
        this.getStdDiv()
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