<template>
<div>
  <b-dropdown id="dropdown-1" text="Dropdown Button" class="datatypedrop" style="margin-top: 5px">
    <b-dropdown-item>BASAL</b-dropdown-item>
    <b-dropdown-item>BOLUS</b-dropdown-item>
    <b-dropdown-item>CGM</b-dropdown-item>
    <b-dropdown-item>Exercise</b-dropdown-item>
    <b-dropdown-item>Meals</b-dropdown-item>
  </b-dropdown>
</div>
</template>

<script>
    export default {

    }  
</script>

<style scoped>
.datatypedrop{
    margin: 0;
    position: relative;
    top: 50%;

    border: none;
    text-align: center;
    padding: 4px 10px;
    font-size: 100%;
    display: inline-block;
}

</style>
    