<template>
  <div class="welcome">
    <h1>WELCOME TO THE SYSTEM!</h1>
    <p class="detail">Sign in and start managing your house.</p>
    <el-form :model="user" :rules="rules" ref="LoginForm" label-width="100px" class="LoginForm" label-position="top">
      <el-form-item label="Account" prop="username">
        <el-input v-model="user.username" placeholder="Please enter your account"></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="password" @keyup.enter.native="handleLogin">
        <el-input v-model="user.password" show-password placeholder="Please enter your password"></el-input>
      </el-form-item>
    </el-form>
    <span class="back" @click="change">Forgot Password?</span>
    <el-button type="primary" round @click.native.prevent="handleLogin" :loading="loading">Sign in</el-button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: {
        username: '',
        password: ''
      },
      loading: false,
      rules: {
        username: [
          {
            required: true,
            message: 'Please enter your account',
            trigger: 'blur'
          },
          {
            min: 8,
            max: 16,
            message: 'Please enter the correct account',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: 'Please enter your password',
            trigger: 'blur'
          },
          {
            pattern: /^\w{5,12}$/,
            message: 'Please enter the correct password',
            trigger: 'blur'
          }
        ]
      }
    };
  },
  methods: {
    handleLogin() {
      this.$refs.LoginForm.validate(async isOK => {
        if (isOK) {
          this.loading = true;
          try {
            const res = await this.$store.dispatch('user/login', this.user);
            // console.log(res);
            this.$router.push('/');
          } catch (error) {
            console.log(error);
          } finally {
            this.loading = false;
          }
        } else {
          this.$message.error('Please enter the correct information!');
        }
      });
    },
    change() {
      this.$store.dispatch('setting/changeLogin', 'Forget');
    }
  }
};
</script>
<style lang="scss" scoped>
@import '@/styles/index.scss';
.welcome {
  width: 640px / $rootSize;
  height: 600px / $rootSize;
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
  .form {
    margin-top: 40px / $rootSize;
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
