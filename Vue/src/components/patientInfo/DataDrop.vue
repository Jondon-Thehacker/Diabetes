<template>
<div>
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
        //Array containing the different options available in the dropdown menu
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
        //Change the current option and emit that to parent
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
    