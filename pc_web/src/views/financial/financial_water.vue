<template>
  <div class="main-container">
    <div class="header-container">
      <h1>用 水 管 理</h1>
      <div class="btn">
        <el-button class="cancel" :disabled="isGet" @click="download">下 载 模 板</el-button>
        <el-button class="save" :disabled="isAdd" @click="upload">上传</el-button>
        <input type="file" accept=".xls,.xlsx" style="display: none" @change="onFileChange" ref="iptRef" />
      </div>
    </div>
    <div class="table-container">
      <div class="navbar">
        <el-form ref="form" :model="form" label-width="50px" :inline="true">
          <el-form-item label="房号">
            <el-input v-model="form.houseName" placeholder="请输入房号"></el-input>
          </el-form-item>
          <el-form-item label="时间">
            <el-date-picker v-model="form.month" type="month" placeholder="请选择时间" :picker-options="pickerBeginOption" value-format="yyyy-MM"> </el-date-picker>
          </el-form-item>
          <el-button type="primary" style="height: 38px" @click="search">查询</el-button>
        </el-form>
      </div>
      <div class="table">
        <el-table :data="tableData" stripe style="width: 100%" height="454" v-loading="loading" :default-sort="{ prop: 'date', order: 'descending' }">
          <el-table-column prop="date" label="时间" sortable width="180"> </el-table-column>
          <el-table-column prop="houseName" label="房号" width="180"> </el-table-column>
          <el-table-column prop="numWater" label="用水量(方)" width="180"> </el-table-column>
          <el-table-column prop="costWater" label="水费(元)" width="180"> </el-table-column>
          <el-table-column prop="uploadWater" label="上传时间"> </el-table-column>
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <el-button type="primary" size="small" :disabled="isEdit || scope.row.state != `0`" @click="edit(scope.row)">编 辑</el-button>
            </template>
          </el-table-column>
          <template #empty>
            <el-empty :image-size="200"></el-empty>
          </template>
        </el-table>
      </div>
      <div class="pagination">
        <el-pagination @current-change="handleCurrentChange" :current-page="pagination.currentPage" :page-size="pagination.pageSize" background layout="prev, pager, next" :total="pagination.total"> </el-pagination>
      </div>
      <div class="drawer">
        <el-drawer title="水费表解析" :visible.sync="drawer" :wrapperClosable="false" :show-close="false">
          <div class="drawer-container">
            <div class="table">
              <h3>数据：</h3>
              <el-table :data="hasData" border style="width: 100%" size="mini" height="320">
                <el-table-column prop="houseName" label="房号"> </el-table-column>
                <el-table-column prop="time" label="月份"> </el-table-column>
                <el-table-column prop="num" label="用水量(方)">
                  <template slot-scope="scope">
                    <el-input v-model.trim="scope.row.num" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="80">
                  <template slot-scope="scope">
                    <el-button type="text" style="color: #ff2855" size="mini" :disabled="isDel" @click="deleteRow(`has`, scope)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="table">
              <h3>空数据：</h3>
              <el-table :data="noneData" border style="width: 100%" size="mini" height="160">
                <el-table-column prop="houseName" label="房号"> </el-table-column>
                <el-table-column prop="time" label="月份"> </el-table-column>
                <el-table-column prop="num" label="用水量(方)">
                  <template slot-scope="scope">
                    <el-input v-model.trim="scope.row.num" oninput="value=value.replace(/[^0-9.]/g,'')" @blur="changeData(scope)"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="100">
                  <template slot-scope="scope">
                    <el-button type="text" style="color: #ff2855" size="mini" @click="deleteRow(`none`, scope)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="btn">
              <el-button type="primary" @click="add">确 定</el-button>
              <el-button @click="drawer = false">取 消</el-button>
            </div>
          </div>
        </el-drawer>
      </div>
      <div class="drawer">
        <el-drawer title="水费表解析" :visible.sync="toggleDrawer" :wrapperClosable="false" :show-close="false">
          <div class="drawer-container">
            <div class="table">
              <h3>新数据：</h3>
              <el-table :data="newData" border style="width: 100%" size="mini" height="240" @selection-change="handleSelectionChange">
                >
                <el-table-column type="selection" width="55"> </el-table-column>
                <el-table-column prop="houseName" label="房号"> </el-table-column>
                <el-table-column prop="time" label="月份"> </el-table-column>
                <el-table-column prop="num" label="用水量(方)"> </el-table-column>
              </el-table>
            </div>
            <div class="table">
              <h3>源数据：</h3>
              <el-table :data="oldData" border style="width: 100%" size="mini" height="240">
                <el-table-column prop="houseName" label="房号"> </el-table-column>
                <el-table-column prop="time" label="月份"> </el-table-column>
                <el-table-column prop="num" label="用水量(方)"> </el-table-column>
              </el-table>
            </div>
            <div class="btn">
              <el-button type="primary" @click="toggle" :disabled="isTooggle">替 换</el-button>
              <el-button @click="toggleDrawer = false">取 消</el-button>
            </div>
          </div>
        </el-drawer>
      </div>
    </div>
  </div>
</template>

<script>
import * as permission from '@/utils/permission';
import { sheetWaterExcel, getWaterExcel, judgeWater, toggleWater, getAllCost, updateWaterByCostId } from '@/api/cost';
import { validatePassword } from '@/api/user';
export default {
  data() {
    return {
      pickerBeginOption: {
        disabledDate: time => {
          return time.getTime() > Date.now();
        }
      },
      form: {
        month: '',
        houseName: ''
      },
      tableData: [],
      pagination: {
        currentPage: 1, //当前页码
        pageSize: 7, //每页显示的记录数
        total: 0
      },
      loading: false,
      password: '',
      drawer: false,
      hasData: [],
      noneData: [],
      newData: [],
      oldData: [],
      multipleSelection: [],
      toggleDrawer: false
    };
  },
  created() {
    this.getAll();
  },
  methods: {
    async getAll() {
      try {
        this.loading = true;
        const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
        const { data } = await getAllCost(param, {
          houseName: this.form.houseName,
          time: this.form.month
        });
        // console.log(data);
        this.tableData = data.records;
        this.pagination.currentPage = data.current;
        this.pagination.total = data.total;
        this.pagination.pageSize = data.size;
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
      }
    },
    //下载模板
    download() {
      this.$message.info('正在获取下载链接，请稍等...');
      sheetWaterExcel();
    },
    //上传模板
    upload() {
      this.$prompt('请输入密码', '上传水费', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputType: 'password',
        inputPattern: /^\w{5,12}$/,
        inputErrorMessage: '密码格式不正确'
      })
        .then(async ({ value }) => {
          try {
            // console.log(value);
            await validatePassword({
              password: value,
              username: this.$store.getters.username
            });
            this.$refs.iptRef.click();
          } catch (error) {
            console.log(error);
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '取消上传'
          });
        });
    },
    handleCurrentChange(currentPage) {
      this.loading = true;
      this.pagination.currentPage = currentPage;
      this.getAll();
      this.loading = false;
    },
    //上传文件
    async onFileChange(e) {
      const files = e.target.files;
      if (files.length === 0) {
        return; // 说明文件选择的窗口打开了，但是它一个文件都没选择就点击了确定关闭了选择弹框
      } else {
        if (this.beforeAvatarUpload(files[0])) {
          try {
            console.log(files[0]);
            const form = new FormData();
            form.append(`file`, files[0]);
            const { data } = await getWaterExcel(form);
            this.hasData = data.has;
            this.noneData = data.none;
            console.log(data);
            this.drawer = true;
          } catch (error) {
            console.log(error);
          }
        }
      }
    },
    //校检函数
    beforeAvatarUpload(file) {
      let extension = file.name.substring(file.name.lastIndexOf('.') + 1);
      //   console.log(extension);
      const whiteList = ['xls', 'xlsx'];
      if (whiteList.indexOf(extension) === -1) {
        this.$message.error('上传文件只能是xls、xlsx格式');
        return false;
      }
      return true;
    },
    //删除改行元素
    deleteRow(type, scope) {
      if (type == `has`) {
        // console.log(scope);
        this.hasData.splice(scope.$index, 1);
      }
      if (type == `none`) {
        console.log(scope);
        this.noneData.splice(scope.$index, 1);
      }
    },
    //空数据修改后回显
    changeData(scope) {
      if (scope.row.num != '' && scope.row.num != null) {
        this.hasData.push(this.noneData[scope.$index]);
        this.noneData.splice(scope.$index, 1);
      }
    },
    //确认添加
    add() {
      this.$confirm('此操作将添加本月的用水数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          try {
            const { data } = await judgeWater({
              listStr: JSON.stringify(this.hasData)
            });
            this.newData = data.new;
            this.oldData = data.old;
            if (this.newData.length > 0 && this.oldData.length > 0) {
              this.$message.info('存在冲突的数据');
              this.toggleDrawer = true;
              this.drawer = false;
            } else {
              this.drawer = false;
              this.$message.success('添加成功');
            }
          } catch (error) {
            console.log(error);
          } finally {
            this.getAll();
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消添加'
          });
        });
    },
    //替换数据
    toggle() {
      this.$confirm('此操作将替换源数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          try {
            await toggleWater({
              listStr: JSON.stringify(this.multipleSelection)
            });
            this.toggleDrawer = false;
            this.$message.success('修改成功');
          } catch (error) {
            console.log(error);
          } finally {
            this.getAll();
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消替换'
          });
        });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      //   console.log(this.multipleSelection);
    },
    //查找
    search() {
      this.getAll();
      this.form = {
        month: '',
        houseName: ''
      };
    },
    //修改信息
    edit(row) {
      this.$prompt('请输入密码', '修改水费', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputType: 'password',
        inputPattern: /^\w{5,12}$/,
        inputErrorMessage: '密码格式不正确'
      })
        .then(async ({ value }) => {
          try {
            // console.log(value);
            await validatePassword({
              password: value,
              username: this.$store.getters.username
            });
            this.$prompt('请输入修改的用水量', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              inputPattern: /^(-?\d+)(\.\d+)?$/,
              inputErrorMessage: '参数格式不正确'
            })
              .then(async ({ value }) => {
                try {
                  await updateWaterByCostId({
                    costId: row.costId,
                    numWater: value
                  });
                  this.$message.success('修改成功');
                } catch (error) {
                  console.log(error);
                } finally {
                  this.getAll();
                }
              })
              .catch(() => {
                this.$message({
                  type: 'info',
                  message: '取消编辑'
                });
              });
          } catch (error) {
            console.log(error);
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '取消编辑'
          });
        });
    }
  },
  computed: {
    isAdd() {
      return permission.isAdd(this.$route.params.type);
    },
    isDel() {
      return permission.isDel(this.$route.params.type);
    },
    isUp() {
      return permission.isUp(this.$route.params.type);
    },
    isPull() {
      return permission.isPull(this.$route.params.type);
    },
    isEdit() {
      return permission.isEdit(this.$route.params.type);
    },
    isGet() {
      return permission.isGet(this.$route.params.type);
    },
    isTooggle() {
      return this.multipleSelection.length > 0 ? false : true;
    }
  }
};
</script>

<style lang="scss" scoped>
.main-container {
  .header-container {
    display: flex;
    justify-content: space-between;

    h1 {
      font-size: 24px;
      line-height: 40px;
      color: #3a3f63;
      font-weight: 400;
    }
    .btn {
      text-align: right;
      .cancel {
        width: 130px;
        height: 42px;
        border-radius: 27px;
        background-color: #fff;
        font-size: 16px;
        color: #4458fe;
        box-shadow: 0px 4px 16px rgba(179, 192, 231, 0.32);
      }
      .save {
        width: 100px;
        height: 42px;
        border-radius: 27px;
        background: linear-gradient(45deg, #4f8aff 0%, #4b5eff 100%);
        background-blend-mode: normal;
        box-shadow: 0px 4px 16px #b3c0e7;
        color: #fff;
        font-size: 16px;
      }
    }
  }
  .table-container {
    height: 649px;
    width: 100%;
    margin-top: 25px;
    background-color: #fff;
    border-radius: 8px;
    padding: 25px;
    .navbar {
      margin-top: 10px;
      height: 70px;
      width: 100%;
      border-bottom: 1px solid #e6e9f4;
      ::v-deep .el-form {
        .el-form-item__label {
          font-size: 14px !important;
          padding: 0 15px 0 0 !important;
          color: #6a74a5;
          font-weight: 700;
        }

        .el-input__inner {
          width: 180px;
          height: 35px;
          border: 1px solid #ccc;
          color: #606266;
          text-align: left;

          &:hover {
            border: 1px solid #ccc;
          }
        }
      }
    }
    .pagination {
      margin-top: 15px;
    }
  }
  .drawer-container {
    width: 90%;
    margin: 0 auto;
    .table {
      margin-bottom: 15px;
      h3 {
        margin-bottom: 5px;
        font-size: 14px;
        font-weight: 400;
      }
      ::v-deep .el-input {
        height: 23px !important;
        .el-input__inner {
          font-size: 12px !important;
          height: 23px !important;
          border: none !important;
        }
      }
    }
  }
}
</style>
