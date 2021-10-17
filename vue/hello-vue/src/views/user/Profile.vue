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
        next(vm => {
            // 进入路由之前, 获取数据
            vm.getData()
        });
    },
    beforeRouteLeave: (to, from, next) => {
        console.log("进入路由之后...");
        next();
    },
    methods: {
        getData: function () {
            console.log("获取数据....")
            this.axios({
                method: "GET",
                url: "http://localhost:8080/static/mock/data.json"
            }).then(function (response) {
                console.log(response)
            })
        }
    }
}
</script>

<style scoped>

</style>
