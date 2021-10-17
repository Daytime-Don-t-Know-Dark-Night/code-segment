<template>

    <div>
        <h1>个人信息</h1>
        <!--{{ $route.params.id }}-->
        {{ id }}
    </div>

</template>

<script>
export default {
    props: ['id'],
    name: "UserProfile",
    // 进入路由前后的钩子函数
    beforeRouteEnter: (to, from, next) => {
        console.log("进入路由之前...加载数据");

        next();
    },
    beforeRouteLeave: (to, from, next) => {
        console.log("进入路由之后...");
        next();
    },
    methods: {
        getData: function () {

        }
    }
}
</script>

<style scoped>

</style>
