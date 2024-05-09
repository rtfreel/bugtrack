<div class="container flex flex-row justify-center items-center mx-auto">
    <input
        id="search"
        class="w-3/4 mt-3"
        placeholder="Search..."
        on:input
    />
    <slot></slot>
</div>

<style>
    #search {
        border: 1px solid var(--color-surface-500);
    }
</style>
