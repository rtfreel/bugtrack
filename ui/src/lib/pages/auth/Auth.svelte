<script lang="ts">
    import SignIn from "../../auth/components/forms/SignIn.svelte";
    import SignUp from "../../auth/components/forms/SignUp.svelte";
    import { SignOptions } from "../../auth/types/SignOptions";

    export let sign = SignOptions.SIGNIN;
    export let errorMessage = "";
</script>

<div class="container h-full flex flex-col items-center mx-auto">
    <h1 class="title text-5xl font-bold pt-8 my-4">Bugtrack</h1>
    <nav
        id="elements-nav"
        class="my-2"
    >
        {#each Object.values(SignOptions) as signOption}
            <a
                class="tab mx-1 {signOption === sign ? 'active-tab' : ''}"
                on:click={() => (sign = signOption, errorMessage = "")}
                href="#{ signOption.replace(/ /g, '-') }"
            >
                {signOption}
            </a>
        {/each}
    </nav>
    {#if sign === SignOptions.SIGNIN}
        <SignIn on:fail={(e) => (errorMessage = e.detail)} />
    {:else}
        <SignUp />
    {/if}
    {#if errorMessage}
        <p class="text-red-500 mt-4">{errorMessage}</p>
    {/if}
</div>

<style>
    .title {
        color: var(--color-surface-500);
    }
    .tab {
        width: 150px;
        font-size: 1.2rem;
        text-decoration: none;
        color: var(--color-surface-400);
        border-bottom: 2px solid var(--color-surface-400);
        cursor: pointer;
        font-weight: normal;
        transition: all 0.15s ease-in-out;
    }
    .active-tab {
        color: var(--color-primary-500);
        border-bottom: 1px solid var(--color-primary-500);
        transition: all 0.15s ease-in-out;
    }
</style>
