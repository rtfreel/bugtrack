<script lang="ts">
    import { onMount } from "svelte";
    import Navbar from "../../common/components/Navbar.svelte";
    import { ProjectService } from "../../core/services/ProjectService";
    import type { PathItem } from "../../common/types/PathItem";
    import type { Project } from "../../core/types/Project";
    import BugList from "../../core/components/bugs/BugList.svelte";
    import ProjectHistory from "../../core/components/projects/ProjectHistory.svelte";

    export let path: PathItem[] = [];
    export let projectId: number;

    const service = new ProjectService();
    let project: Project = {};
    let editedProject: Project = {};

    let editing: boolean = false;

    function getProject(id: number) {
        service.getProject(id).then((response) => {
            project = response;
        });
    }
    function saveProject(newProject: Project) {
        service.saveProject(newProject).then((response) => {
            project = response;
        });
    }
    function escapeDescription(description: string) {
        let tmpDiv = document.createElement("div");
        tmpDiv.innerText = description;
        return tmpDiv.innerHTML.replace("\n", "<br />");
    }
    onMount(() => {
        getProject(projectId);
        path = [
            { name: "Projects", ref: "/" },
            { name: "Project #" + projectId, ref: "/project/" + projectId },
        ];
    });
</script>

<Navbar {path} />
<div class="container mx-auto pt-4">
    <div class="flex justify-between items-end min-h-7 my-0 p-0">
        <a
            class="{project.editable ? 'editbtn' : ''} text-xs my-0"
            href="#edit"
            title={project.editable
                ? editing
                    ? "Cancel editing"
                    : "Edit project"
                : ""}
            on:click|preventDefault={project.editable
                ? () => {
                      editedProject = { ...project };
                      editing = !editing;
                  }
                : () => {}}
        >
            <span>
                {#if project.editable}
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
                PROJECT #{projectId}
            </span>
        </a>
        {#if editing}
            <button
                class="primary savebtn"
                on:click={() => {
                    saveProject(editedProject);
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
            bind:value={editedProject.title}
        />
        <textarea
            class="w-full"
            rows="6"
            bind:value={editedProject.description}
        />
    {:else}
        <p id="project-title" class="text-4xl mt-0 mb-4">{project.title}</p>
        <hr class="w-1/4 mt-0" />
        <p class="mt-2">
            {@html project.description
                ? escapeDescription(project.description)
                : ""}
        </p>
    {/if}
</div>
<div class="projectdata flex flex-row mt-4">
    <div class="w-2/3">
        <BugList />
    </div>
    <div class="w-1/3">
        <ProjectHistory />
    </div>
</div>

<style>
    #project-title {
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
    .projectdata {
        flex-grow: 1;
    }
</style>
