<template>
  <div class="forget">
    <h1>FORGOT PASSWORD?</h1>
    <p class="detail">Don't worry! Enter your registration information to remake the pasword.</p>
    <el-form :model="ForgetForm" :rules="rules" ref="ForgetForm" label-width="100px" class="ForgetForm" label-position="top">
      <el-row>
        <el-col :span="24">
          <el-form-item label="Account" prop="username">
            <el-input v-model="ForgetForm.username" placeholder="Please enter your account"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="Name" prop="name" class="gap">
            <el-input v-model="ForgetForm.name" placeholder="Name"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="Phone" prop="phone">
            <el-input v-model="ForgetForm.phone" placeholder="Please enter your Phone"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="New Password" prop="pass" class="gap">
            <el-input v-model="ForgetForm.pass" placeholder="New Password"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Confirm Password" prop="checkPass" class="gap" style="float: right">
            <el-input v-model="ForgetForm.checkPass" placeholder="Confirm Password"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <p class="back" @click="change">Back to Register?</p>
    <el-button type="info" round @click="handleConfirm" :loading="loading">Confirm</el-button>
  </div>
</template>

<script>
import { validMobile } from '@/utils/validate';
export default {
  data() {
    const validateMobile = (rule, value, callback) => {
      validMobile(value) ? callback() : callback(new Error('Please enter the correct phone number'));
    };
    const validatePass = (rule, value, callback) => {
      if (this.checkPass !== '') {
        this.$refs.ForgetForm.validateField('checkPass');
      }
      callback();
    };
    const validatePass2 = (rule, value, callback) => {
      value != this.ForgetForm.pass ? callback(new Error('两次输入密码不一致!')) : callback();
    };
    return {
      ForgetForm: {
        username: '',
        name: '',
        pass: '',
        checkPass: '',
        phone: ''
      },
      rules: {
        phone: [
          { required: true, trigger: 'blur', message: 'Please enter your phone number' },
          {
            trigger: 'blur',
            validator: validateMobile // 校验手机号
          }
        ],
        pass: [
          { required: true, trigger: 'blur', message: 'Please enter your new password' },
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { required: true, trigger: 'blur', message: 'Please confimr your new password' },
          { validator: validatePass2, trigger: 'blur' }
        ],
        username: [
          { required: true, message: 'Please enter your account', trigger: 'blur' },
          { min: 8, max: 16, message: 'Please enter the correct account', trigger: 'blur' }
        ],
        name: [{ required: true, message: 'Please enter your name', trigger: 'blur' }]
      },
      loading: false
    };
  },
  methods: {
    change() {
      this.$store.dispatch('setting/changeLogin', 'Login');
    },
    handleConfirm() {
      this.loading = true;
    }
  }
};
</script>

<style lang="scss" scoped>
@import '@/styles/index.scss';

.forget {
  width: 640px / $rootSize;
  height: 600px / $rootSize;
  // background-color: pink;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  h1 {
    font-size: 32px / $rootSize;
    color: 3A3F63;
    font-weight: 400;
  }
  .detail {
    font-size: 16px / $rootSize;
    color: #6a74a5;
  }
  .gap {
    width: 95%;
  }
  .back {
    float: right;
    width: 105px;
    font-size: 16px / $rootSize;
    color: #3a3f63;
    margin-top: 15px / $rootSize;
    &:hover {
      cursor: pointer;
      color: #4458fe;
    }
  }
  .el-button {
    margin-top: 30px;
    width: 240px;
    height: 54px;
    border-radius: 27px;
    background-blend-mode: normal;
    box-shadow: 0px 4px 16px #b3c0e7;
  }
}
</style>
