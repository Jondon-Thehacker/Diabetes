<!--Emil Pontoppidan Rasmussen, s204441-->
<template>
    <div class="emaildiv">
        <!--Button is only functional when an email is selected-->
        <b-button id="sendEmail" v-if="patientEmail != null" :href="mailto + patientEmail"> Email </b-button>
        <b-button v-if="patientEmail === null"> Email </b-button>
    </div> 

</template>

<script>
export default {
    props: {
        patientEmail: String
    },
    data() {
        return {
            mailto: "mailto:",
        }
    }

}
</script>

<style scoped>

.emaildiv{
    text-align: center;
    padding: 10px;

}
</style>
