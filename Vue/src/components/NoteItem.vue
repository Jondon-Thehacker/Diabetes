<template>
    <hr>
    <button type="button" class="btn-close delete-note-btn" aria-label="Close"></button>
    <br> {{author}} - {{date}} <br> {{note}}
</template>

<script>

export default{
    props: {
        patientId: Number,
        author: Number,
        date: String,
        note: String
    }
}

</script>

<style>

.delete-note-btn {
    float: right;
    padding-right: 0;
}

</style>



