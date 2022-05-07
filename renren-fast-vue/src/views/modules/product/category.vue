<template>
  <div>
    <el-tree :data="menus" :props="defaultProps" @node-click="handleNodeClick" show-checkbox node-key="catId"
      :default-expanded-keys="expandedKeys">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button v-if="node.level <=2" type="text" size="mini" @click="() => append(data)">
            Append
          </el-button>
          <el-button v-if="node.childNodes==0" type="text" size="mini" @click="() => remove(node, data)">
            Delete
          </el-button>
        </span>
      </span>
    </el-tree>
    <el-dialog title="添加菜单" :visible.sync="dialogFormVisible">
      <el-form :model="category">
        <el-form-item label="菜单名称">
          <el-input v-model="category.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCategory">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'RenrenFastVueCategory',

    data() {
      return {
        menus: [],
        category: {
          "name": "",
          "parentCid": 0,
          "catLevel": 1,
          "showStatus": 1,
          "sort": 0
        },
        dialogFormVisible: false,
        expandedKeys: [],
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
      addCategory() {
        this.$http({
          url: this.$http.adornUrl('/product/category/save'),
          method: 'post',
          data: this.$http.adornData(this.category, false)
        }).then(({
          data
        }) => {
          this.$message({
            type: 'success',
            message: '添加成功'
          });
          // 关闭弹窗
          this.dialogFormVisible = false
          // 刷新菜单
          this.getMenus();
          // 设置默认打开的节点
          this.expandedKeys = [this.category.parentCid]
        })
      },
      append(data) {
        this.dialogFormVisible = true
        this.category.parentCid = data.catId
        this.category.catLevel = data.catLevel * 1 + 1
      },
      remove(node, data) {
        var ids = [data.catId]
        this.$confirm(`是否刪除【${data.name}】菜单, 是否继续?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/product/category/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({
            data
          }) => {
            this.$message({
              type: 'success',
              message: '删除成功'
            });

            // 刷新菜单
            this.getMenus();
            // 设置默认打开的节点
            this.expandedKeys = [node.parent.data.catId]
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });

      },
      // 获取菜单
      getMenus() {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/product/category/list/tree'),
          method: 'get',
        }).then(({
          data
        }) => {
          this.menus = data.data;
        })
      },
      handleNodeClick(data) {

      }
    },
  };

</script>

<style lang="scss" scoped>

</style>
