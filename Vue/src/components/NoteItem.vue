<template>
    <hr>
    <button @click="deleteNote" type="button" class="btn-close delete-note-btn" aria-label="Close"></button>
    <br> DoctorId: {{author}} - {{ formattedDate }} <br> {{note}}
</template>

<script>

export default{

    props: {
        noteId: Number,
        doctorId: Number,
        patientId: Number,
        author: Number,
        date: String,
        note: String
    },

    methods: {
        deleteNote(){
            
            this.axios.delete(
                'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/patients/' + this.patientId + '/Notes/' + this.noteId
            ).then(res => {
                console.log(res.data)
                this.notes = res.data
                this.$emit("updateNotes")
            })  
        }
    },

    computed: {
        formattedDate(){
            let year = this.date.slice(0,4)
            let day = this.date.slice(8,10)
            let month = this.date.slice(5,7)
            let time = this.date.slice(11,16)
            return day + '/' + month + '-' + year + ' ' +  time
        }
    }
}

</script>

<style>

.delete-note-btn {
    float: right;
    padding-right: 0;
}

</style>



