<template>
    <div class="note-button">
        <button v-if="patientId != null" @click="getNotes" class="btn btn-secondary" type="button" data-bs-toggle="offcanvas" 
        data-bs-target="#offcanvasRight" aria-controls="offcanvasRight" style="margin: 0; position: relative; top: 40%">
            Toggle notes
        </button>

        <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
            <div class="offcanvas-header">
                <h5 id="offcanvasRightLabel">Notes</h5>
                <add-note @updateNotes="getNotes()" :patientId="patientId" :doctorId="doctorId"/>
                <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
            </div>

            <div class="offcanvas-body"> 
                <note-item v-for="(note, index) in notes" :key="index"
                @updateNotes="getNotes()"
                :doctorId="doctorId"
                :author="note.doctor.doctorId"
                :patientId="patientId"
                :date="note.date"
                :note="note.note"
                :noteId="note.noteId"
                ></note-item>
            </div>

        </div>
    </div>
</template>

<script>
import AddNote from './AddNote.vue'
import NoteItem from './NoteItem.vue'

export default {
    props: {
        patientId: Number,
        doctorId: Number,
        patientName: String
    },

    components: {
        AddNote,
        NoteItem
    },

    data(){
        return{
            notes: []
        }
    },

    methods: {
        //GET-call to retrieve the patient's notes
        getNotes() {
            this.axios({
                method: 'get',
                url: 'http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/patients/' + this.patientId + '/Notes',
            }).then(res => {
                //Reverse the result array, so that newest notes are at the top
                this.notes = (res.data).reverse()
            })  
        }
    },
}

</script>

<style scoped>
.note-button{
    display: inline-block;
    padding: 10px;
    vertical-align: middle;
}
</style>