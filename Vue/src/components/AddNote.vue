<template>
<div>
  <b-button v-b-modal.modal-note>Add a note</b-button>

  <b-modal id="modal-note" title="Add a note" ok-title="Add note" @ok="handleSubmit">
      <b-form-textarea v-model="note" rows="8" placeholder="Write a note..."></b-form-textarea>
  </b-modal>
</div>

</template>

<script>
export default {
    props: {
        doctorId: Number,
        patientId: Number
    },
    data() {
        return {
            note: '',
        }
    },
    methods: {
        handleSubmit() {
            this.axios.post('http://localhost:8080/api/v1/Doctors/' + this.doctorId + '/patients/' + this.patientId + '/Notes', {
                note: this.note,
                date: new Date(),
                doctorId: this.doctorId,
                patientId: this.patientId,
            }).then(() => {
                    this.note = '';
                    console.log("Note added")
                    this.$emit("updateNotes")
            })
            
           
        
        }
    }
}
</script>
