<template>
    <p>{{patientId}}</p>
    <p>{{timeInterval}}</p>
</template>

<script>
export default {
    props: {
        patientId: Number,
        doctorId: Number,
        timeInterval: String
    },

methods: {
    getDataInTimeInterval() {
      this.axios({
        method: "get",
        url:
          "http://localhost:8080/api/v1/Doctors/" +
          this.doctorId +
          "/" +
          this.patientId +
          "/CGM/" +
          this.timeInterval +
          "/summary/barChart/120",
      }).then((res) => {
            console.log(res.data);
           // this.timeAbove = res.data;
      });
    }
},
 watch: {
    patientId() {
      this.getDataInTimeInterval()
    },
    
    timeInterval() {
      this.getDataInTimeInterval()
    }
  },

    
}
</script>

<style>
    
</style>