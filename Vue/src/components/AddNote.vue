<template>
    <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#noteModal" style="margin: 5px">
     Select Interval
    </button>

    <div class="modal fade" id="noteModal" tabindex="-1" aria-labelledby="noteModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="noteModalLabel">Choose Time Interval</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>From:</p>
                    <b-form-textarea v-model="note" placeholder="Write a node..."></b-form-textarea>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" @click="handleSave()" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
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
      handleSubmit() {
       
      }

    }
  }
</script>