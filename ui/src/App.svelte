<script lang="ts">
    import { Route, Router } from "svelte-routing";
    import Projects from "./lib/pages/projects/Projects.svelte";
    import ProtectedRoute from "./lib/auth/components/ProtectedRoute.svelte";
    import Auth from "./lib/pages/auth/Auth.svelte";
    import Project from "./lib/pages/projects/Project.svelte";
</script>

<Router>
    <Route path="/auth"><Auth /></Route>
    <ProtectedRoute path="/">
        <Projects
            path={[
                { name: "Projects", ref: "/" },
                { name: "All", ref: "/" },
            ]}
        />
    </ProtectedRoute>
    <ProtectedRoute path="/owned">
        <Projects
            path={[
                { name: "Projects", ref: "/" },
                { name: "Owned", ref: "/owned" },
            ]}
            owned={true}
        />
    </ProtectedRoute>
    <ProtectedRoute path="/project/:projectId" let:params>
        <Project path={[{ name: "Projects", ref: "/" }]} projectId={+params.projectId} />
    </ProtectedRoute>
</Router>

<style global>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;
</style>
