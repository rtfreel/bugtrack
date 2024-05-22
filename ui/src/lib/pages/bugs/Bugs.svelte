<script lang="ts">
    import Navbar from "../../common/components/Navbar.svelte";
    import BugList from "../../core/components/bugs/BugList.svelte";
</script>
<Navbar path={[{ name: "Bugs", ref: "/bugs" }, { name: "Reported", ref: "/bugs" }]} />
<BugList columns={3} />
