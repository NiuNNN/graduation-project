<template>
  <div class="main-container">
    <div class="header-container">
      <h1>房 间 管 理</h1>
    </div>
    <div class="table-container">
      <div class="fl">
        <div class="floor" @click="goFloor(13)"><span>13 层</span></div>
        <div class="floor" @click="goFloor(12)"><span>12 层</span></div>
        <div class="floor" @click="goFloor(11)"><span>11 层</span></div>
        <div class="floor" @click="goFloor(10)"><span>10 层</span></div>
        <div class="floor" @click="goFloor(9)"><span>9 层 </span></div>
        <div class="floor" @click="goFloor(8)"><span>8 层 </span></div>
        <div class="floor" @click="goFloor(7)"><span>7 层 </span></div>
        <div class="floor" @click="goFloor(6)"><span>6 层 </span></div>
        <div class="floor" @click="goFloor(5)"><span>5 层 </span></div>
        <div class="floor" @click="goFloor(4)"><span>4 层 </span></div>
        <div class="floor" @click="goFloor(3)"><span>3 层 </span></div>
        <div class="floor" @click="goFloor(2)"><span>2 层 </span></div>
        <div class="door"></div>
        <div class="ps">*六层以上为高楼层</div>
      </div>
      <div class="fr">
        <template v-if="isCurrent == 1">
          <div class="fr-index">
            <div class="fr-header">
              <h1>房 屋 总 览</h1>
            </div>
            <div class="card"></div>
          </div>
        </template>
        <template v-else>
          <div class="fr-floor">
            <el-page-header @back="getAll" :content="title"> </el-page-header>
            <div class="floor-container" v-loading="loading">
              <div class="navbar">
                <el-row justify="space-around">
                  <el-col :span="12">
                    <span class="area">总 面 积 : {{ area }} (㎡)</span>
                    <span class="area" style="color: #00d0b5">
                      可 用 面 积 : {{ available }} (㎡)</span
                    ></el-col
                  >
                  <el-col :span="12" style="text-align: right">
                    <el-button class="add" @click="add()" :disabled="isAdd"
                      >添 加 +</el-button
                    ></el-col
                  >
                </el-row>
              </div>
              <div class="table">
                <el-table :data="tableData" style="width: 100%" height="472">
                  <el-table-column prop="houseName" label="房号" width="80">
                  </el-table-column>
                  <el-table-column prop="styleName" label="房型名称">
                  </el-table-column>
                  <el-table-column prop="area" label="面积(㎡)">
                  </el-table-column>
                  <el-table-column prop="balcony" label="阳台" width="80">
                  </el-table-column>
                  <el-table-column prop="price" label="价格(元/月)">
                  </el-table-column>
                  <el-table-column prop="state" label="状态" width="80">
                  </el-table-column>
                  <el-table-column fixed="right" label="操作">
                    <template slot-scope="scope">
                      <el-button
                        type="danger"
                        size="mini"
                        @click="handleDel(scope.row)"
                        :disabled="isDel || scope.row.state == `已租`"
                        >删 除</el-button
                      >
                    </template>
                  </el-table-column>
                  <template #empty>
                    <el-empty :image-size="200"></el-empty>
                  </template>
                </el-table>
              </div>
            </div>
          </div>
        </template>
      </div>
      <div class="drawer">
        <el-drawer
          :title="addTitle"
          :visible.sync="drawer"
          size="28%"
          :direction="direction"
          :before-close="handleClose"
        >
          <el-form
            ref="houseForm"
            :model="houseForm"
            label-width="100px"
            :rules="rules"
          >
            <el-form-item label="房 间 号">
              <el-input
                v-model="houseForm.houseName"
                :disabled="true"
              ></el-input>
            </el-form-item>
            <el-form-item label="房 型" prop="styleName">
              <el-select
                v-model="houseForm.styleName"
                placeholder="请选择房型"
                @change="getStyleArea"
              >
                <el-option
                  :label="item"
                  :value="item"
                  v-for="(item, index) in styleNameSelect"
                  :key="index"
                >
                </el-option>
              </el-select> </el-form-item
            ><el-form-item label="面 积 (㎡)" prop="area">
              <el-select
                v-model="houseForm.area"
                placeholder="请选择面积"
                @change="getStyleBalcony"
              >
                <el-option
                  :label="item"
                  :value="item"
                  v-for="(item, index) in styleAreaSelect"
                  :key="index"
                >
                </el-option>
              </el-select> </el-form-item
            ><el-form-item label="阳 台" prop="balcony" style="margin: 0">
              <el-select v-model="houseForm.balcony" placeholder="请选择阳台">
                <el-option
                  :label="item"
                  :value="item"
                  v-for="(item, index) in styleBalconySelect"
                  :key="index"
                >
                </el-option>
              </el-select>
              <p class="ps" style="width: 100%; margin: 0">
                *请依次选择房型、面积、阳台 进行添加房间
              </p>
            </el-form-item>
            <el-form-item>
              <span style="font-size: 12px"
                >剩 余 房 型 : {{ styleAvailable }} (间)</span
              >
              <span style="margin-left: 15px; color: #00d0b5; font-size: 12px"
                >可 用 面 积 : {{ available }} (㎡)</span
              >
              <div>
                <el-button type="primary" @click="submit" :disabled="isDisabled"
                  >确 认</el-button
                >
                <el-button @click="reset()">取 消</el-button>
              </div>
            </el-form-item>
          </el-form>
        </el-drawer>
      </div>
    </div>
  </div>
</template>

<script>
import { getArea } from "@/api/floor";
import {
  getHouseDetail,
  getHouseNumber,
  insertHouse,
  deleteHouse,
} from "@/api/house";
import {
  getStyleArea,
  getStyleBalcony,
  getStyleName,
  leftStyle,
} from "@/api/house_type";
import { validEmpty } from "@/utils/validate";
import * as permission from "@/utils/permission";
export default {
  data() {
    return {
      loading: true,
      isCurrent: 1,
      tableData: [],
      drawer: false,
      direction: "rtl",
      available: "",
      area: "",
      houseForm: {
        houseName: "",
        styleName: "",
        area: "",
        balcony: "",
        styleId: "",
      },
      styleAvailable: "",
      isDisabled: true,
      //下拉框
      styleNameSelect: [],
      styleAreaSelect: [],
      styleBalconySelect: [],
      rules: {
        styleName: [
          { required: true, message: "请选择房型信息", trigger: "blur" },
        ],
        area: [{ required: true, message: "请选择面积信息", trigger: "blur" }],
        balcony: [
          { required: true, message: "请选择阳台信息", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    //点击取消
    reset() {
      this.houseForm = {
        houseName: "",
        styleName: "",
        area: "",
        balcony: "",
        styleId: "",
      };
      this.drawer = false;
    },
    //获取楼层面积
    async getArea(floor) {
      try {
        const { data } = await getArea(floor);
        this.available = data.available;
        this.area = data.area;
      } catch (error) {
        console.log(error);
      }
    },
    //返回主页
    getAll() {
      this.isCurrent = 1;
    },
    //切换楼层
    goFloor(floor) {
      this.isCurrent = floor;
      this.getHouseDetail();
      this.getArea(floor);
    },
    async handleDel(row) {
      this.$confirm("此操作将永久删除该房间, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          try {
            await deleteHouse(row);
            this.$message.success("删除成功");
          } catch (error) {
            console.log(error);
          } finally {
            this.getHouseDetail();
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //关闭抽屉
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          this.reset();
          done();
        })
        .catch((_) => {});
    },
    //获取房间号码
    async getHouseNumber() {
      try {
        const { data } = await getHouseNumber(this.isCurrent);
        // console.log(data);
        this.houseForm.houseName = data;
        this.drawer = true;
      } catch (error) {
        console.log(error);
      }
    },

    //添加房间
    add() {
      try {
        this.getHouseNumber();
        this.getStyleName();
      } catch (error) {
        console.log(error);
      }
    },
    //确认创建
    async submit() {
      try {
        await insertHouse({ floorId: this.isCurrent, ...this.houseForm });
        this.$message.success("创建成功");
      } catch (error) {
        console.log(error);
      } finally {
        this.getArea(this.isCurrent);
        this.getHouseDetail();
        this.reset();
      }
    },
    //获取房型
    async getStyleName() {
      this.houseForm.balcony = "";
      this.houseForm.area = "";
      const { data } = await getStyleName(this.isCurrent);
      // console.log(data);
      this.styleNameSelect = data;
    },
    //获取面积大小
    async getStyleArea() {
      this.houseForm.balcony = "";
      this.houseForm.area = "";
      const { data } = await getStyleArea(this.isCurrent, {
        styleName: this.houseForm.styleName,
      });
      this.styleAreaSelect = data;
    },
    //获取阳台
    async getStyleBalcony() {
      this.houseForm.balcony = "";
      const { data } = await getStyleBalcony(this.isCurrent, {
        styleName: this.houseForm.styleName,
        area: this.houseForm.area,
      });
      this.styleBalconySelect = data;
    },
    //获取房屋详细信息
    async getHouseDetail() {
      this.loading = true;
      try {
        const { data } = await getHouseDetail(this.isCurrent);
        this.tableData = data;
        this.loading = false;
        // console.log(data);
      } catch (error) {
        console.log(error);
      }
    },
  },
  computed: {
    title() {
      return `第 ${this.isCurrent} 层 详 情 页`;
    },
    addTitle() {
      return `添 加 第 ${this.isCurrent} 层 房间`;
    },
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
  watch: {
    houseForm: {
      async handler(newVal, oldVal) {
        if (
          validEmpty(newVal.styleName) ||
          validEmpty(newVal.balcony) ||
          validEmpty(newVal.area)
        ) {
          this.styleAvailable = "";
          this.isDisabled = true;
        } else {
          // 获取房型的剩余数量
          const { data } = await leftStyle(this.isCurrent, {
            styleName: this.houseForm.styleName,
            area: this.houseForm.area,
            balcony: this.houseForm.balcony,
          });
          this.houseForm.styleId = data.styleId;
          this.styleAvailable = data.available;
          if (this.styleAvailable == 0) {
            this.isDisabled = true;
          } else {
            if (Number(this.available) - Number(this.houseForm.area) >= 0) {
              this.isDisabled = false;
            } else {
              this.isDisabled = true;
            }
          }
        }
      },
      deep: true,
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
  }
  .table-container {
    display: flex;
    height: 649px;
    width: 100%;
    margin-top: 25px;
    background-color: #fff;
    border-radius: 8px;
    padding: 25px;
  }
  .fl {
    width: 25%;
    margin-right: 2%;
    // background-color: blue;
    padding-top: 25px;
    .floor {
      padding-left: 151px;
      margin: auto;
      width: 80%;
      height: 40px;
      background: url("@/assets/image/house.png");
      background-repeat: no-repeat;
      span {
        display: inline-block;
        width: 60px;
        padding-left: 20px;
        font-size: 14px;
        color: #3a3f63;
        font-weight: 700;
        border-bottom: 3px dashed #e6e9f4;
        cursor: pointer;
        &:hover {
          color: #4458fe;
          border-bottom: 3px dashed #4458fe;
        }
      }
      .current {
        color: #4458fe;
        border-bottom: 3px dashed #4458fe;
      }
    }
    .door {
      margin: auto;
      width: 80%;
      height: 45px;
      background: url("@/assets/image/door.png");
      background-repeat: no-repeat;
    }
  }
  .fr {
    width: 73%;
    // background-color: pink;
    .fr-index {
      .fr-header {
        h1 {
          padding-left: 10px;
          font-size: 18px;
          line-height: 40px;
          color: #3a3f63;
          font-weight: 400;
        }
      }
    }
    .fr-floor {
      .floor-container {
        .navbar {
          margin-top: 15px;
          border-bottom: 3px solid #e6e9f4;
        }
        .area {
          margin-right: 10px;
          padding-left: 25px;
          font-size: 14px;
          color: #6a74a5;
          font-weight: 700;
          line-height: 30px;
        }
        .add {
          width: 100px;
          height: 20px;
          border-radius: 27px;
          background: linear-gradient(45deg, #4f8aff 0%, #4b5eff 100%);
          background-blend-mode: normal;
          box-shadow: 0px 4px 16px #b3c0e7;
          color: #fff;
          font-size: 14px;
          line-height: 0px;
        }
        .table {
          .el-table {
            height: 467px;
          }
        }
      }
    }
  }
  .drawer {
    ::v-deep .el-form {
      padding: 0 30px 0 20px;

      .el-form-item {
        margin-bottom: 20px;
      }

      .el-form-item__label {
        font-size: 14px !important;
        padding: 0 15px 0 0 !important;
      }
      .el-select {
        width: 100%;
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
  .ps {
    width: 80%;
    margin: auto;
    margin-top: 2px;
    font-size: 10px;
    color: #ff2855;
  }
}
</style>
