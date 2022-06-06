<template>
    <hr>
    <!--<b-button type="button" v-b-modal.delete-modal :src="trashUrl"  class="btn-close delete-note-btn" aria-label="Close">
    </b-button>-->
    <b-img class="delete-note-btn" width="20" height="20" :src="trashUrl" v-b-modal="'modal-'+this.noteId"></b-img>
 
    <b-modal :id="'modal-'+this.noteId" title="Delete" ok-title="Yes" @ok="deleteNote(this.noteId)"> 
    <p class="my-4"> Are you sure you want to delete this note? </p>
    </b-modal>

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
    data() {
        return {
            trashUrl: require('@/assets/TrashCan.png')
        }
    },

    methods: {
        deleteNote(noteId){
            console.log()
            this.axios.delete(
                'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/patients/' + this.patientId + '/Notes/' + noteId
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
    min-width:30px;
    min-height:30px;
    /*background: url("@/assets/TrashCan.png")*/
}

.delete-note-btn:hover{
    background-color:rgb(192, 189, 189);
    border-radius: 5px;
}

</style>



