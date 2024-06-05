<script lang="ts">
    import { createEventDispatcher } from "svelte";
    import { authState } from "../../state";

    let username = "";
    let password = "";
    let firstName = "";
    let lastName = "";

    const dispatch = createEventDispatcher();

    function signUp() {
        let result = authState.signUp({
            username: username,
            password: password,
            firstName: firstName,
            lastName: lastName,
        });
        if (result) {
            result.then(
                () => {
                    window.location.href = "/auth#Sign-in";
                    window.location.reload();
                },
                () => dispatch("fail", "Username is already taken!")
            );
        } else {
            dispatch("fail", "Username is already taken!");
        }
    }
</script>

<form class="flex flex-col" on:submit|preventDefault={signUp}>
    <label for="firstname" class="mt-1">First name:</label>
    <input id="firstname" type="text" bind:value={firstName} required />
    <label for="lastname" class="mt-1">Last name:</label>
    <input id="lastname" type="text" bind:value={lastName} required />
    <label for="username" class="mt-2">Username:</label>
    <input id="username" type="text" bind:value={username} required />
    <label for="password" class="mt-1">Password:</label>
    <input id="password" type="password" bind:value={password} required />
    <button id="signup-btn" type="submit" class="primary mt-2">Sign up</button>
</form>
