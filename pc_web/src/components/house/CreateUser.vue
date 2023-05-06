<template>
  <div class="bg">
    <div class="collapse-container">
      <el-collapse v-model="activeName" accordion style="border-top: none" @change="createUserName" v-loading="loading">
        <el-collapse-item name="1">
          <template #title>
            <div class="collapse-title">
              <span>添 加 新 住 户</span>
            </div>
          </template>
          <div class="bg-container">
            <div class="insert">
              <div class="top">
                <el-steps :active="active" finish-status="success">
                  <el-step title="上传住户信息"></el-step>
                  <el-step title="确认住户信息"></el-step>
                  <el-step title="选择房屋信息"></el-step>
                  <el-step title="选择房屋杂费"></el-step>
                  <el-step title="确认房屋信息"></el-step>
                  <el-step title="签署合同"></el-step>
                  <el-step title="支付押金"></el-step>
                  <el-step title="完成"></el-step>
                </el-steps>
              </div>
              <div v-if="active === 0">
                <div class="middle">
                  <h3>账号: {{ username }}</h3>
                  <el-form :inline="true" :model="userForm" class="demo-form-inline" size="small" :rules="rules" ref="userForm">
                    <el-form-item label="住户电话:" prop="phone">
                      <el-input v-model="userForm.phone" placeholder="请输入住户电话"></el-input>
                    </el-form-item>
                  </el-form>
                  <div class="idcard">
                    <span class="idcard-header"><i style="color: #ff2855; margin-right: 4px">*</i>身份证照片</span>
                    <el-row :gutter="20" justify="space-between" style="padding: 0 25px">
                      <el-col :span="9">
                        <div @click="uploadAvatar(`font`)" :class="[{ error: isErrorFont }, { idcard_container: true }]">
                          <input type="file" accept="image/jpeg" style="display: none" @change="onFileChange($event, `font`)" ref="fontRef" />

                          <img :src="fontUrl" alt="" v-if="fontUrl" />
                          <i v-else class="el-icon-plus avatar-uploader-icon">带国徽面</i>
                        </div>
                      </el-col>
                      <el-col :span="9">
                        <div @click="uploadAvatar(`back`)" :class="[{ error: isErrorBack }, { idcard_container: true }]">
                          <input type="file" accept="image/jpeg" style="display: none" @change="onFileChange($event, `back`)" ref="backRef" />
                          <img :src="backUrl" alt="" v-if="backUrl" />
                          <i v-else class="el-icon-plus avatar-uploader-icon">带头像面</i>
                        </div>
                      </el-col>
                    </el-row>
                  </div>
                </div>
                <div class="bottom" style="margin-top: 60px">
                  <el-button type="primary" :disabled="isAdd" @click="firstStep" :loading="firstStepBtn">下一步</el-button>
                  <el-button @click="close">取 消</el-button>
                </div>
              </div>
              <div v-if="active === 1">
                <div class="middle">
                  <el-descriptions class="margin-top" :title="`账号：${user.username}`" :column="3">
                    <el-descriptions-item label="姓名">{{ user.name }}</el-descriptions-item>
                    <el-descriptions-item label="性别">{{ user.sex }}</el-descriptions-item>
                    <el-descriptions-item label="民族">{{ user.nation }}</el-descriptions-item>
                    <el-descriptions-item label="生日">{{ user.birthday }}</el-descriptions-item>
                    <el-descriptions-item label="身份证">{{ user.idcard }}</el-descriptions-item>
                    <el-descriptions-item label="手机号">{{ user.phone }}</el-descriptions-item>
                    <el-descriptions-item label="签订时间">{{ user.sign }}</el-descriptions-item>
                    <el-descriptions-item label="过期时间">{{ user.lose }}</el-descriptions-item>
                    <el-descriptions-item label="签订机构">{{ user.authority }}</el-descriptions-item>
                    <el-descriptions-item label="地址">{{ user.address }}</el-descriptions-item>
                  </el-descriptions>
                </div>
                <div class="bottom">
                  <template v-if="secondStepBtn">
                    <el-button type="primary" :disabled="true">确认无误后，下一步({{ second }}s)</el-button>
                  </template>
                  <template v-else>
                    <el-button type="primary" @click="secondStep" :disabled="isAdd">下一步</el-button>
                  </template>
                  <el-button @click="back()">返 回</el-button>
                </div>
              </div>
              <div v-if="active === 2">
                <div class="middle">
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
                    <el-table :data="tableData" style="width: 100%" @row-click="singleElection" highlight-current-row height="384" border>
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
                    </el-table>
                  </div>
                </div>
                <div class="bottom">
                  <el-button type="primary" :disabled="isAdd" @click="thirdStep">下一步</el-button>
                  <el-button :disabled="isAdd" @click="active = 8">跳过</el-button>
                </div>
              </div>
              <div v-if="active === 3">
                <div class="middle">
                  <div class="table">
                    <p style="color: #303133; font-size: 14px">请选择杂费：</p>
                    <el-table ref="multipleTable" :data="baseData" tooltip-effect="dark" style="width: 100%" height="438" border @selection-change="handleSelectionChange" :row-key="getRowKey">
                      <el-table-column type="selection" width="55" :reserve-selection="true"> </el-table-column>
                      <el-table-column prop="baseName" label="收费名称" width="120"> </el-table-column>
                      <el-table-column prop="price" label="价格(月/元)" width="120"> </el-table-column>
                      <el-table-column prop="remark" label="收费说明"> </el-table-column>
                    </el-table>
                  </div>
                </div>
                <div class="bottom">
                  <el-button type="primary" :disabled="isAdd" @click="active--">上一步</el-button>
                  <el-button type="primary" :disabled="isAdd" @click="forthStep">下一步</el-button>
                </div>
              </div>
              <div v-if="active === 4">
                <div class="middle">
                  <p style="color: #303133; font-size: 14px">已选择房间：</p>
                  <el-table :data="checkList" style="width: 100%" @row-click="singleElection" highlight-current-row size="mini" border>
                    <el-table-column prop="houseName" label="房号"> </el-table-column>
                    <el-table-column prop="styleName" label="房型"> </el-table-column>
                    <el-table-column prop="balcony" label="阳台"> </el-table-column>
                    <el-table-column prop="area" label="面积(㎡)"> </el-table-column>
                    <el-table-column prop="price" label="房屋租金(月/元)"></el-table-column>
                    <el-table-column prop="deposit" label="总租金(月/元)"></el-table-column>
                  </el-table>
                  <p style="color: #303133; font-size: 14px">已选择杂费：</p>
                  <el-table :data="multipleSelection" style="width: 100%" @row-click="singleElection" highlight-current-row size="mini" height="300" border>
                    <el-table-column prop="baseName" label="收费名称" width="120"> </el-table-column>
                    <el-table-column prop="price" label="价格(月/元)" width="120"> </el-table-column>
                    <el-table-column prop="remark" label="收费说明"> </el-table-column>
                  </el-table>
                </div>
                <div class="bottom">
                  <el-button type="primary" :disabled="isAdd" @click="backThird">上一步</el-button>
                  <el-button type="primary" :disabled="isAdd" @click="fithStep">下一步</el-button>
                </div>
              </div>
              <div v-if="active === 5">
                <div v-if="contractShow">
                  <div class="middle">
                    <p style="color: #303133; font-size: 14px">签署合同</p>
                    <iframe frameborder="0" style="width: 100%; height: 450px" :src="pdfSrc"></iframe>
                  </div>
                  <div class="bottom">
                    <el-button @click="dialogVisible = active--">上一步</el-button>
                    <el-button type="primary" @click="signVisible = true">确 定</el-button>
                  </div>
                </div>
                <div v-else>
                  <div class="middle">
                    <p style="color: #303133; font-size: 14px">合同签署完毕</p>
                    <iframe frameborder="0" style="width: 100%; height: 450px" :src="pdfSrc"></iframe>
                  </div>
                  <div class="bottom">
                    <el-button type="primary" @click="sixth">确 定</el-button>
                  </div>
                </div>

                <el-dialog title="电子签名" :visible.sync="signVisible" append-to-body :show-close="false" :close-on-click-modal="false">
                  <sign-canvas @back="backContract" @commitSign="writeSign"></sign-canvas>
                </el-dialog>
              </div>
              <div v-if="active === 6">
                <div class="middle">
                  <p style="color: #303133; font-size: 18px; border-left: 5px solid #3a3f63; padding-left: 5px">订单编号：{{ order.outTradeNo }}</p>
                  <div class="pay">{{ payTitle }}</div>
                  <p style="color: #303133; font-size: 18px; border-left: 5px solid #3a3f63; padding-left: 5px">请选择支付方式：</p>
                  <div style="margin-top: 30px; padding-left: 15px">
                    <el-button @click="alipay"><i style="margin-right: 5px; color: #1296db; font-size: 20px" class="iconfont">&#xe634;</i>支付宝支付</el-button>
                    <el-button @click="cashpay"><i style="margin-right: 5px; color: #1296db; font-size: 20px" class="iconfont">&#xea5b;</i>现金支付</el-button>
                  </div>
                  <p style="color: #303133; font-size: 14px; padding: 25px 15px 0">*支付后请点击此<el-button type="text" @click="judgePay">刷新</el-button>，若稍后支付，可点击<el-button type="text" @click="active += 2" style="color: #ff2855; margin-left: 0">跳过...</el-button></p>
                </div>
              </div>
              <div v-if="active === 8">
                <el-result icon="success" title="支付成功" subTitle="租客录入成功，3秒后刷新页面">
                  <template slot="extra">
                    <el-button type="primary" size="medium" @click="flash">确定</el-button>
                  </template>
                </el-result>
              </div>
            </div>
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
import { getDeposit, getPriceElse, getBaseIdStrList } from '@/utils/financial';
import { getBasic } from '@/api/basic';
import { getFloor } from '@/api/floor';
import { getAllStyleName } from '@/api/house_type';
import { validMobile } from '@/utils/validate';
import { getHouse } from '@/api/house';
import { insertRent } from '@/api/rent';
import { createUserName, validateIDCard, confirmMsg, insertUser } from '@/api/user';
import { targetUrl } from '@/targetUrl.js';
import { writeRentContract, writeSign } from '@/api/contract';
import * as permission from '@/utils/permission';
import SignCanvas from '@/components/utils/SignCanvas.vue';
import { payByCash, payByAlipay, generateOrder, judgePay } from '@/api/order';
export default {
  inject: ['reload'],
  components: {
    SignCanvas
  },
  data() {
    const validateMobile = (rule, value, callback) => {
      validMobile(value) ? callback() : callback(new Error('请输入正确的手机号'));
    };
    return {
      loading: false,
      activeName: '',
      active: 0,
      //第一步
      userForm: { phone: '' },
      fontUrl: '',
      backUrl: '',
      fontFile: '',
      backFile: '',
      username: '',
      isErrorFont: false,
      isErrorBack: false,
      rules: {
        phone: [
          { required: true, trigger: 'blur', message: '请输入住户手机' },
          {
            trigger: 'blur',
            validator: validateMobile // 校验手机号
          }
        ]
      },
      isfont: false,
      isback: false,
      firstStepBtn: false,
      //第二步
      user: {},
      secondStepBtn: true,
      second: 3,
      timer: null,
      //第三步
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
      //第四步
      baseData: [],
      multipleSelection: [],
      //第五步
      // dialogVisible: true,
      pdfSrc: '',
      signVisible: false,
      localSrc: '',
      // finishVisible: false,
      contractShow: true,
      rentId: '',
      total: '',
      order: {},
      //第六步
      payTitle: ''
    };
  },
  methods: {
    /**
     * 返回上一页
     */
    back() {
      this.active--;
      this.second = 3;
      clearInterval(this.timer);
    },
    close() {
      this.activeName = '';
      this.userForm.phone = '';
      this.fontUrl = '';
      this.backUrl = '';
      this.fontFile = '';
      this.backFile = '';
    },
    /**
     * 获取新的用户账号
     */
    async createUserName() {
      try {
        this.loading = true;
        const { data } = await createUserName({ roleId: 1 });
        this.username = data;
        const { data: floor } = await getFloor();
        this.floorList = floor;
        const { data: style } = await getAllStyleName();
        this.styleList = style;
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
      }
    },
    /**
     * 点击身份证事件
     */
    uploadAvatar(direction) {
      if (direction === `font`) {
        this.$refs.fontRef.click();
      }
      if (direction === `back`) {
        this.$refs.backRef.click();
      }
    },
    /**
     * 图片被修改后上传
     * 通过转换为Base64进行回显
     */
    onFileChange(e, direction) {
      this.loading = true;
      const files = e.target.files;
      if (files.length === 0) {
        return; // 说明文件选择的窗口打开了，但是它一个文件都没选择就点击了确定关闭了选择弹框
      } else {
        if (this.beforeAvatarUpload(files[0])) {
          //校检上传的文件
          const fr = new FileReader();
          fr.readAsDataURL(files[0]); // 传入文件对象开始阅读
          fr.onload = async e => {
            try {
              if (direction === `font`) {
                this.isfont = false;
                this.fontUrl = e.target.result;
                this.fontFile = files[0];
                this.isErrorFont = true;
              }
              if (direction === `back`) {
                this.isback = false;
                this.backUrl = e.target.result;
                this.backFile = files[0];
                this.isErrorBack = true;
              }
              const form = new FormData();
              form.append(`file`, files[0]);
              form.append(`direction`, direction);
              const { data } = await validateIDCard(form);
              if (data === `font`) {
                this.isErrorFont = false;
                this.isfont = true;
              }
              if (data === `back`) {
                this.isErrorBack = false;
                this.isback = true;
              }
              this.$message.success(`校验通过`);
            } catch (error) {
              console.log(error);
            } finally {
              this.loading = false;
            }
          };
        }
      }
    },
    //校检函数
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    /**
     * 第一步
     */
    firstStep() {
      this.firstStepBtn = true;
      this.$refs.userForm.validate(async isOK => {
        if (isOK) {
          if (this.isfont && this.isback) {
            try {
              const form = new FormData();
              form.append(`font`, this.fontFile);
              form.append(`back`, this.backFile);
              form.append(`phone`, this.userForm.phone);
              form.append(`username`, this.username);
              const { data } = await confirmMsg(form);
              // console.log(data);
              this.user = data;
              this.active++;
              this.countTimeOut();
            } catch (error) {
              console.log(error);
            } finally {
              this.firstStepBtn = false;
            }
          } else {
            this.firstStepBtn = false;
            this.$message.error('请上传对应的证件');
          }
        } else {
          this.firstStepBtn = false;
        }
      });
    },
    //第二部确认信息定时器
    countTimeOut() {
      this.timer = setInterval(() => {
        this.second--;
        if (this.second == 0) {
          this.second = 10;
          this.secondStepBtn = false;
          clearInterval(this.timer);
        }
      }, 1000);
    },
    /**
     * 第二步
     */
    async secondStep() {
      try {
        const form = new FormData();
        form.append(`font`, this.fontFile);
        form.append(`back`, this.backFile);
        form.append(`user`, JSON.stringify(this.user));
        // console.log(this.user);
        const { data } = await insertUser(form);
        this.user = data;
        this.$message.success('创建成功,请录入房间信息');
        this.active++;
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
    //第三步
    async thirdStep() {
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
    backThird() {
      this.multipleSelection = [];
      this.active--;
    },
    //第四步
    forthStep() {
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
    //第五步
    async fithStep() {
      try {
        this.$message.info('正在生成合同，请稍等...');
        this.loading = true;
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
        const { data } = await writeRentContract(param);
        this.pdfSrc = `${targetUrl}/view/Contract/${data}`;
        this.localSrc = data;
        // console.log(data);
        this.active++;
        this.loading = false;
      } catch (error) {
        console.log(error);
      }
    },
    //返回到合同页
    backContract() {
      this.signVisible = false;
      // this.dialogVisible = true;
    },
    //签名
    async writeSign(e) {
      try {
        this.$message.info('正在处理合同，请稍等...');
        let param = {
          area: this.checkList[0].area,
          priceArea: this.checkList[0].priceArea,
          priceElse: getPriceElse(this.multipleSelection),
          price: this.checkList[0].price,
          ...this.user
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
        this.payTitle = `待支付：￥${this.order.total}`;
        // console.log(res);
        this.signVisible = false;
        // this.dialogVisible = false;
        // this.finishVisible = true;
        this.contractShow = false;
      } catch (error) {
        console.log(error);
      }
    },
    //第六步
    async sixth() {
      // console.log(this.active);
      // this.finishVisible = false;
      this.active = 6;
      // console.log(this.active);
      // console.log(this.checkList[0].price);
    },
    //支付宝支付
    async alipay() {
      this.judgePay();
      try {
        // 首先先跳转到新的页面 然后等待支付结果
        const { data } = await payByAlipay({
          ...this.order,
          subject: '押金'
        });
        // console.log(data);
        const newWindow = window.open('', '_target');
        newWindow.document.write(data);
        newWindow.focus();
      } catch (error) {
        console.log(error);
      }
    },
    //现金支付
    cashpay() {
      this.judgePay();
      this.$confirm('确定收款成功, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        await payByCash({
          ...this.order
        });
        this.active = 8;
      });
    },
    //判断是否支付
    async judgePay() {
      this.loading = true;
      try {
        const { data } = await judgePay(this.order);
        if (data > 0) {
          this.$message.success('支付成功');
          this.payTitle = `已支付：￥${this.order.total}`;
          this.active = 8;
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
      }
    },
    //第七步
    flash() {
      this.reload();
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
.bg {
  height: 100%;
  width: 100%;
  margin-top: 25px;
  background-color: #fff;
  border-radius: 8px;
  .collapse-container {
    .collapse-title {
      padding: 0 25px;
      font-size: 16px;
      color: #5a607f;
      span {
        display: inline-block;
        margin-right: 15px;
        width: 120px;
        height: 44px;
        text-align: center;
        font-size: 16px;
        color: #5a607f;
      }
    }
    .bg-container {
      height: 100%;
      border-top: 1px solid #e6e9f4;
      .insert {
        width: 80%;
        height: 435px;
        margin: 25px auto;
        // background-color: pink;
        .six {
          width: 100%;
          padding: 0 50px;
          margin-top: 25px;
          height: 500px;
          .pay {
            width: 100%;
            height: 150px;
            font-size: 50px;
            line-height: 150px;
            text-align: center;
          }
        }
        .middle,
        .bottom {
          width: 100%;
          padding: 0 50px;
          margin-top: 25px;
          h3 {
            height: 40px;
            line-height: 40px;
            border-left: 5px solid #3a3f63;
            padding-left: 5px;
            margin-bottom: 5px;
            font-weight: 400;
          }
          .pay {
            width: 100%;
            height: 100px;
            margin: 30px auto;
            text-align: center;
            font-size: 35px;
            line-height: 100px;
            color: #ff2855;
            background-color: #e6e9f4;
            border-radius: 8px;
            border: 1px solid #e6e9f4;
          }
          ::v-deep .el-form {
            margin: 15px 0 0 0;
            padding-right: 40px;
            .el-form-item__label {
              height: 40px;
              line-height: 40px;
              border-left: 5px solid #3a3f63;
              font-size: 14px !important;
              padding: 0 15px 0 5px !important;
              color: #303133;
            }
            .el-input--small {
              padding-top: 5px;
            }
            .el-input__inner {
              border: 1px solid #ccc;
              color: #303133;

              &:hover {
                border: 1px solid #ccc;
              }

              &::placeholder {
                color: #606266;
              }
            }
          }
        }
        .bottom {
          padding-left: 100px;
        }
      }
    }
  }
}
::v-deep .idcard {
  .idcard-header {
    height: 40px;
    line-height: 40px;
    border-left: 5px solid #3a3f63;
    padding-left: 5px;
    display: inline-block;
    font-size: 14px;
    color: #303133;
  }
  .idcard_container {
    margin: 0 auto;
    width: 300px;
    height: 150px;
    border: 1px dashed #3a3f63;
    border-radius: 8px;
    text-align: center;
    cursor: pointer;
    i {
      font-size: 20px;
      line-height: 150px;
    }
    img {
      width: 298px;
      height: 148px;
      border-radius: 8px;
    }
  }
  .error {
    border-color: #ff2855;
  }
}
.form {
  ::v-deep .el-form {
    .el-input__inner {
      width: 175px;
    }
  }
}
::v-deep .el-collapse-item__content {
  height: 665px !important;
}
</style>
