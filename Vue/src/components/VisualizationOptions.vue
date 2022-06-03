<template>
<div>
  <b-dropdown id="dropdown-2" :text="actualVisualizationOption" class="datatypedrop">
    <b-dropdown-item @click = "handleVisualizationOption('General Information')">General information</b-dropdown-item>
    <b-dropdown-item @click = "handleVisualizationOption('Average Linechart')">Average Linechart</b-dropdown-item>
    <b-dropdown-item @click = "handleVisualizationOption('TIR Barcharts')">TIR Barcharts</b-dropdown-item>
  </b-dropdown>
</div>
</template>

<script>
    export default {

      data() {
        return {
          actualVisualizationOption: "Choose visualization" 
        }
      },
      methods: {

        handleVisualizationOption(visualizationOption) {
          this.actualVisualizationOption = visualizationOption
          this.$emit("emitVisualizationOption", this.actualVisualizationOption)
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
    