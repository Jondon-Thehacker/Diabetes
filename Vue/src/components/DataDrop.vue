<template>
<div>
  <b-dropdown id="dropdown-1" :text="actualMeasurementType" class="datatypedrop">
    <b-dropdown-item @click = "handleMeasurementType('BASAL')">BASAL</b-dropdown-item>
    <b-dropdown-item @click = "handleMeasurementType('BOLUS')">BOLUS</b-dropdown-item>
    <b-dropdown-item @click = "handleMeasurementType('CGM')">CGM</b-dropdown-item>
    <b-dropdown-item @click = "handleMeasurementType('EXERCISE')">Exercise</b-dropdown-item>
    <b-dropdown-item @click = "handleMeasurementType('MEALS')">Meals</b-dropdown-item>
  </b-dropdown>
</div>
</template>

<script>
    export default {

      data() {
        return {
          actualMeasurementType: "Choose type" 
        }
      },
      methods: {

        handleMeasurementType(measurementType) {
          this.actualMeasurementType = measurementType
          this.$emit("emitMeasurementType", this.actualMeasurementType)
        }

      }

    }  
</script>

<style scoped>
.datatypedrop{
    margin: 0;
    position: relative;
    top: 50%;

    border: none;
    text-align: center;
    padding: 10px;
    font-size: 100%;
    display: inline-block;
}

</style>
    