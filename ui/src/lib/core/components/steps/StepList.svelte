<script lang="ts">
    import { createEventDispatcher } from "svelte";
    import type { Step } from "../../types/Step";
    import StepItem from "./StepItem.svelte";

    export let steps: Step[] = [];
    export let editable: boolean;

    const dispatch = createEventDispatcher();

    let stepItems: StepItem[] = [];
    let changed: boolean[] = [];

    function createStep() {
        let order = 999999;
        steps = [
            ...steps,
            {
                description: "",
                expectedResult: "",
                actualResult: "",
                sortOrder: order,
            },
        ];
        fixOrder();
        dispatch("stepsUpdated", steps);
    }

    function saveSteps() {
        let newSteps: Step[] = [];
        stepItems.forEach((stepItem) => {
            newSteps = [...newSteps, stepItem.getStep()];
        });
        stepItems = [];
        steps = [...newSteps];
        fixOrder();
        dispatch("stepsUpdated", steps);
        changed = [];
    }

    function fixOrder() {
        steps = [...steps.sort((a, b) => a.sortOrder - b.sortOrder)];
        let order = 1;
        steps.forEach((step) => {
            step.sortOrder = order;
            order++;
        });
    }
</script>

<div class="w-full flex flex-col items-center px-3">
    <div class="w-full text-xl text-center mb-3">Steps</div>
    {#each steps as step, index (step.id)}
        <StepItem
            {step}
            {editable}
            bind:this={stepItems[index]}
            on:update={() => (changed[index] = true)}
            on:revert={() => (changed[index] = false)}
            on:removed={() => {
                steps.splice(index, 1);
                fixOrder();
                dispatch("stepsUpdated", steps);
            }}
        />
    {/each}
    {#if editable}
        <div class="w-full flex flex-row justify-center mt-2">
            <button class="secondary w-1/5" on:click={createStep}>+</button>
            <button
                class="primary w-1/5 ms-2"
                disabled={!changed.includes(true)}
                on:click={saveSteps}>Save</button
            >
        </div>
    {/if}
</div>
