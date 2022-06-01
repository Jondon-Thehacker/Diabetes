<template>
    <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#intervalModal" style="margin: 5px">
     Select Interval
    </button>

    <div class="modal fade" id="intervalModal" tabindex="-1" aria-labelledby="intervalModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="intervalModalLabel">Choose Time Interval</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>From:</p>
                    <b-form-input v-model="dateFrom" type="date" style="margin-bottom: 3px"></b-form-input>
                    <b-form-input v-model="timeFrom" type="time" style="margin-bottom: 3px"></b-form-input>

                    <p>To:</p>
                    <b-form-input v-model="dateTo" type="date" style="margin-bottom: 3px"></b-form-input>
                    <b-form-input v-model="timeTo" type="time" style="margin-bottom: 3px"></b-form-input>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" @click="handleSave()" class="btn btn-primary" data-bs-dismiss="modal">Save changes</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            dateFrom: null,
            timeFrom: null,
            dateTo: null,
            timeTo: null
        }
    },
    emits: ['timeIntervalEmit'],
    
    methods: {
        handleSave() {
            let timeInterval = this.dateFrom + ' ' + this.timeFrom + '/' + this.dateTo + ' ' + this.timeTo
            this.$emit("timeIntervalEmit", timeInterval)
            console.log("!!!!!!")
        }
    },
}
</script>

<style>

</style>