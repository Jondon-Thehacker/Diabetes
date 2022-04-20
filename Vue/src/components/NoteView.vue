<template>
    <button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight" style="margin: 0; position: relative; top: 40%">Toggle notes</button>

    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
        <div class="offcanvas-header">
            <h5 id="offcanvasRightLabel">Notes</h5>
            <button style="background: blue">ADD</button>
            <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            Note 1 <br> Lorem ipsum dolor sit amet consectetur adipisicing elit. Veritatis harum nulla rerum quis sed excepturi. Temporibus explicabo ab fugit, nam amet illo incidunt tenetur nemo deleniti. Voluptate voluptatum vero sit.
            <hr><br> Note 2 <br> Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellat voluptates quos reprehenderit porro perferendis totam ab laudantium culpa autem ipsam voluptate, fugiat doloribus nam nesciunt. Quod laudantium numquam officiis eum.
            <hr><br> Note 3 <br> Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellat voluptates quos reprehenderit porro perferendis totam ab laudantium culpa autem ipsam voluptate, fugiat doloribus nam nesciunt. Quod laudantium numquam officiis eum.
            <hr><br> Note 4 <br> Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellat voluptates quos reprehenderit porro perferendis totam ab laudantium culpa autem ipsam voluptate, fugiat doloribus nam nesciunt. Quod laudantium numquam officiis eum.
            <hr><br> Note 5 <br> Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellat voluptates quos reprehenderit porro perferendis totam ab laudantium culpa autem ipsam voluptate, fugiat doloribus nam nesciunt. Quod laudantium numquam officiis eum.    
        </div>
    </div>
</template>

<script>
export default {
    
}
</script>

<style>
    
</style>