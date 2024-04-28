<script lang="ts">
    import { Route } from 'svelte-routing';
    import { authState } from '../state';
    import accessDenied from '../../pages/AccessDenied.svelte';

    export let path: string;
    export let component: any;
</script>

{#if $authState.signedIn}
    <Route path={path} component={component} />
{:else}
    <Route path={path} component={accessDenied} />
{/if}
