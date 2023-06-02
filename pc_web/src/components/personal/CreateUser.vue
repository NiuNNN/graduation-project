<template>
  <div class="bg">
    <div class="collapse-container">
      <el-collapse v-model="activeName" accordion style="border-top: none" @change="getRole">
        <el-collapse-item name="1">
          <template #title>
            <div class="collapse-title">
              <span>添 加 新 员 工</span>
            </div>
          </template>
          <div class="bg-container">
            <div class="insert">
              <div class="top">
                <el-steps :active="active" finish-status="success">
                  <el-step title="上传员工信息"></el-step>
                  <el-step title="确认员工信息"></el-step>
                  <el-step title="签署合同"></el-step>
                  <el-step title="完成"></el-step>
                </el-steps>
              </div>
              <div v-if="active === 0">
                <div class="middle">
                  <el-form :inline="true" :model="userForm" class="demo-form-inline" size="small" :rules="rules" ref="userForm">
                    <el-row>
                      <el-col :span="8">
                        <el-form-item label="电话:" prop="phone">
                          <el-input v-model="userForm.phone" placeholder="请输入住户电话"></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="8">
                        <el-form-item label="职位:" prop="roleId">
                          <el-select v-model="userForm.roleId" placeholder="请选择职位">
                            <el-option v-for="(item, index) in roleList" :label="item.roleName" :value="item.roleId" :key="index"></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                    </el-row>
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
                  <el-button type="primary" :disabled="isAdd" @click="firstStep">下一步</el-button>
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
                  <el-descriptions class="margin-top" :title="`职位：${roleName}`" :column="3">
                    <el-descriptions-item v-for="(item, index) in userSalary" :key="index" :label="item.salaryName">{{ item.price }}</el-descriptions-item>
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
                  <p style="color: #303133; font-size: 14px">签署合同</p>
                  <iframe frameborder="0" style="width: 100%; height: 450px" :src="pdfSrc"></iframe>
                </div>
                <div class="bottom">
                  <el-button type="primary" @click="signVisible = true">确 定</el-button>
                </div>
                <el-dialog title="电子签名" :visible.sync="signVisible" append-to-body :show-close="false" :close-on-click-modal="false">
                  <sign-canvas @back="backContract" @commitSign="writeSign"></sign-canvas>
                </el-dialog>
              </div>
              <div v-if="active === 3">
                <div class="middle">
                  <p style="color: #303133; font-size: 14px">合同签署完毕</p>
                  <iframe frameborder="0" style="width: 100%; height: 450px" :src="pdfSrc"></iframe>
                </div>
                <div class="bottom">
                  <el-button type="primary" @click="active = 4">确 定</el-button>
                </div>
              </div>
              <div v-if="active === 4">
                <el-result icon="success" title="员工录用成功" subTitle="3秒后刷新页面">
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
import { validMobile } from '@/utils/validate';
import { createUserName, validateIDCard, confirmMsg, insertUser } from '@/api/user';
import { targetUrl } from '@/targetUrl.js';
import { writeWorkContract, writeWorkSign } from '@/api/contract';
import * as permission from '@/utils/permission';
import SignCanvas from '@/components/utils/SignCanvas.vue';
import { getRole, getUserSalary } from '@/api/role';
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
      activeName: '',
      active: 0,
      //第一步
      userForm: { phone: '', roleId: '', username: '' },
      fontUrl: '',
      backUrl: '',
      fontFile: '',
      backFile: '',
      roleList: [],
      isErrorFont: false,
      isErrorBack: false,
      rules: {
        phone: [
          { required: true, trigger: 'blur', message: '请输入住户手机' },
          {
            trigger: 'blur',
            validator: validateMobile // 校验手机号
          }
        ],
        roleId: [{ required: true, trigger: 'change', message: '请选择职位' }]
      },
      isfont: false,
      isback: false,
      firstStepBtn: false,
      //第二步
      user: {},
      userSalary: [],
      secondStepBtn: true,
      second: 3,
      roleName: '',
      timer: null,
      //第三步
      pdfSrc: '',
      signVisible: false,
      localSrc: ''
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
     * 获取职位信息
     */
    async getRole() {
      try {
        const { data } = await getRole();
        this.roleList = data;
      } catch (error) {
        console.log(error);
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
              const { data } = await confirmMsg(form);
              const { data: username } = await createUserName({ roleId: this.userForm.roleId });
              const { data: role } = await getUserSalary({ roleId: this.userForm.roleId });
              // console.log(role);
              this.roleName = role[0].roleName;
              this.userSalary = role;
              this.user = { ...data, ...this.userForm, username: username };
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
        form.append(`roleId`, JSON.stringify(this.userForm.roleId));
        // console.log(this.user);
        const { data } = await insertUser(form);
        this.user = data;
        this.$message.info('创建成功,正在生成合同，请稍等...');
        await this.generateContract();
        this.active++;
      } catch (error) {
        console.log(error);
      }
    },
    //三步
    async generateContract() {
      try {
        let param = {
          price: this.userSalary[0].price,
          priceElse: this.userSalary[1].price,
          ...this.user
        };
        const { data } = await writeWorkContract(param);
        // console.log(data);
        this.pdfSrc = `${targetUrl}/view/Contract/${data}`;
        this.localSrc = data;
        // console.log(data);
      } catch (error) {
        console.log(error);
      }
    },
    //返回到合同页
    backContract() {
      this.signVisible = false;
    },
    //签名
    async writeSign(e) {
      try {
        this.$message.info('正在处理合同，请稍等...');
        let param = {
          price: this.userSalary[0].price,
          priceElse: this.userSalary[1].price,
          ...this.user
        };
        const form = new FormData();
        form.append(`file`, e);
        form.append(`param`, JSON.stringify(param));
        form.append(`url`, this.localSrc);
        const { data } = await writeWorkSign(form);
        this.pdfSrc = `${targetUrl}/view/Contract/${data}`;
        this.$message.success('签署成功');
        // console.log(res);
        this.signVisible = false;
        this.active++;
      } catch (error) {
        console.log(error);
      }
    },
    //第四步
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
            // padding-right: 40px;
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
              width: 100%;
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
