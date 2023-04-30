<template>
  <div class="main-container">
    <div class="header-container">
      <h1>房 型 管 理</h1>
      <el-button class="add" @click="openDrawer('add')" :disabled="isAdd"
        >添 加 +</el-button
      >
    </div>
    <div class="table-container">
      <div class="navbar">
        <el-form ref="form" :model="form" label-width="50px" :inline="true">
          <el-row justify="space-around">
            <el-col :span="5">
              <el-form-item label="房型">
                <el-select
                  v-model="form.styleName"
                  placeholder="请选择房型"
                  @change="search"
                >
                  <el-option label="----请选择-----" value=""></el-option>
                  <el-option
                    :label="item"
                    :value="item"
                    v-for="(item, index) in styleNameList"
                    :key="index"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5"
              ><el-form-item label="阳台">
                <el-select
                  v-model="form.balcony"
                  placeholder="请选择阳台"
                  @change="search"
                >
                  <el-option label="----请选择-----" value=""></el-option>
                  <el-option label="有" value="有"></el-option>
                  <el-option label="无" value="无"></el-option>
                </el-select> </el-form-item
            ></el-col>
            <el-col :span="5"
              ><el-form-item label="楼层">
                <el-select
                  v-model="form.floor"
                  placeholder="请选择楼层"
                  @change="search"
                >
                  <el-option label="----请选择-----" value=""></el-option>
                  <el-option label="高" value="高"></el-option>
                  <el-option label="低" value="低"></el-option>
                </el-select> </el-form-item
            ></el-col>
            <el-col :span="9">
              <span class="area">总 面 积 : {{ area }} (㎡)</span>
              <span class="area" style="color: #00d0b5"
                >可 用 面 积 : {{ available }} (㎡)</span
              >
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div class="table" v-loading="loading">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="styleId" label="编号" width="80">
          </el-table-column>
          <el-table-column prop="styleName" label="房型名称" width="200">
          </el-table-column>
          <el-table-column prop="area" label="面积(㎡)"> </el-table-column>
          <el-table-column prop="balcony" label="阳台"></el-table-column>
          <el-table-column prop="floor" label="楼层"> </el-table-column>
          <el-table-column prop="num" label="数量(间)"> </el-table-column>
          <el-table-column prop="available" label="空余(间)"> </el-table-column>
          <el-table-column prop="price" label="租金(元/㎡)"> </el-table-column>
          <el-table-column prop="state" label="状态"> </el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="openDrawer('edit', scope.row)"
                :disabled="isEdit"
                >编 辑</el-button
              >
              <template v-if="scope.row.state == '正常'">
                <el-button
                  size="mini"
                  type="danger"
                  @click="handleDelete(scope.row)"
                  :disabled="isPull"
                  >下 架</el-button
                >
              </template>
              <template v-else>
                <el-button
                  size="mini"
                  type="info"
                  @click="handleReset(scope.row)"
                  :disabled="isUp"
                  >上 架</el-button
                >
              </template>
            </template>
          </el-table-column>
          <template #empty>
            <el-empty :image-size="200"></el-empty>
          </template>
        </el-table>
      </div>
      <!-- 分页器 -->
      <div class="pagination">
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page="pagination.currentPage"
          :page-size="pagination.pageSize"
          background
          layout="prev, pager, next"
          :total="pagination.total"
        >
        </el-pagination>
      </div>
    </div>
    <div class="drawer">
      <el-drawer
        :title="drawerTitle"
        size="28%"
        :visible.sync="isShowDrawer"
        :before-close="handleClose"
      >
        <el-form
          ref="styleForm"
          :model="styleForm"
          :rules="rules"
          label-width="100px"
        >
          <el-form-item label="房型名称" prop="styleName">
            <el-input
              v-model.trim="styleForm.styleName"
              placeholder="请输入房型名称"
            ></el-input>
          </el-form-item>
          <el-form-item label="面积(㎡)" prop="area">
            <el-input
              v-model.number.trim="styleForm.area"
              placeholder="请输入该房型面积"
            ></el-input>
          </el-form-item>
          <el-form-item label="阳台" prop="balcony">
            <el-radio-group v-model="styleForm.balcony">
              <el-radio label="有"></el-radio>
              <el-radio label="无"></el-radio>
            </el-radio-group> </el-form-item
          ><el-form-item label="楼层" prop="floor">
            <el-radio-group v-model="styleForm.floor">
              <el-radio label="高"></el-radio>
              <el-radio label="低"></el-radio>
            </el-radio-group>
            <span style="margin-left: 15px; color: #ff2855; font-size: 12px"
              >*6楼以上为高楼层</span
            >
          </el-form-item>
          <el-form-item label="数量(间)" prop="num">
            <el-input
              v-model.trim="styleForm.num"
              placeholder="请输入该房型数量"
            ></el-input>
          </el-form-item>
          <el-form-item label="租金(元/㎡)" prop="price">
            <el-input
              v-model.trim="styleForm.price"
              placeholder="请输入该房型租金"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <div style="margin-top: 20px">
              <el-button type="primary" @click="submit" :disabled="isAdd"
                >确 认</el-button
              >
              <el-button @click="cancel">取 消</el-button>
            </div>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>
  </div>
</template>

<script>
import { getArea } from "@/api/floor";
import {
  delStyle,
  getAllStyle,
  getStyleName,
  insertStyle,
  resetStyle,
  updateStyle,
} from "@/api/house_type";
import { validNumber, valueAllEmpty } from "@/utils/validate";
import * as permission from "@/utils/permission";
export default {
  data() {
    const validateNumber = (rule, value, callback) => {
      validNumber(value) ? callback() : callback(new Error("请输入数字"));
    };
    return {
      loading: true,
      styleNameList: [],
      available: "",
      area: "",
      form: {
        styleName: "",
        balcony: "",
        floor: "",
      },
      styleForm: {
        styleName: "",
        area: "",
        price: "",
        num: "",
        balcony: "",
        floor: "",
      },
      drawerTitle: "",
      isShowDrawer: false,
      tableData: [],
      rules: {
        styleName: [
          { required: true, message: "请输入房型名称", trigger: "blur" },
        ],
        area: [
          { required: true, message: "请输入该房型面积", trigger: "blur" },
          {
            validator: validateNumber,
            message: "面积必须为数字值",
            trigger: "blur",
          },
        ],
        price: [
          { required: true, message: "请输入该房型面积", trigger: "blur" },
          {
            validator: validateNumber,
            message: "价格必须为数字值",
            trigger: "blur",
          },
        ],
        num: [
          { required: true, message: "请输入该房型数量", trigger: "blur" },
          {
            validator: validateNumber,
            message: "数量必须为数字值",
            trigger: "blur",
          },
        ],
        balcony: [
          { required: true, message: "请选择阳台信息", trigger: "blur" },
        ],
        floor: [{ required: true, message: "请选择楼层信息", trigger: "blur" }],
      },
      isCurrent: "",
      pagination: {
        currentPage: 1, //当前页码
        pageSize: 8, //每页显示的记录数
        total: 0,
      },
    };
  },
  created() {
    this.getAllStyle();
    this.getStyleName();
    this.getArea();
  },
  beforeUpdate() {
    this.loading = false;
  },
  methods: {
    cancel() {
      this.isShowDrawer = false;
      this.reset();
    },
    reset() {
      this.$refs.styleForm.resetFields();
      this.styleForm = {
        styleName: "",
        area: "",
        price: "",
        num: "",
        balcony: "",
        floor: "",
      };
    },
    async getArea() {
      try {
        const { data } = await getArea(1);
        this.area = data.area;
        this.available = data.available;
      } catch (error) {
        console.log(error);
      }
    },
    //获取全部房屋类型
    async getAllStyle() {
      try {
        this.loading = true;
        const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
        const { data } = await getAllStyle(param);
        this.tableData = data.records;
        this.pagination.currentPage = data.current;
        this.pagination.total = data.total;
        this.pagination.pageSize = data.size;
        // console.log(data);
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
      }
      // console.log(data);
    },
    //打开侧边栏
    openDrawer(type, row) {
      if (type == "add") {
        // console.log(this);
        this.drawerTitle = "添加房型";
        this.isCurrent = "add";
      }
      if (type == "edit") {
        this.drawerTitle = "修改房型";
        this.isCurrent = "edit";
        this.styleForm = { ...row };
      }
      this.isShowDrawer = true;
    },
    //确认按钮
    async submit() {
      try {
        await this.$refs.styleForm.validate();
        if (this.isCurrent == "add") {
          try {
            const result = await insertStyle(this.styleForm);
            this.$message.success(result.message);
          } catch (error) {
            console.log(error);
          }
        }
        if (this.isCurrent == "edit") {
          try {
            // console.log(this.styleForm);
            const result = await updateStyle(this.styleForm);
            this.$message.success(result.message);
            console.log(result);
          } catch (error) {
            console.log(error);
          }
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.isShowDrawer = false;
        this.$refs.styleForm.resetFields();
        this.loading = true;
        this.getStyleName();
        this.getAllStyle();
        this.getArea();
        this.loading = false;
      }
    },
    //房型下架
    async handleDelete(row) {
      try {
        const result = await delStyle(row.styleId);
        this.$message.success(result.message);
      } catch (error) {
        console.log(error);
      } finally {
        this.getAllStyle();
        this.getStyleName();
        this.getArea();
      }
    },
    //恢复上架
    async handleReset(row) {
      // console.log(row);
      try {
        const result = await resetStyle(row.styleId);
        this.$message.success(result.message);
      } catch (error) {
        console.log(error);
      } finally {
        this.getAllStyle();
        this.getStyleName();
        this.getArea();
      }
    },
    //关闭抽屉
    handleClose(done) {
      // console.log(done);
      if (valueAllEmpty(this.styleForm)) done();
      else {
        this.$confirm("确认关闭？")
          .then((_) => {
            this.reset();
            done();
          })
          .catch((_) => {});
      }
    },
    //换页
    handleCurrentChange(currentPage) {
      this.loading = true;
      this.pagination.currentPage = currentPage;
      this.getAllStyle();
      this.loading = false;
    },
    //获取下拉框
    async getStyleName() {
      this.form = { styleName: "", balcony: "", floor: "" };
      const { data } = await getStyleName(1);
      this.styleNameList = data;
      // console.log(data);
    },
    //搜索
    async search() {
      try {
        const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
        const { data } = await getAllStyle(param, this.form);
        this.tableData = data.records;
        this.pagination.currentPage = data.pages;
        this.pagination.total = data.total;
      } catch (error) {
        console.log(error);
      }
      // console.log(data);
    },
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
  },
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

    .add {
      width: 100px;
      height: 40px;
      border-radius: 27px;
      background: linear-gradient(45deg, #4f8aff 0%, #4b5eff 100%);
      background-blend-mode: normal;
      box-shadow: 0px 4px 16px #b3c0e7;
      color: #fff;
      font-size: 16px;
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
      .area {
        font-size: 14px;
        color: #6a74a5;
        font-weight: 700;
        line-height: 40px;
        margin-left: 30px;
      }
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
          text-align: center;

          &:hover {
            border: 1px solid #ccc;
          }
        }
      }
    }
    .table {
      .el-table {
        height: 467px;
      }
    }
    .pagination {
      margin-top: 25px;
    }
  }

  .drawer {
    ::v-deep .el-form {
      padding: 0 10px 0 20px;

      .el-form-item {
        margin-bottom: 20px;
      }

      .el-form-item__label {
        font-size: 14px !important;
        padding: 0 15px 0 0 !important;
      }

      .el-input__inner {
        border: 1px solid #ccc;
        color: #606266;

        &:hover {
          border: 1px solid #ccc;
        }

        &::placeholder {
          font-size: 14px;
          color: #606266;
        }
      }
    }
  }
}
</style>
