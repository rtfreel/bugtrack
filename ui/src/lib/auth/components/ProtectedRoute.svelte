<script lang="ts">
    import { Route } from "svelte-routing";
    import { authState } from "../state";
    import Auth from "../../pages/auth/Auth.svelte";

    export let path: string;
</script>

{#if $authState.signedIn}
    <Route {path} let:params>
        <slot {params} />
    </Route>
{:else}
    <Route {path}><Auth errorMessage="Please, sign in first!" /></Route>
{/if}
