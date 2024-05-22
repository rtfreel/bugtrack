<script lang="ts">
    import { onMount } from "svelte";
    import Searchbar from "../../../common/components/Searchbar.svelte";
    import { BugService } from "../../services/BugService";
    import type { Bug } from "../../types/Bug";
    import { statusList } from "../../types/Status";
    import BugCard from "./BugCard.svelte";

    export let projectId: number = 0;
    export let columns: number = 2

    const service = new BugService();
    let bugs: Bug[] = [];

    function refreshBugs(query?: string) {
        let bugsQuery = !projectId
            ? service.getReportedBugs(statusList, query)
            : service.getProjectBugs(projectId, statusList, query);
        bugsQuery.then((response) => {
            bugs = response;
        });
    }
    function onSearch(e: Event) {
        const input = e.target as HTMLInputElement;
        refreshBugs(input.value);
    }

    onMount(() => {
        refreshBugs();
    });
</script>

<div>
    <Searchbar on:input={onSearch}>
        <!-- TODO: bug creation -->
        <button class="primary mt-3 ms-4">New +</button>
    </Searchbar>
    <div class="container grid {columns === 2 ? 'grid-cols-2' : 'grid-cols-3'} mx-auto mt-3">
        {#each bugs as bug}
            <BugCard {bug} />
        {/each}
    </div>
</div>
