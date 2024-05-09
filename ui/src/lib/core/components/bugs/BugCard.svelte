<script lang="ts">
    import type { Bug } from "../../types/Bug";

    export let bug: Bug;
</script>

<a class="bug-card px-4 py-2" href="/project/{bug.id}">
    <div class="project-title single-line font-bold">
        <i class="fa-solid fa-bug" /> #{bug.id}
        {bug.title}
    </div>
    <div class="single-line">{bug.description}</div>
    <div class="single-line">
        Status: <span>
            {bug.reporter?.firstName +
                " " +
                bug.reporter?.lastName +
                " (" +
                bug.reporter?.username +
                ")"}
        </span>
    </div>
    <div class="single-line">
        Reported by: <span>
            {bug.reporter?.firstName +
                " " +
                bug.reporter?.lastName +
                " (" +
                bug.reporter?.username +
                ")"}
        </span>
    </div>
</a>

<style>
    .bug-card {
        border: 1px solid var(--color-surface-500);
        border-radius: 8px;
        margin: 8px;
        transition: all 0.15s ease-in-out;
    }
    .bug-card:hover {
        border-color: var(--color-primary-400);
        border-radius: 8px;
        margin: 8px;
    }
    .bug-card:hover > .project-title {
        color: var(--color-primary-400);
    }
    .single-line {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
</style>

