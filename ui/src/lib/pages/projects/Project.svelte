<script lang="ts">
    import { onMount } from "svelte";
    import Navbar from "../../common/components/Navbar.svelte";
    import { ProjectService } from "../../core/services/ProjectService";
    import type { PathItem } from "../../common/types/PathItem";
    import type { Project } from "../../core/types/Project";

    export let path: PathItem[] = [];
    export let projectId: number;

    const service = new ProjectService();
    let project: Project = {};
    onMount(() => {
        service.getProject(projectId).then((response) => {
            project = response;
        })
        path = [
            { name: "Projects", ref: "/" },
            { name: "Project #" + projectId, ref: "/project/" + projectId },
        ];
    });
</script>

<Navbar {path} />

<div>
    <h1>{ project.title }</h1>
    <p>{ project.description }</p>
</div>
