<template>

    <div>
        <h1>个人信息</h1>
        <!--{{ $route.params.id }}-->
        {{id}}
    </div>

</template>

<script>
export default {
    props: ['id'],
    name: "UserProfile"
}
</script>

<style scoped>

</style>
