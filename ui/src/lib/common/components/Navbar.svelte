<script lang="ts">
    import "@fortawesome/fontawesome-free/css/all.min.css";
    import type { PathItem } from "../types/PathItem";
    import { authState } from "../../auth/state";

    export let path: PathItem[] = [];
</script>

<header>
    <div id="headernav" class="flex flex-row justify-between items-center ps-2">
        <h1 id="title" class="text-2xl font-bold ms-3">Bugtrack</h1>
        <nav class="flex flex-row items-center h-full mx-1 py-3">
            <a class="navlink" href="/bugs/reported">My bugs</a>
            <div class="navdrop">
                <span class="navlink peer">Projects</span>
                <div
                    class="navmenu absolute hidden peer-hover:flex hover:flex w-fit flex-col"
                >
                    <a class="navsublink" href="/">All projects</a>
                    <a class="navsublink" href="/owned">Owned only</a>
                </div>
            </div>
            <div class="navdrop">
                <span class="navlink peer px-2">
                    <i class="fa-regular fa-user"></i>&nbsp;{$authState.user
                        ?.username}
                </span>
                <div
                    class="navmenu absolute hidden peer-hover:flex hover:flex w-fit flex-col right-0"
                >
                    <span class="px-3 pt-1">
                        Greetings, {$authState.user?.firstName +
                            " " +
                            $authState.user?.lastName}!
                    </span>
                    <button
                        class="primary mx-3 my-2"
                        on:click={() => {
                            authState.signOut();
                            window.location.href = "/auth";
                        }}
                    >
                        Sign out
                    </button>
                </div>
            </div>
        </nav>
    </div>
    <div id="navpath" class="ms-2">
        {#each path as item}
            <a class="pathitem" href={item.ref}>&nbsp;&gt;&nbsp;{item.name}</a>
        {/each}
    </div>
</header>

<style>
    header {
        background-color: var(--color-surface-200);
    }
    #headernav {
        background-color: var(--color-surface-300);
        border-width: 0 1px 1px 1px;
        border-radius: 0 0 8px 8px;
        border-color: var(--color-surface-400);
    }
    #title {
        color: var(--color-primary-100);
    }
    .pathitem {
        color: var(--color-surface-500);
        transition: all 0.15s ease-in-out;
    }
    .pathitem:hover {
        color: var(--color-primary-500);
    }
    .navlink {
        cursor: pointer;
        border-left: 1px solid var(--color-surface-400);
        min-width: 110px;
        max-width: 220px;
        word-wrap: break-word;
        overflow-x: hidden;

        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        transition: all 0.15s ease-in-out;
    }
    .navlink:hover {
        background-color: var(--color-surface-400);
    }
    .navmenu {
        min-width: 110px;
        background-color: var(--color-surface-300);
        border-width: 1px;
        border-color: var(--color-surface-500);
        border-radius: 0 8px 8px 8px;
    }
    .navdrop:last-child > .navmenu {
        border-radius: 8px 0 8px 8px;
    }
    .navsublink {
        border-radius: 0 8px 8px 8px;
        padding: 5px 0.5rem;
        transition: all 0.15s ease-in-out;
    }
    .navsublink:hover {
        color: var(--color-surface-300);
        background-color: var(--color-primary-300);
    }
    .navsublink:first-child {
        border-radius: 0 8px 0 0;
    }
    .navsublink:last-child {
        border-radius: 0 0 8px 8px;
    }
</style>
