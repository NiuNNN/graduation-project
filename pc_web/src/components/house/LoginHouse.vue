<template>
  <div class="container">
    <div v-if="active == 0">
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
          <el-table-column prop="houseName" label="房号"> </el-table-column>
          <el-table-column prop="styleName" label="房型"> </el-table-column>
          <el-table-column prop="balcony" label="阳台"> </el-table-column>
          <el-table-column prop="area" label="面积(㎡)"> </el-table-column>
          <el-table-column prop="price" label="房屋租金(月/元)"></el-table-column>
          <template #empty>
            <el-empty :image-size="80"></el-empty>
          </template>
        </el-table>
      </div>
      <div class="btn">
        <el-button type="primary" @click="submit1">确 定</el-button>
        <el-button @click="close">取 消</el-button>
      </div>
    </div>
    <div v-if="active == 1">
      <div class="table">
        <p style="color: #303133; font-size: 14px">请选择杂费：</p>
        <el-table ref="multipleTable" :data="baseData" tooltip-effect="dark" style="width: 100%" height="438" size="mini" border @selection-change="handleSelectionChange" :row-key="getRowKey">
          <el-table-column type="selection" width="55" :reserve-selection="true"> </el-table-column>
          <el-table-column prop="baseName" label="收费名称" width="120"> </el-table-column>
          <el-table-column prop="price" label="价格(月/元)" width="120"> </el-table-column>
          <el-table-column prop="remark" label="收费说明"> </el-table-column>
        </el-table>
      </div>
      <div class="btn">
        <el-button type="primary" @click="submit2">确 定</el-button>
        <el-button @click="active--">上一步</el-button>
        <el-button @click="close">取 消</el-button>
      </div>
    </div>
    <div v-if="active == 2">
      <p style="color: #303133; font-size: 14px">已选择房间：</p>
      <el-table :data="checkList" style="width: 100%" @row-click="singleElection" highlight-current-row size="mini" border>
        <el-table-column prop="houseName" label="房号"> </el-table-column>
        <el-table-column prop="styleName" label="房型"> </el-table-column>
        <el-table-column prop="balcony" label="阳台"> </el-table-column>
        <el-table-column prop="area" label="面积(㎡)"> </el-table-column>
        <el-table-column prop="price" label="房屋租金(月/元)"></el-table-column>
        <el-table-column prop="deposit" label="总租金(月/元)"></el-table-column>
      </el-table>
      <p style="color: #303133; font-size: 14px; margin-top: 15px">已选择杂费：</p>
      <el-table :data="multipleSelection" style="width: 100%" @row-click="singleElection" highlight-current-row size="mini" height="300" border>
        <el-table-column prop="baseName" label="收费名称" width="120"> </el-table-column>
        <el-table-column prop="price" label="价格(月/元)" width="120"> </el-table-column>
        <el-table-column prop="remark" label="收费说明"> </el-table-column>
      </el-table>
      <div class="btn">
        <el-button type="primary" @click="submit3">确 定</el-button>
        <el-button @click="back">上一步</el-button>
        <el-button @click="close">取 消</el-button>
      </div>
    </div>
    <div v-if="active == 3">
      <p style="color: #303133; font-size: 14px">请签署合同：</p>
      <iframe frameborder="0" style="width: 100%; height: 500px" :src="pdfSrc"></iframe>
      <div class="btn">
        <el-button type="primary" @click="innerDrawer = true">确 定</el-button>
        <el-button @click="close">取 消</el-button>
      </div>
      <div class="drawer">
        <el-drawer title="电子签名" :wrapperClosable="false" :show-close="false" :append-to-body="true" :destroy-on-close="true" size="50%" :visible.sync="innerDrawer">
          <div style="padding: 30px">
            <sign-canvas @back="backContract" @commitSign="writeSign"></sign-canvas>
          </div>
        </el-drawer>
      </div>
    </div>
    <div v-if="active == 4">
      <pay :order="order" @closePayDrawer="close"></pay>
    </div>
  </div>
</template>

<script>
import { targetUrl } from '@/targetUrl.js';
import { getFloor } from '@/api/floor';
import { getAllStyleName } from '@/api/house_type';
import { getHouse } from '@/api/house';
import { getBasic } from '@/api/basic';
import { getDeposit, getPriceElse, getBaseIdStrList } from '@/utils/financial';
import { writeRentContract, writeSign } from '@/api/contract';
import SignCanvas from '../utils/SignCanvas.vue';
import { insertRent } from '@/api/rent';
import { generateOrder } from '@/api/order';
import Pay from '../financial/Pay.vue';
export default {
  components: {
    SignCanvas,
    Pay
  },
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
      active: 0,
      baseData: [],
      multipleSelection: [],
      pdfSrc: '',
      innerDrawer: false,
      order: {},
      total: ''
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
    async submit1() {
      if (this.checkList.length === 0) {
        this.$message.error('请选择房间，再进行下一步');
      } else {
        const { data } = await getBasic();
        this.baseData = data;
        // console.log(this.baseData);
        this.active++;
      }
    },
    getRowKey(row) {
      // console.log(row.baseId);
      return row.baseId;
    },
    //点击复选框事件
    handleSelectionChange(val) {
      this.multipleSelection = val;
      // console.log(this.multipleSelection);
    },
    submit2() {
      if (this.multipleSelection.length == 0) {
        this.$confirm('没有选择房屋杂费, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.checkList = getDeposit(this.multipleSelection, this.checkList);
          this.active++;
        });
      } else {
        this.checkList = getDeposit(this.multipleSelection, this.checkList);
        this.active++;
      }
    },
    submit3() {
      this.writeRentContract();
      this.active++;
    },
    //生成合同
    async writeRentContract() {
      try {
        this.$message.info('正在生成合同，请稍等...');
        let param = {
          name: this.user.name,
          area: this.checkList[0].area,
          priceArea: this.checkList[0].priceArea,
          priceElse: getPriceElse(this.multipleSelection),
          price: this.checkList[0].deposit,
          idcard: this.user.idcard,
          address: this.user.address,
          phone: this.user.phone
        };
        console.log(param);
        const { data } = await writeRentContract(param);
        this.pdfSrc = `${targetUrl}/view/Contract/${data}`;
        this.localSrc = data;
        console.log(data);
      } catch (error) {
        console.log(error);
      }
    },
    //返回合同
    backContract() {
      this.innerDrawer = false;
    },
    //签署合同
    async writeSign(e) {
      try {
        this.$message.info('正在处理合同，请稍等...');
        let param = {
          ...this.user,
          area: this.checkList[0].area,
          priceArea: this.checkList[0].priceArea,
          priceElse: getPriceElse(this.multipleSelection),
          price: this.checkList[0].price
        };
        this.total = this.checkList[0].price;
        const form = new FormData();
        form.append(`file`, e);
        form.append(`param`, JSON.stringify(param));
        form.append(`url`, this.localSrc);
        const { data } = await writeSign(form);
        this.pdfSrc = `${targetUrl}/view/Contract/${data}`;
        let rent = {
          houseId: this.checkList[0].houseId,
          userId: this.user.userId,
          deposit: this.checkList[0].deposit
        };
        const res = await insertRent({
          ...rent,
          basicList: getBaseIdStrList(this.multipleSelection)
        });
        this.$message.success('签署成功');
        this.rentId = res.data;
        const { data: order } = await generateOrder({
          rentId: this.rentId,
          total: this.checkList[0].price,
          userId: this.$store.getters.userId,
          type: '押金'
        });
        this.order = order;
        this.active++;
        // console.log(order);
        // this.innerDrawer=false
      } catch (error) {
        console.log(error);
      }
    },
    //取消
    close() {
      this.$emit('closeAddHouseDrawer');
    },
    back() {
      this.multipleSelection = [];
      this.active--;
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
