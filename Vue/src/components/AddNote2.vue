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
    data() {
        return {
            note: '',
            submittedNotes: [] //Push to database instead
        }
    },
    methods: {
        handleSubmit() {
            this.submittedNotes.push(this.note);
            console.log(this.submittedNotes);
            this.note = '';
        }
    },
}
</script>
