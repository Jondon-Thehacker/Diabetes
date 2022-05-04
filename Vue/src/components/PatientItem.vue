<template>
    <b-list-group-item @click="handlePatientClick" class="d-flex align-items-center clickable-patient">
                <b-avatar :badge="badgeNr!=0 ? badgeNr : undefined" badge-variant="danger" :text="findInitials(patientName)"></b-avatar>
                <span class="m-1 patient-name-span"> {{patientName}} </span>
                <!--<b-badge v-if="badgeNr != 0"> {{badgeNr}} </b-badge>-->
    </b-list-group-item>
</template>

<script>
export default {
    data() {
        return {
        
        }
    },
    methods: {
        findInitials(name) {
            return name.match(/(\b\S)?/g).join("").match(/(^\S|\S$)?/g).join("").toUpperCase()
        },
        handlePatientClick() {
            console.log("My name is " + this.patientName)
        }
    },
    props: {
        badgeNr: {
            type: Number,
            default: 0
        },
        patientName: {
            type: String,
            default: "Unkown"
        }
    }
}
</script>

<style scoped>

.patient-name-span {
    align-self: center;
    min-width: 80%;
    
}
</style>