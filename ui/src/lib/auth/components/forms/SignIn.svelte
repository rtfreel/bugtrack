<script lang="ts">
    import { authState } from "../../state";


    let username = "";
    let password = "";

    function signIn() {
        console.log(authState.signIn({ username: username, password: password }));
    }
</script>

<form class="flex flex-col" on:submit|preventDefault={signIn}>
    <label for="username" class="mt-1">Username:</label>
    <input type="text" class="" bind:value={username} />
    <label for="password" class="mt-1">Password:</label>
    <input type="password" bind:value={password} />
    <button type="submit" class="primary mt-4">Sign in</button>
</form>
