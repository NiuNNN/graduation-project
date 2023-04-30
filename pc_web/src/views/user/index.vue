<template>
  <div class="main-container">
    <div class="left">
      <div class="header-container">用 户 设 置</div>
      <div class="information-container">
        <div class="avatar-container">
          <div class="avatar">
            <input type="file" accept="image/jpeg" style="display: none" @change="onFileChange" ref="iptRef" />
            <img v-imageerror="defaultImg" :src="user.avatar" alt="" @click="uploadAvatar" />
            <div class="camera" @click="uploadAvatar"><i class="el-icon-camera-solid"></i></div>
          </div>
          <p>{{ user.username }}</p>
          <p>最 近 更 新 时 间：{{ user.remark }}</p>
        </div>
        <div class="choose">
          <ul>
            <li :class="{ li_active: isCurrent == `PersonalInformation` }" @click="changeInformation(`PersonalInformation`)">
              <p><i :class="{ 'el-icon-user': true, i_active: isCurrent === `PersonalInformation` }"></i>个 人 信 息</p>
            </li>
            <li :class="{ li_active: isCurrent === `FinancialInformation` }" @click="changeInformation(`FinancialInformation`)">
              <p><i :class="{ 'el-icon-coin': true, i_active: isCurrent === `FinancialInformation` }"> </i>财 务 信 息</p>
            </li>
            <li :class="{ li_active: isCurrent === `ContractInformation` }" @click="changeInformation(`ContractInformation`)">
              <p><i :class="{ 'el-icon-tickets': true, i_active: isCurrent === `ContractInformation` }"> </i>合 同 信 息</p>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="right">
      <component :is="isComponent"></component>
    </div>
  </div>
</template>

<script>
import PersonalInformation from '@/components/user/PersonalInformation.vue';
import ContractInformation from '@/components/user/ContractInformation.vue';
import FinancialInformation from '@/components/user/FinancialInformation.vue';
import { uploadAvatarAPI } from '@/api/user';
export default {
  components: {
    PersonalInformation,
    ContractInformation,
    FinancialInformation
  },
  data() {
    return {
      user: {
        avatar: '',
        remark: ''
      },
      isComponent: 'PersonalInformation',
      isCurrent: 'PersonalInformation',
      defaultImg: require('@/assets/image/avatar_default.png')
    };
  },
  created() {
    this.user = { ...this.$store.getters.user };
  },
  methods: {
    //修改点击样式
    changeInformation(name) {
      this.isComponent = name;
      this.isCurrent = name;
    },
    uploadAvatar() {
      this.$refs.iptRef.click();
    },
    /**
     * 图片被修改后上传
     * 通过转换为Base64进行回显
     */
    onFileChange(e) {
      const files = e.target.files;
      if (files.length === 0) {
        return; // 说明文件选择的窗口打开了，但是它一个文件都没选择就点击了确定关闭了选择弹框
      } else {
        if (this.beforeAvatarUpload(files[0])) {
          //校检上传的文件
          const fr = new FileReader();
          fr.readAsDataURL(files[0]); // 传入文件对象开始阅读
          fr.onload = async e => {
            // onload等待把文件读成base64字符串后会触发onload事件函数
            // e.target.result的值就是读完的结果
            this.user.avatar = e.target.result; // 赋予给变量，让他显示在img的src里

            const form = new FormData();
            form.append(`file`, files[0]);
            form.append(`username`, this.$store.getters.username);
            await uploadAvatarAPI(form);
            this.$store.dispatch('user/getUser');
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
    }
  }
};
</script>

<style lang="scss" scoped>
.main-container {
  display: flex;
  justify-content: space-between;
  .left {
    margin-right: 2%;
    width: 28%;
    height: 100%;
    // background-color: pink;
    .header-container {
      width: 100%;
      height: 40px;
      font-size: 24px;
      line-height: 40px;
      color: #3a3f63;
    }
    .information-container {
      margin-top: 25px;
      padding: 50px 15px;
      width: 100%;
      height: 100%;
      background-color: #fff;
      border-radius: 8px;
      .avatar-container {
        width: 100%;
        height: 100%;
        .avatar {
          position: relative;
          width: 120px;
          height: 120px;
          margin: 0 auto 12px;
          border-radius: 50%;
          img {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            cursor: pointer;
          }
          .camera {
            position: absolute;
            right: -80px;
            bottom: 28px;
            width: 28px;
            height: 28px;
            background-color: #fff;
            line-height: 28px;
            text-align: center;
            position: relative;
            border-radius: 50%;
            color: #858ebd;
            box-shadow: 0px 4px 16px rgba(179, 192, 231, 0.32);
            cursor: pointer;
          }
        }
        p {
          font-size: 16px;
          color: #3a3f63;
          text-align: center;
        }
        p:last-child {
          font-size: 12px;
          color: #5a607f;
        }
      }
      .choose {
        width: 90%;
        height: 100%;
        margin: 20px auto;
        ul {
          li {
            width: 100%;
            height: 56px;
            border-radius: 8px;
            line-height: 56px;
            cursor: pointer;
            text-align: center;
            p {
              color: #2b2f42;
              font-size: 14px;
              i {
                margin-top: 10px;
                display: inline-block;
                margin-right: 15px;
                border-radius: 8px;
                width: 40px;
                height: 40px;
                text-align: center;
                font-size: 18px;
                line-height: 40px;
                color: #858ebd;
                background-color: #f4f7ff;
              }
            }
          }
          .li_active {
            box-shadow: 0px 4px 16px rgba(179, 192, 231, 0.32) !important;
          }
          .i_active {
            color: #fff;
            background: linear-gradient(45deg, #4f8aff 0%, #4b5eff 100%) !important;
          }
        }
      }
    }
  }
  .right {
    width: 70%;
    height: 100%;
  }
}
</style>
