<script lang="ts">
    import type { Project } from "../../types/Project";

    export let project: Project;
</script>

<a class="project-card px-4 py-2" href="/project/{project.id}">
    <div class="project-title single-line font-bold">
        <i class="fa-regular fa-folder" /> #{project.id}
        {project.title}
    </div>
    <div class="single-line">{project.description}</div>
    <div class="single-line">
        Owner: <span class="owner">
            {project.owner?.firstName +
                " " +
                project.owner?.lastName +
                " (" +
                project.owner?.username +
                ")"}
        </span>
    </div>
</a>

<style>
    .project-card {
        border: 1px solid var(--color-surface-500);
        border-radius: 8px;
        margin: 8px;
        transition: all 0.15s ease-in-out;
    }
    .project-card:hover {
        border-color: var(--color-primary-400);
        border-radius: 8px;
        margin: 8px;
    }
    .project-card:hover > .project-title {
        color: var(--color-primary-400);
    }
    .single-line {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
</style>
