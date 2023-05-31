<template>
  <div class="container">
    <div class="form">
      <el-form :inline="true" :model="houseForm" class="demo-form-inline" size="small" style="padding-right: 0">
        <el-row>
          <el-col :span="7">
            <el-form-item label="房型">
              <el-select v-model="houseForm.styleName" placeholder="请选择房型"><el-option :label="item.styleName" :value="item.styleName" v-for="(item, index) in styleList" :key="index"></el-option></el-select> </el-form-item
          ></el-col>
          <el-col :span="7"
            ><el-form-item label="楼层">
              <el-select v-model="houseForm.floor" placeholder="请选择楼层"> <el-option :label="item.floorName" :value="item.floorName" v-for="(item, index) in floorList" :key="index"></el-option></el-select> </el-form-item
          ></el-col>
          <el-col :span="7"
            ><el-form-item label="阳台">
              <el-select v-model="houseForm.balcony" placeholder="请选择阳台">
                <el-option label="有" value="有"></el-option>
                <el-option label="无" value="无"></el-option
              ></el-select> </el-form-item
          ></el-col>
          <el-col :span="3">
            <el-form-item>
              <el-button type="primary" @click="getHouse">查询</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="table">
      <p style="color: #303133; font-size: 14px">请选择房间：</p>
      <el-table :data="tableData" style="width: 100%" @row-click="singleElection" highlight-current-row height="450" size="mini" border>
        <el-table-column width="55" label="选择">
          <template slot-scope="scope">
            <!-- 可以手动的修改label的值，从而控制选择哪一项 -->
            <el-radio class="radio" v-model="templateSelection" :label="scope.row.houseId">&nbsp;</el-radio>
          </template>
        </el-table-column>
        <el-table-column prop="houseName" label="房号" width="80"> </el-table-column>
        <el-table-column prop="styleName" label="房型"> </el-table-column>
        <el-table-column prop="balcony" label="阳台" width="80"> </el-table-column>
        <el-table-column prop="area" label="面积(㎡)" width="80"> </el-table-column>
        <el-table-column prop="price" label="房屋租金(月/元)" width="120"></el-table-column>
        <template #empty>
          <el-empty :image-size="80"></el-empty>
        </template>
      </el-table>
    </div>
    <div class="btn">
      <el-button type="primary" @click="submit">确 定</el-button>
      <el-button @click="close">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getFloor } from '@/api/floor';
import { getAllStyleName } from '@/api/house_type';
import { getHouse } from '@/api/house';
export default {
  props: {
    user: {
      type: Object,
      default: () => {
        return {};
      }
    }
  },
  data() {
    return {
      houseForm: {
        styleName: '',
        floor: '',
        balcony: ''
      },
      tableData: [],
      //   当前选择的行的id
      templateSelection: '',
      //   当前选择的行的数据
      checkList: [],
      floorList: [],
      styleList: [],
      newUser: {}
    };
  },
  created() {
    this.getAll();
  },
  methods: {
    async getAll() {
      try {
        const { data: floor } = await getFloor();
        this.floorList = floor;
        const { data: style } = await getAllStyleName();
        this.styleList = style;
        this.getHouse();
      } catch (error) {
        console.log(error);
      }
    },
    //按需获取房间
    async getHouse() {
      try {
        // console.log(this.houseForm);
        const { data } = await getHouse(this.houseForm);
        this.tableData = data;
        this.resetHouse();
      } catch (error) {
        console.log(error);
      }
    },
    //重置下拉框
    resetHouse() {
      this.houseForm = {
        styleName: '',
        floor: '',
        balcony: ''
      };
    },
    //获取teble 单选框数据
    singleElection(row) {
      // console.log(row);
      this.templateSelection = row.houseId;
      this.checkList = this.tableData.filter(item => item.houseId === row.houseId);
      // console.log(`该行的编号为${row.houseId}`);
    },
    submit() {
      if (this.checkList.length === 0) {
        this.$message.error('请选择房间，再进行下一步');
      } else {
        console.log(this.checkList);
        this.$emit(`changeUpdateHouseComponent`, {
          houseData: this.checkList,
          component: `UpdateContract`
        });
      }
    },
    //取消
    close() {
      this.newUser = this.user;
      this.$emit('afterUpdateHouse', this.newUser);
    }
  }
};
</script>

<style lang="scss" scoped>
.container {
  width: 90%;
  margin: 0 auto;
  .form {
    ::v-deep .el-form {
      .el-input__inner {
        width: 150px;
      }
      .el-form-item__label {
        margin-right: 5px;
      }
    }
  }
  .table {
    p {
      margin-bottom: 15px;
    }
  }
  .btn {
    margin-top: 15px;
    padding-left: 5px;
  }
}
</style>
