<template>
    <div>
        <b-container class="bv-example-row" style="padding: 0" fluid>
            <b-row class="top-bar2">
                <b-col>Patient Name</b-col>
                <b-col>Email</b-col>
                <b-col>data</b-col>
            </b-row>
            <b-row>
                <b-col>
                    <visualization-box/>
                </b-col>
                <b-col cols="1" class="notes">
                    <button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight" style="margin: 0; position: relative; top: 40%">Toggle notes</button>

                    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
                        <div class="offcanvas-header">
                            <h5 id="offcanvasRightLabel">Notes</h5>
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
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import VisualizationBox from './VisualizationBox.vue'

export default {
    components: {
        VisualizationBox
    }
}
</script>

<style>

.notes {
    margin-top: 20px;
    margin-right: 0px;
    background-color:cornflowerblue;
}

.top-bar2 {
    background-color: rgb(66, 117, 119);
}


    
</style>