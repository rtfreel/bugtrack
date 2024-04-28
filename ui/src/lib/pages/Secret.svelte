<script lang="ts">
    import axios from "axios";
    import { authHeader } from "../auth/utils/header";

    $: a = ""
    axios.get("http://localhost:8080/test/hello", { headers: authHeader })
        .then((res) => a = res.data);
</script>

<h1>Secret:</h1>
<p>{a}</p>
