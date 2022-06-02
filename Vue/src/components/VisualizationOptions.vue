<template>
<div>
  <b-dropdown id="dropdown-2" :text="actualVisualizationOption" class="datatypedrop">
    <b-dropdown-item @click = "handleVisualizationOption('linechart')">Linechart</b-dropdown-item>
    <b-dropdown-item @click = "handleVisualizationOption('avgLinechart')">Average Linechart</b-dropdown-item>
    <b-dropdown-item @click = "handleVisualizationOption('not linechart')">Not linechart</b-dropdown-item>
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
    