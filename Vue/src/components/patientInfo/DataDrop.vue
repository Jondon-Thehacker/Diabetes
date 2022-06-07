<template>
<div>
  <!--<b-dropdown id="dropdown-1" :text="actualMeasurementType" class="datatypedrop">
    <b-dropdown-item @click = "handleMeasurementType('BASAL')">BASAL</b-dropdown-item>
    <b-dropdown-item @click = "handleMeasurementType('BOLUS')">BOLUS</b-dropdown-item>
    <b-dropdown-item @click = "handleMeasurementType('CGM')">CGM</b-dropdown-item>
    <b-dropdown-item @click = "handleMeasurementType('EXERCISE')">Exercise</b-dropdown-item>
    <b-dropdown-item @click = "handleMeasurementType('MEALS')">Meals</b-dropdown-item>
  </b-dropdown>-->

  <b-dropdown id="dropdown-1" :text="actualOption" class="datatypedrop">
    <b-dropdown-item v-for="(option, index) in options" :key="index"
      @click ="handleOption(option)">
      {{option}}
    </b-dropdown-item>
  </b-dropdown>
</div>
</template>

<script>
    export default {
      props: {
        options: Array,
        optionName: {
          type: String,
          default: ''
        }
      },

      data() {
        return {
          actualOption: "Choose " + this.optionName

        }
      },
      methods: {

        handleOption(option) {
          this.actualOption = option
          this.$emit("emitOption", this.actualOption)
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
    