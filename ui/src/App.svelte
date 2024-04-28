<script lang="ts">
    import { Route, Router } from "svelte-routing";
    import Home from "./lib/pages/Home.svelte";
    import SignIn from "./lib/pages/SignIn.svelte";
    import Secret from "./lib/pages/Secret.svelte";
    import ProtectedRoute from "./lib/auth/components/ProtectedRoute.svelte";

</script>

<Router>
    <Route path="/" component={Home} />
    <Route path="/signin" component={SignIn} />
    <ProtectedRoute path="/secret" component={Secret} />
</Router>

<style>
</style>
