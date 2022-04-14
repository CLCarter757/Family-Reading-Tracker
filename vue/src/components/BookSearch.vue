<template>
    <div>
        <form>
            <select name="search" id="drop-search">
                <option value="title">Title</option>
                <option value="author">Author</option>
                <option value="isbn">ISBN</option>
            </select>
            <input type="text"/>
            
        </form>
        <div>
            <button id="clear">Clear</button>
            <button id="search">Search</button>
        </div>


    </div>
</template>

<script>


export default {
    name: 'book-search'
    
}

</script>

<style>

</style>
