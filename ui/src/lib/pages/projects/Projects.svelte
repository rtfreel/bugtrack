<script lang="ts">
    import Navbar from "../../common/components/Navbar.svelte";
    import Searchbar from "../../common/components/Searchbar.svelte";
    import { ProjectService } from "../../core/services/ProjectService";
    import type { PathItem } from "../../common/types/PathItem";
    import type { Project } from "../../core/types/Project";
    import { onMount } from "svelte";
    import ProjectCard from "../../core/components/projects/ProjectCard.svelte";

    export let path: PathItem[] = [];
    export let owned: boolean = false;

    const service = new ProjectService();
    let projects: Project[] = [];
    function refreshProjects(query?: string) {
        const listQuery = owned
            ? service.getOwnedProjects
            : service.getProjects;
        listQuery(query).then((response) => {
            projects = response;
        });
    }
    function onSearch(e: Event) {
        const input = e.target as HTMLInputElement;
        refreshProjects(input.value);
    }

    onMount(() => {
        refreshProjects();
    });
</script>

<Navbar {path} />
<Searchbar on:input={onSearch}>
    <!-- TODO: project creation -->
    <button class="primary mt-3 ms-4">New +</button>
</Searchbar>
<div class="container grid grid-cols-3 mx-auto mt-3">
    {#each projects as project}
        <ProjectCard {project} />
    {/each}
</div>
