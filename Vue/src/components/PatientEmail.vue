<template>
    <div> 
        <div class = "patientemail"> Put patient name here </div>
        <b-button href = "mailto: morerdejlig@gmail.com" class = "patientemail emailbutton"> Email </b-button>
    </div> 

</template>



<script>
export default {
    
}
</script>




<style scoped>
.patientemail{
    display: inline-block;
    padding: 1rem 1rem;
    vertical-align: middle;
}
.emailbutton{
    border: none;
    text-align: center;
    padding: 4px 10px;
    font-size: 100%;
}
</style>
