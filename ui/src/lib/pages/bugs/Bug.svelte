<script lang="ts">
    import { onMount } from "svelte";
    import Navbar from "../../common/components/Navbar.svelte";
    import { BugService } from "../../core/services/BugService";
    import type { PathItem } from "../../common/types/PathItem";
    import BugHistory from "../../core/components/bugs/BugHistory.svelte";
    import type { Bug } from "../../core/types/Bug";

    export let path: PathItem[] = [];
    export let bugId: number = 0;

    const service = new BugService();
    let bug: Bug = {};
    let editedBug: Bug = {};

    let editing: boolean = false;

    function getBug(id: number) {
        service.getBug(id).then((response) => {
            bug = response;
        });
    }
    function saveBug(newBug: Bug) {
        service.saveBug(newBug).then((response) => {
            bug = response;
        });
    }
    function escapeDescription(description: string) {
        let tmpDiv = document.createElement("div");
        tmpDiv.innerText = description;
        return tmpDiv.innerHTML.replace("\n", "<br />");
    }
    onMount(() => {
        getBug(bugId);
        path = [
            { name: "Projects", ref: "/" },
            { name: "Project #" + bug.projectId, ref: "/" },
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
            class="text-3xl w-full mt-1 mb-3"
            bind:value={editedBug.title}
        />
        <textarea
            class="w-full"
            rows="6"
            bind:value={editedBug.description}
        />
    {:else}
        <p id="title" class="text-4xl mt-0 mb-4">{bug.title}</p>
        <hr class="w-1/4 mt-0" />
        <p class="mt-2">
            {@html bug.description
                ? escapeDescription(bug.description)
                : ""}
        </p>
    {/if}
</div>
<div class="flex flex-row flex-grow mt-4">
    <div class="w-2/3">
        <div class="w-full text-xl text-center">Bug data</div>
    </div>
    <div class="w-1/3">
        <BugHistory />
    </div>
</div>

<style>
    #title {
        font-weight: bold;
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

