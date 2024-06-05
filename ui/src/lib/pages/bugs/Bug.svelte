<script lang="ts">
    import { onMount } from "svelte";
    import Navbar from "../../common/components/Navbar.svelte";
    import { BugService } from "../../core/services/BugService";
    import type { PathItem } from "../../common/types/PathItem";
    import BugHistory from "../../core/components/bugs/BugHistory.svelte";
    import type { Bug } from "../../core/types/Bug";
    import { escapeDescription } from "../../common/utils/formatting";
    import type { Status } from "../../core/types/Status";
    import StepList from "../../core/components/steps/StepList.svelte";
    import type { BugHistory as BugHistoryItem } from "../../core/types/Bug";

    export let path: PathItem[] = [];
    export let bugId: number = 0;

    const service = new BugService();
    let bug: Bug = {};
    let editedBug: Bug = {};
    let history: BugHistoryItem[] = [];

    let editing: boolean = false;

    function onStatusChange(e: Event) {
        editedBug = { ...bug };
        editing = true;
        const target = e.target as HTMLSelectElement;
        editedBug.status = target.value as Status;
    }

    async function getBug(id: number) {
        const newBug = await service.getBug(id);
        bug = {...newBug};
        history = await service.getHistory(id);

    }
    async function saveBug(newBug: Bug) {
        const savedBug = await service.saveBug(newBug);
        bug = {...savedBug};
        history = await service.getHistory(bugId);
        window.location.reload();
    }
    onMount(async () => {
        await getBug(bugId);
        path = [
            { name: "Projects", ref: "/" },
            {
                name: "Project #" + bug.projectId,
                ref: "/project/" + bug.projectId,
            },
            { name: "Bug #" + bugId, ref: "/bug/" + bugId },
        ];
    });
</script>

<Navbar {path} />
<div class="container mx-auto pt-4">
    <div class="flex justify-between items-end min-h-7 my-0 p-0">
        <a
            class="{bug.editable ? 'editbtn' : ''} text-xs my-0"
            href="#edit"
            title={bug.editable
                ? editing
                    ? "Cancel editing"
                    : "Edit project"
                : ""}
            on:click|preventDefault={bug.editable
                ? () => {
                      editedBug = { ...bug };
                      editing = !editing;
                  }
                : () => {}}
        >
            <span>
                {#if bug.editable}
                    <i
                        class="fa-solid
                        fa-{editing ? 'xmark' : 'pencil'}
                        {editing ? 'text-red-500' : ''}"
                    />
                {:else}
                    <i class="fa-regular fa-folder" />
                {/if}
            </span>
            <span class="ms-1">
                Bug #{bugId}
            </span>
        </a>
        {#if editing}
            <button
                class="primary savebtn"
                on:click={() => {
                    saveBug(editedBug);
                    editing = false;
                }}
            >
                Save
            </button>
        {/if}
    </div>
    {#if editing}
        <input
            type="text"
            class="text-3xl w-full mt-1"
            bind:value={editedBug.title}
        />
    {:else}
        <p id="title" class="text-4xl mt-0 mb-4">{bug.title}</p>
        <hr class="w-1/4 mt-0" />
    {/if}
    {#if bug.editable}
        <select
            id="statusdd"
            class="mt-3"
            on:change={onStatusChange}
            disabled={!bug.editable}
            value={bug.status}
        >
            <option value="NEW">New</option>
            <option value="ACTIVE">Active</option>
            <option value="TESTING">Testing</option>
            <option value="CLOSED">Closed</option>
        </select>
        <!-- TODO: Reporter -->
    {:else}
        <p id="status" class="mt-3">{bug.status}</p>
    {/if}
    {#if editing}
        <textarea
            class="w-full mt-3"
            rows="6"
            bind:value={editedBug.description}
        />
    {:else}
        <p class="mt-2">
            {@html bug.description ? escapeDescription(bug.description) : ""}
        </p>
    {/if}
</div>
<div class="flex flex-row flex-grow mt-4">
    <div id="slcontainer" class="w-2/3">
        <StepList
            steps={bug.steps}
            editable={bug.editable || false}
            on:stepsUpdated={(e) => {
                editedBug = { ...bug };
                editedBug.steps = e.detail;
                saveBug(editedBug);
            }}
        />
    </div>
    <div class="w-1/3">
        <BugHistory {history} />
    </div>
</div>

<style>
    #title {
        font-weight: bold;
        color: var(--color-primary-400);
    }
    #statusdd {
        padding: 5px;
        border-radius: 3px;
        background-color: var(--color-surface-200);
    }
    #status {
        color: var(--color-primary-400);
    }
    .editbtn {
        transition: all 0.15s ease-in-out;
    }
    .editbtn:hover {
        color: var(--color-primary-400);
    }
    .savebtn {
        padding: 0 1rem;
    }
</style>
