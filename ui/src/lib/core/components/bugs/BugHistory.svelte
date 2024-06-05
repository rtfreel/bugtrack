<script lang="ts">
    import { formatDate } from "../../../common/utils/formatting";
    import type { BugHistory } from "../../types/Bug";

    export let history: BugHistory[];

    $: {
        if (history !== undefined) {
            if (history.length != 0) {
                history[0].isCreated = true;
                if (history.length > 1) {
                    for (let i = 1; i < history.length; i++) {
                        history[i].changedFields = [];
                        if (history[i].status != history[i - 1].status) {
                            history[i].changedFields?.push("status");
                        }
                        if (history[i].title != history[i - 1].title) {
                            history[i].changedFields?.push("title");
                        }
                        if (
                            history[i].description != history[i - 1].description
                        ) {
                            history[i].changedFields?.push("description");
                        }
                        if (history[i].stepsUpdated) {
                            history[i].changedFields?.push("steps");
                        }
                    }
                }
            }
        }
    }
</script>

<div class="flex flex-col h-full">
    <div class="w-full text-xl text-center">History</div>
    <div id="historycol" class="container flex-grow mt-2">
        {#each [...history].reverse() as item (item.id)}
            <div class="history-card p-2">
                <div>
                    User: {item.user?.username}
                    ({item.user?.firstName}
                    {item.user?.lastName})
                </div>
                {#if item.isCreated}
                    <div>Reported the bug</div>
                {:else}
                    <div>
                        Changed: {(item.changedFields?.join(", ").length || 0) > 0
                            ? item.changedFields?.join(", ")
                            : "steps"}
                    </div>
                {/if}
                <div>At: {formatDate(item.changeDateTime)}</div>
            </div>
        {/each}
    </div>
</div>

<style>
    #historycol {
        border-left: 1px solid var(--color-surface-500);
    }
    .history-card {
        border: 1px solid var(--color-surface-500);
        border-radius: 8px;
        margin: 8px;
    }
</style>
