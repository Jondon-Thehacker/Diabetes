<template>
    <button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight" style="margin: 0; position: relative; top: 40%">Toggle notes</button>

    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
        <div class="offcanvas-header">
            <h5 id="offcanvasRightLabel">Notes</h5>
            <add-note/>
            <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <hr><br> Note 2 <br> Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellat voluptates quos reprehenderit porro perferendis totam ab laudantium culpa autem ipsam voluptate, fugiat doloribus nam nesciunt. Quod laudantium numquam officiis eum.
            <hr><br> Note 3 <br> Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellat voluptates quos reprehenderit porro perferendis totam ab laudantium culpa autem ipsam voluptate, fugiat doloribus nam nesciunt. Quod laudantium numquam officiis eum.
            <hr><br> Note 4 <br> Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellat voluptates quos reprehenderit porro perferendis totam ab laudantium culpa autem ipsam voluptate, fugiat doloribus nam nesciunt. Quod laudantium numquam officiis eum.
            <hr><br> Note 5 <br> Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellat voluptates quos reprehenderit porro perferendis totam ab laudantium culpa autem ipsam voluptate, fugiat doloribus nam nesciunt. Quod laudantium numquam officiis eum.    
        </div>
    </div>
</template>

<script>
import AddNote from './AddNote.vue'
export default {
  components: { AddNote },
    
}
</script>

<style>
    
</style>