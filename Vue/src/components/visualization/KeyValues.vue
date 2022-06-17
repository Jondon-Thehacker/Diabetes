<!--Jonathan Max Michelsen, s204437-->
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
    getKeyValues(){
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/' + this.patientId + '/' + this.measurementType + '/' + this.timeInterval + '/summary/keyValues/5'
        }).then(res => {
          console.log(res.data)
          if(res.data != "" && res.data != undefined){
            switch(this.measurementType){
                case 'EXERCISE':
                  this.gcv = null
                  this.gmi = null
                  this.avg = res.data.keyValues.Average.toPrecision(4) + ' %';
                  this.stddiv = res.data.keyValues.Sd.toPrecision(4) + ' %';
                  this.min = res.data.keyValues.Min.toPrecision(4) + ' %';
                  this.max = res.data.keyValues.Max.toPrecision(4) + ' %';
                  break;
                case 'CGM':
                  this.gcv = res.data.keyValues.GV.toPrecision(4) + ' %'
                  this.gmi = res.data.keyValues.GMI.toPrecision(4) + ' mmol/mol'
                  this.avg = res.data.keyValues.Average.toPrecision(4) + ' mmol/L';
                  this.stddiv = res.data.keyValues.Sd.toPrecision(4) + ' mmol/L';
                  this.min = res.data.keyValues.Min.toPrecision(4) + ' mmol/L';
                  this.max = res.data.keyValues.Max.toPrecision(4) + ' mmol/L';
                  break;
                case 'MEALS':
                  this.gcv = null
                  this.gmi = null
                  this.avg = res.data.keyValues.Average.toPrecision(4) + ' g CHO';
                  this.stddiv = res.data.keyValues.Sd.toPrecision(4) + ' g CHO';
                  this.min = res.data.keyValues.Min.toPrecision(4) + ' g CHO';
                  this.max = res.data.keyValues.Max.toPrecision(4) + ' g CHO';
                  break;
                case 'BASAL':
                  this.gcv = null
                  this.gmi = null
                  this.avg = res.data.keyValues.Average.toPrecision(4) + ' mU/min';
                  this.stddiv = res.data.keyValues.Sd.toPrecision(4) + ' mU/min';
                  this.min = res.data.keyValues.Min.toPrecision(4) + ' mU/min';
                  this.max = res.data.keyValues.Max.toPrecision(4) + ' mU/min';
                  break;
                case 'BOLUS':
                  this.gcv = null
                  this.gmi = null
                  this.avg = res.data.keyValues.Average.toPrecision(4) + ' U';
                  this.stddiv = res.data.keyValues.Sd.toPrecision(4) + ' U';
                  this.min = res.data.keyValues.Min.toPrecision(4) + ' U';
                  this.max = res.data.keyValues.Max.toPrecision(4) + ' U';
                  break;
                default:
                  this.gcv = null
                  this.gmi = null                
                  this.avg = res.data.keyValues.Average.toPrecision(4)
                  this.stddiv = res.data.keyValues.Sd.toPrecision(4)
                  this.min = res.data.keyValues.Min.toPrecision(4)
                  this.max = res.data.keyValues.Max.toPrecision(4)
              }
          }
        })
    }
  },

  watch:{
    patientId(){
      //Null check
      if (this.patientId!=null && this.measurementType!=null && this.timeInterval!=null) {
        this.getKeyValues()
      }
    },
    measurementType() {
      //Null check
      if(this.patientId!=null && this.measurementType!=null && this.timeInterval!=null){
        this.getKeyValues()
      }
    },
    timeInterval(){
      //Null check
      if(this.patientId!=null && this.measurementType!=null && this.timeInterval!=null){
        this.getKeyValues()
      }

    }
  }
};
</script>
<style></style>