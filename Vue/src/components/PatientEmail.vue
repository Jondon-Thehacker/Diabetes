<template>
    <div class="emaildiv"> 
        <b-button :href="mailto + patientEmail"> Email </b-button>
    </div> 

</template>



<script>
export default {
    props: {
        patientEmail: String
    },
    data() {
        return {
            mailto: "mailto:"
        }
    },

    
}
</script>

<style scoped>

.emaildiv{
    text-align: center;
    padding: 10px;

}
</style>
