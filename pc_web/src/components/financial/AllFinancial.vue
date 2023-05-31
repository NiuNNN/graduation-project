<template>
  <div class="main-container">
    <div class="table-container">
      <div class="left">
        <div class="bg">
          <financial-chart :key="datekey"></financial-chart>
        </div>
      </div>
      <div class="right">
        <div class="table">
          <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" height="615" :default-sort="{ prop: 'date', order: 'descending' }" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"> </el-table-column>
            <el-table-column prop="date" label="时间" sortable width="80"> </el-table-column>
            <el-table-column prop="status" label="状态" width="75"> </el-table-column>
            <el-table-column align="right">
              <template slot="header" slot-scope="scope">
                <el-date-picker size="mini" v-model="month" type="month" placeholder="请选择时间" :picker-options="pickerBeginOption" value-format="yyyy-MM"> </el-date-picker>
                <el-button size="mini" type="primary" style="margin-left: 5px" icon="el-icon-search" @click="search"></el-button>
                <el-button size="mini" type="primary" style="margin-left: 5px" icon="el-icon-download" @click="download(`all`)"></el-button>
              </template>
              <template slot-scope="scope">
                <el-button size="mini" @click="showDrawer(scope.row)" :disabled="isGet">查 看</el-button>
                <el-button type="primary" size="mini" @click="download(`single`, scope.row)" :disabled="isGet">导 出</el-button>
              </template>
            </el-table-column>
            <template #empty>
              <el-empty :image-size="200"></el-empty>
            </template>
          </el-table>
        </div>
      </div>
      <div class="drawer">
        <el-drawer :title="title" :visible.sync="drawer" :destroy-on-close="true" size="50%">
          <!-- 审核信息 -->
          <div class="bg">
            <div class="main">
              <el-descriptions title=" 审核信息" :column="3" size="mini">
                <template slot="extra">
                  <el-button type="primary" size="small" @click="judgeGenerateFinancial" :disabled="this.financial.state == 1">操作</el-button>
                </template>
                <el-descriptions-item label="年月">{{ financial.date }}</el-descriptions-item>
                <el-descriptions-item label="审核状态">{{ financial.status }}</el-descriptions-item>
                <el-descriptions-item label="审核人">{{ financial.name }}</el-descriptions-item>
                <el-descriptions-item label="审核时间">{{ financial.time }}</el-descriptions-item>
                <el-descriptions-item label="水费总支出">{{ financial.outWater }}</el-descriptions-item>
                <el-descriptions-item label="电费总支出">{{ financial.outElectric }}</el-descriptions-item>
                <el-descriptions-item label="薪水总支出">{{ financial.outSalary }}</el-descriptions-item>
                <el-descriptions-item label="杂费总支出">{{ financial.outMis }}</el-descriptions-item>
                <el-descriptions-item label="水费总收入">{{ financial.inWater }}</el-descriptions-item>
                <el-descriptions-item label="电费总收入">{{ financial.inElectric }}</el-descriptions-item>
                <el-descriptions-item label="水费总盈利">{{ financial.proWater }}</el-descriptions-item>
                <el-descriptions-item label="电费总盈利">{{ financial.proElectric }}</el-descriptions-item>
                <el-descriptions-item label="杂费总收入">{{ financial.inMis }}</el-descriptions-item>
                <el-descriptions-item label="租金总收入">{{ financial.inRent }}</el-descriptions-item>
                <el-descriptions-item label="总收入">{{ financial.advanceTotal }}</el-descriptions-item>
                <el-descriptions-item label="总支出">{{ financial.realTotal }}</el-descriptions-item>
                <el-descriptions-item label="获利">{{ financial.finalTotal }}</el-descriptions-item>
              </el-descriptions>
            </div>
          </div>
          <!-- 房屋信息 -->
          <div class="bg">
            <div class="main">
              <el-descriptions title=" 房屋收入"> </el-descriptions>
              <el-table :data="houseTable" stripe style="width: 100%" height="250" size="small">
                <el-table-column prop="houseName" label="房号"> </el-table-column>
                <el-table-column prop="numElectric" label="用电量(度)">
                  <template slot-scope="scope">
                    <span v-if="scope.row.numElectric != null">{{ scope.row.numElectric }}</span>
                    <span v-else>待处理</span>
                  </template>
                </el-table-column>
                <el-table-column prop="costElectric" label="电费(元)">
                  <template slot-scope="scope">
                    <span v-if="scope.row.costElectric != null">￥{{ scope.row.costElectric }}</span>
                    <span v-else>待处理</span>
                  </template>
                </el-table-column>
                <el-table-column prop="numWater" label="用水量(方)">
                  <template slot-scope="scope">
                    <span v-if="scope.row.numWater != null">{{ scope.row.numWater }}</span>
                    <span v-else>待处理</span>
                  </template>
                </el-table-column>
                <el-table-column prop="costWater" label="水费(元)">
                  <template slot-scope="scope">
                    <span v-if="scope.row.costWater != null">￥{{ scope.row.costWater }}</span>
                    <span v-else>待处理</span>
                  </template>
                </el-table-column>
                <el-table-column prop="mis" label="杂费(元)">
                  <template slot-scope="scope">
                    <span v-if="scope.row.mis != null">￥{{ scope.row.mis }}</span>
                    <span v-else>待处理</span>
                  </template>
                </el-table-column>
                <el-table-column prop="total" label="总收费(元)">
                  <template slot-scope="scope">
                    <span v-if="scope.row.total != null">￥{{ scope.row.total }}</span>
                    <span v-else>待处理</span>
                  </template>
                </el-table-column>
                <template #empty>
                  <el-empty :image-size="80"></el-empty>
                </template>
              </el-table>
            </div>
          </div>
          <!-- 薪水信息 -->
          <div class="bg">
            <div class="main">
              <el-descriptions title=" 薪水支出"> </el-descriptions>
              <el-table :data="salaryTable" stripe style="width: 100%" height="250" size="small">
                <el-table-column prop="username" label="账号" width="120"> </el-table-column>
                <el-table-column prop="name" label="姓名" width="80"> </el-table-column>
                <el-table-column prop="roleName" label="职位"> </el-table-column>
                <el-table-column label="基本薪水(元)">
                  <template slot-scope="scope">
                    <span>￥{{ scope.row.basePrice }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="福利(元)">
                  <template slot-scope="scope">
                    <span>￥{{ scope.row.elsePrice }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="price" label="总薪水(元)">
                  <template slot-scope="scope">
                    <span>￥{{ scope.row.price }}</span>
                  </template>
                </el-table-column>
                <template #empty>
                  <el-empty :image-size="80"></el-empty>
                </template>
              </el-table>
            </div>
          </div>
          <!-- 杂费信息 -->
          <div class="bg">
            <div class="main">
              <el-descriptions title=" 其他支出"> </el-descriptions>
              <el-table :data="misTable" stripe style="width: 100%" height="250" size="small">
                <el-table-column prop="time" label="时间"> </el-table-column>
                <el-table-column prop="type" label="支出种类"> </el-table-column>
                <el-table-column prop="total" label="金额(元)"> </el-table-column>
                <template #empty>
                  <el-empty :image-size="80"></el-empty>
                </template>
              </el-table>
            </div>
          </div>
        </el-drawer>
      </div>
      <div class="dialog">
        <el-dialog title="水电标准信息" :visible.sync="dialogFormVisible" width="40%">
          <el-form :model="form" :rules="rules">
            <el-form-item label="标准水费:" :label-width="formLabelWidth" prop="water">
              <el-input v-model="form.water" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="标准电费:" :label-width="formLabelWidth" prop="electric">
              <el-input v-model="form.electric" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="generateFinancial">确 定</el-button>
          </div>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import * as permission from '@/utils/permission';
import FinancialChart from '../echart/FinancialChart.vue';
import { getAllFinancial, getFinancialExcel, getFinancialDetail, judgeGenerateFinancial, generateFinancial } from '@/api/financial';
import { validatePassword } from '@/api/user';
import { validNumber } from '@/utils/validate';
export default {
  components: {
    FinancialChart
  },
  async created() {
    await this.getAllFinancial();
  },
  data() {
    const validateNumber = (rule, value, callback) => {
      validNumber(value) ? callback() : callback(new Error('请输入数字'));
    };
    return {
      pickerBeginOption: {
        disabledDate: time => {
          return time.getTime() > Date.now();
        }
      },
      datekey: Date.now(),
      dialogFormVisible: false,
      month: '',
      tableData: [],
      houseTable: [],
      salaryTable: [],
      misTable: [],
      multipleSelection: [],
      drawer: false,
      title: '',
      financial: {},
      formLabelWidth: '120px',
      form: {
        water: '',
        electric: ''
      },
      rules: {
        water: [
          { required: true, message: '请输入标准水费', trigger: 'blur' },
          {
            validator: validateNumber,
            message: '水费必须为数字值',
            trigger: 'blur'
          }
        ],
        electric: [
          { required: true, message: '请输入标准电费', trigger: 'blur' },
          {
            validator: validateNumber,
            message: '电费费必须为数字值',
            trigger: 'blur'
          }
        ]
      }
    };
  },
  methods: {
    //判断财务信息
    async judgeGenerateFinancial() {
      this.$prompt('请输入密码', `${this.financial.date} 财务账单生成`, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputType: 'password',
        inputPattern: /^\w{5,12}$/,
        inputErrorMessage: '密码格式不正确'
      }).then(async ({ value }) => {
        try {
          // console.log(value);
          await validatePassword({
            password: value,
            username: this.$store.getters.username
          });
          await judgeGenerateFinancial({ date: this.financial.date });
          this.dialogFormVisible = true;
        } catch (error) {
          console.log(error);
        } finally {
          this.getAllFinancial();
        }
      });
    },
    //改变复选框
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //获取全部财务信息
    async getAllFinancial() {
      try {
        const { data } = await getAllFinancial({ date: this.month });
        this.tableData = data;
      } catch (error) {
        console.log(error);
      }
    },
    //查询
    async search() {
      await this.getAllFinancial();
      this.month = '';
    },
    //导出
    download(type, row) {
      if (type == `all`) {
        if (this.multipleSelection.length > 0) {
          this.$prompt('请输入密码', '导出账单', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            inputType: 'password',
            inputPattern: /^\w{5,12}$/,
            inputErrorMessage: '密码格式不正确'
          }).then(async ({ value }) => {
            try {
              // console.log(value);
              await validatePassword({
                password: value,
                username: this.$store.getters.username
              });
              await getFinancialExcel({ list: JSON.stringify(this.multipleSelection) });
            } catch (error) {
              console.log(error);
            } finally {
              this.multipleSelection = [];
            }
          });
        } else {
          this.$message.info('请勾选后导出账单...');
        }
      } else if (type == `single`) {
        this.$prompt('请输入密码', '导出账单', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputType: 'password',
          inputPattern: /^\w{5,12}$/,
          inputErrorMessage: '密码格式不正确'
        }).then(async ({ value }) => {
          try {
            // console.log(value);
            await validatePassword({
              password: value,
              username: this.$store.getters.username
            });
            let arr = [];
            arr.push(row);
            await getFinancialExcel({ list: JSON.stringify(arr) });
          } catch (error) {
            console.log(error);
          }
        });
      }
    },
    //打开抽屉
    async showDrawer(row) {
      try {
        this.title = `${row.date} 财务信息`;
        this.financial = row;
        this.drawer = true;
        const { data } = await getFinancialDetail({ date: row.date });
        // console.log(data);
        this.houseTable = data.houseTable[0];
        this.salaryTable = data.salaryTable[0];
        this.misTable = data.misTable[0];
      } catch (error) {
        console.log(error);
      }
    },
    //生成财务信息
    generateFinancial() {
      this.$confirm('请确认输入的标准水电金额是否正确?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          try {
            console.log(this.financial);
            await generateFinancial({ userId: this.$store.getters.userId, date: this.financial.date, ...this.form });
            this.$message.success(`${this.title} 财务信息生成成功...`);
          } catch (error) {
            console.log(error);
          } finally {
            this.getAllFinancial();
            this.datekey = Date.now();
            this.dialogFormVisible = false;
            this.drawer = false;
          }
        })
        .catch(() => {});
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
    }
  }
};
</script>

<style lang="scss" scoped>
.main-container {
  .table-container {
    // height: 649px;
    width: 100%;
    margin-top: 25px;
    background-color: #fff;
    border-radius: 8px;
    padding: 0 25px;
    display: flex;
    .left {
      width: 55%;
      .bg {
        width: 98%;
        margin-bottom: 15px;
        height: 615px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      }
    }
    .right {
      width: 45%;
      .table {
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      }
    }
    .drawer {
      .bg {
        width: 100%;
        padding: 10px 25px 15px 25px;
        .main {
          padding: 10px;
          box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        }
      }
    }
    .dialog {
      ::v-deep .el-form {
        .el-form-item {
          margin-bottom: 22px;
        }
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
  }
  .el-date-editor.el-input {
    width: 180px;
  }
}
</style>
