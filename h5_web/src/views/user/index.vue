<template>
  <div class="container">
    <div class="avatar">
      <input type="file" accept="image/jpeg" style="display: none" ref="iptRef" @change="onFileChange" />
      <img v-imageerror="defaultImg" :src="avatar" alt="" @click="uploadAvatar" />
      <div class="camera"><van-icon name="edit" @click="uploadAvatar" /></div>
    </div>
    <div class="mian">
      <van-cell title="个 人 资 料" is-link icon="contact" :to="`userprofile/${this.$store.getters.username}`" />
      <van-cell title="住 房 信 息" is-link icon="hotel-o" :to="`userhouse/${this.$store.getters.username}`" />
      <van-cell title="密 码 设 置" is-link icon="shield-o" to="password" />
    </div>
    <div class="footer">
      <van-button round type="info" style="width: 80%" @click="logout">退 出 登 录</van-button>
    </div>
  </div>
</template>

<script>
import { uploadAvatarAPI } from '@/api/user';
export default {
  data() {
    return {
      defaultImg: require('@/assets/image/avatar_default.png'),
      avatar: ''
    };
  },
  created() {
    this.avatar = this.$store.getters.avatar;
  },
  methods: {
    uploadAvatar() {
      this.$refs.iptRef.click();
    },
    /**
     * 图片被修改后上传
     * 通过转换为Base64进行回显
     */
    onFileChange(e) {
      this.$toast.loading();
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
            this.avatar = e.target.result; // 赋予给变量，让他显示在img的src里
            const form = new FormData();
            form.append(`file`, files[0]);
            form.append(`username`, this.$store.getters.username);
            await uploadAvatarAPI(form);
            this.$store.dispatch('user/getUser');
            this.$toast.success('头像修改成功');
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
    logout() {
      this.$store.dispatch('user/logout');
    }
  }
};
</script>

<style lang="less" scoped>
.container {
  .avatar {
    position: relative;
    margin-top: 60px;
    text-align: center;
    img {
      width: 120px;
      height: 120px;
      border-radius: 50%;
      cursor: pointer;
    }
    .camera {
      position: absolute;
      right: 135px;
      bottom: 5px;
      width: 28px;
      height: 28px;
      background-color: #fff;
      line-height: 28px;
      text-align: center;
      border-radius: 50%;
      color: #858ebd;
      box-shadow: 0px 4px 16px rgba(179, 192, 231, 0.32);
      cursor: pointer;
    }
  }
  .footer {
    margin-top: 35px;
    text-align: center;
  }
}
</style>
