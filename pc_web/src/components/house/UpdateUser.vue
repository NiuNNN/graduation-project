<template>
  <div class="container" v-loading="loading">
    <h3>账号:{{ user.username }}</h3>
    <el-form :inline="true" :model="userForm" class="demo-form-inline" size="small" ref="userForm" :rules="rules">
      <el-form-item label="用户电话:" prop="phone">
        <el-input v-model="userForm.phone" placeholder="请输入新的电话号码"></el-input>
      </el-form-item>
      <el-form-item label="密码:" prop="password">
        <el-input v-model="userForm.password" placeholder="请输入新的密码"></el-input>
      </el-form-item>
    </el-form>
    <div class="idcard">
      <span class="idcard-header">身份证照片</span>
      <div class="idcard-flex">
        <div @click="uploadAvatar(`font`)" :class="[{ error: isErrorFont }, { idcard_container: true }]">
          <input type="file" accept="image/jpeg" style="display: none" @change="onFileChange($event, `font`)" ref="fontRef" />

          <img :src="fontUrl" alt="" v-if="fontUrl" />
          <i v-else class="el-icon-plus avatar-uploader-icon">带国徽面</i>
        </div>
        <div @click="uploadAvatar(`back`)" :class="[{ error: isErrorBack }, { idcard_container: true }]">
          <input type="file" accept="image/jpeg" style="display: none" @change="onFileChange($event, `back`)" ref="backRef" />
          <img :src="backUrl" alt="" v-if="backUrl" />
          <i v-else class="el-icon-plus avatar-uploader-icon">带头像面</i>
        </div>
      </div>
    </div>
    <div class="btn">
      <el-button type="primary" @click="submit">确 定</el-button>
      <el-button @click="close">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { validateIDCard, updateUserByIDCard, updateUser } from '@/api/user';
import { isEmpty } from '@/utils/string';
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
      fontUrl: '',
      backUrl: '',
      fontFile: '',
      backFile: '',
      username: '',
      isErrorFont: false,
      isErrorBack: false,
      rules: {
        phone: [
          { required: false, trigger: 'blur', message: '请输入住户手机' },
          {
            pattern: /^1[3-9]\d{9}$/,
            message: '请输入正确的手机号',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: false,
            message: '请输入密码',
            trigger: 'blur'
          },
          {
            pattern: /^\w{5,12}$/,
            message: '请输入正确的秘密',
            trigger: 'blur'
          }
        ]
      },
      isfont: false,
      isback: false,
      userForm: { phone: '', password: '' },
      loading: false,
      newUser: {}
    };
  },
  methods: {
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
    //提交
    submit() {
      this.loading = true;
      this.newUser = this.user;
      this.$refs.userForm.validate(async isOK => {
        if (isOK) {
          if (this.isfont && this.isback) {
            try {
              const form = new FormData();
              form.append(`font`, this.fontFile);
              form.append(`back`, this.backFile);
              form.append(`username`, this.user.username);
              const { data } = await updateUserByIDCard(form);
              this.newUser = { ...this.newUser, ...data };
              console.log(this.newUser);
              this.$message.success('修改成功');
            } catch (error) {
              console.log(error);
            } finally {
              this.loading = false;
            }
          }
          if (!isEmpty(this.userForm.password) || !isEmpty(this.userForm.phone)) {
            try {
              this.loading = true;
              await updateUser({
                ...this.userForm,
                username: this.user.username
              });
              this.newUser = { ...this.newUser, ...this.userForm };
              if (!isEmpty(this.userForm.phone)) {
              }
              console.log(this.newUser);
              this.$message.success('修改成功');
            } catch (error) {
              console.log(error);
            } finally {
              this.loading = false;
            }
          }
          this.loading = false;
          this.$emit('updateUser', this.newUser);
        } else {
          this.loading = false;
        }
      });
    },
    close() {
      this.newUser = this.user;
      this.$emit('updateUser', this.newUser);
    }
  }
};
</script>

<style lang="scss" scoped>
.container {
  width: 80%;
  margin: 0 auto;
  h3 {
    height: 40px;
    line-height: 40px;
    border-left: 5px solid #3a3f63;
    font-size: 14px;
    color: #303133;
    font-weight: 400;
    padding-left: 5px;
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
    .idcard-flex {
      margin-top: 15px;
      display: flex;
      justify-content: space-around;
      align-items: center;
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
        border-color: #ff2855 !important;
      }
    }
  }
  .btn {
    margin-top: 15px;
    padding-left: 15px;
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
</style>
