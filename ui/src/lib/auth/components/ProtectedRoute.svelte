<script lang="ts">
    import { Route } from 'svelte-routing';
    import { authState } from '../state';
    import AccessDenied from '../../pages/AccessDenied.svelte';

    export let path: string;
</script>

{#if $authState.signedIn}
    <Route path={path}><slot></slot></Route>
{:else}
    <Route path={path}><AccessDenied/></Route>
{/if}
