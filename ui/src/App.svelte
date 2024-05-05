<script lang="ts">
    import { Route, Router } from "svelte-routing";
    import Home from "./lib/pages/Home.svelte";
    import Secret from "./lib/pages/Secret.svelte";
    import ProtectedRoute from "./lib/auth/components/ProtectedRoute.svelte";
    import Auth from "./lib/pages/Auth.svelte";
</script>

<Router>
    <Route path="/"><Home/></Route>
    <Route path="/auth"><Auth/></Route>
    <ProtectedRoute path="/secret"><Secret/></ProtectedRoute>
</Router>

<style global>
  @tailwind base;
  @tailwind components;
  @tailwind utilities;
</style>
