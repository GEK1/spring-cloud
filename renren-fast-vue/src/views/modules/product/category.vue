<template>
    <div>
        <el-tree :data="menus" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
    </div>
</template>

<script>
export default {
    name: 'RenrenFastVueCategory',

    data() {
        return {
            menus:[],
            defaultProps: {
                children: 'children',
                label: 'name'
            }
        };
    },

    created() {
       this.getMenus(); 
    },
    methods: {
        // 获取菜单
        getMenus(){
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/product/category/list/tree'),
          method: 'get',
        }).then(({data}) => {
                this.menus=data.data;
        })
        },
        handleNodeClick(data) {
       
      }
    },
};
</script>

<style lang="scss" scoped>

</style>