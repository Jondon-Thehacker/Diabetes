<template>
  <div>
    <b-button v-b-modal.modal-prevent-closing>Add note</b-button>

    <b-modal
      id="modal-prevent-closing"
      ref="modal"
      title="Add a note"
      @show="resetModal"
      @hidden="resetModal"
      @ok="handleOk"
    >
      <form ref="form" @submit.stop.prevent="handleSubmit">
        <b-form-group
          label="Note"
          label-for="note-input"
          :state="noteState"
        >
          <b-form-textarea
            id="note-input"
            v-model="note"
            :state="noteState"
            required
          ></b-form-textarea>
        </b-form-group>
      </form>
    </b-modal>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        note: '',
        submittedNotes: []
      }
    },
    
    methods: {

      checkFormValidity() {
        const valid = this.$refs.form.checkValidity()
        return valid
      },

      resetModal() {
        this.note = ''
      },

      handleOk(bvModalEvent) {
        // Prevent modal from closing
        bvModalEvent.preventDefault()
        // Trigger submit handler
        this.handleSubmit()
      },

      handleSubmit() {
        // Exit when the form isn't valid
        if (!this.checkFormValidity()) {
          return
        }

        // Push the name to submitted names
        this.submittedNotes.push(this.note)
        // Hide the modal manually
        this.$nextTick(() => {
          this.$bvModal.hide('modal-prevent-closing')
        })
      }

    }
  }
</script>