<script lang="ts">
    import { createEventDispatcher } from "svelte";
    import type { Step } from "../../types/Step";

    export let step: Step;
    export let editable: boolean;

    let dispatch = createEventDispatcher();

    let editedStep: Step = { ...step };
    let editing: boolean = false;
    let removing: boolean = false;

    export function getStep() {
        editing = false;
        removing = false;
        return editedStep;
    }
</script>

<div class="step-item {removing ? 'to-remove' : ''} w-full flex flex-row mt-1">
    <span class="w-8">{step.sortOrder}.</span>
    <textarea
        class="w-full"
        rows="3"
        bind:value={editedStep.description}
        on:input={() => {
            editing = true;
            dispatch("update");
        }}
        readonly={!editable}
    />
    {#if editable}
        <div class="step-controls flex flex-col w-8">
            <button
                on:click={() => {
                    if (removing) {
                        dispatch("removed");
                    }
                    removing = true;
                }}
                title={removing ? "Confirm removal" : "Remove step"}
            >
                <i
                    class="fa-regular {removing
                        ? 'fa-circle-check'
                        : 'fa-trash-can'}"
                ></i>
            </button>
            {#if editing || removing}
                <button
                    on:click={() => {
                        if (removing) {
                            removing = false;
                            return;
                        }
                        editing = false;
                        editedStep = { ...step };
                        dispatch("revert");
                    }}
                    title={removing ? "Cancel removal" : "Revert changes"}
                >
                    <i class="fa-regular fa-circle-xmark"></i>
                </button>
            {/if}
        </div>
    {/if}
</div>

<style>
    .step-item {
        border-radius: 8px;
        border: none;
    }
    .step-item.to-remove textarea {
        opacity: 0.5;
    }
</style>
