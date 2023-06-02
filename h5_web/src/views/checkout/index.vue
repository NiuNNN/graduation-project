<template>
  <div class="container">
    <div class="header">
      <Header title="退房申请"></Header>
    </div>
    <div class="main" v-if="state === ''">
      <div class="bg">
        <van-cell title="房号" :value="house.houseName" />
        <van-cell title="录入时间" :value="house.loginTime" />
      </div>
      <div class="bg">
        <van-field v-model="message" rows="2" autosize label="留言" type="textarea" maxlength="50" placeholder="请输入留言" show-word-limit />
      </div>
      <div class="footer">
        <van-button round type="info" style="width: 80%" @click="checkout" :disabled="disabled">申 请 退 房</van-button>
      </div>
    </div>
    <div class="main" v-if="state === 0">
      <van-cell is-link @click="showCancel = true">
        <template #title>
          <p>已提交申请</p>
        </template>
        <template #label>
          <div>申请时间：{{ checkoutData.start }}</div>
          <div>留言：{{ checkoutData.message }}</div>
        </template>
      </van-cell>
      <van-action-sheet v-model="showCancel" :actions="actions" @select="onSelect" />
    </div>
    <div class="main" v-if="state === 1">
      <van-cell>
        <template #title>
          <p style="color: #00d0b5">申请已通过</p>
        </template>
        <template #label>
          <div>申请时间：{{ checkoutData.start }}</div>
          <div>留言：{{ checkoutData.message }}</div>
        </template>
      </van-cell>
    </div>
    <div class="main" v-if="state === 2">
      <van-cell-group>
        <van-cell is-link @click="showCancel = true">
          <template #title>
            <p style="color: #ff2855">申请不通过</p>
          </template>
          <template #label>
            <div>申请时间：{{ checkoutData.start }}</div>
            <div>留言：{{ checkoutData.message }}</div>
          </template>
        </van-cell>
        <van-cell>
          <template #title>
            <p>详情</p>
          </template>
          <template #label>
            <div>具体问题: {{ checkoutData.remark }}</div>
          </template>
        </van-cell>
      </van-cell-group>
      <van-action-sheet v-model="showCancel" :actions="actions" @select="onSelect" />
    </div>
  </div>
</template>

<script>
import { insertCheckOut, getCheckOutState, cancelCheckOut } from '@/api/checkout';
import Header from '@/components/utils/HeaderVue.vue';
import { valueAllEmpty } from '@/utils/validate';
import { getHouseDetailByUserId } from '@/api/house';
export default {
  components: {
    Header
  },
  data() {
    return {
      message: '',
      state: '',
      showCancel: false,
      actions: [{ name: '重新提交' }],
      checkoutData: {},
      house: {}
    };
  },
  async created() {
    await this.reset();
  },
  methods: {
    async reset() {
      this.$toast.loading({
        duration: 0,
        message: '加载中...',
        forbidClick: true
      });
      await this.getHouseDetail();
      await this.getCheckOutState();
      this.$toast.clear();
    },
    //获取房屋信息
    async getHouseDetail() {
      try {
        const { data } = await getHouseDetailByUserId({
          userId: this.$store.getters.userId
        });
        this.house = data;
      } catch (error) {
        console.log(error);
      }
    },
    //申请退房
    checkout() {
      this.$dialog
        .confirm({
          title: '申请报修',
          message: '你确定要申请吗？'
        })
        .then(async () => {
          try {
            this.$toast.loading();
            const { data } = await insertCheckOut({
              userId: this.$store.getters.userId,
              message: this.message
            });
            this.checkoutData = data;
            this.state++;
            this.$toast.success('申请成功,待审核...');
          } catch (error) {
            console.log(error);
          }
        });
    },
    async getCheckOutState() {
      try {
        const { data } = await getCheckOutState({
          userId: this.$store.getters.userId
        });
        // console.log(data);
        if (data != null) {
          this.checkoutData = data;
          this.state = this.checkoutData.state;
        }
        // console.log(data);
      } catch (error) {
        console.log(error);
      }
    },
    async onSelect(item) {
      // 默认情况下点击选项时不会自动收起
      // 可以通过 close-on-click-action 属性开启自动收起
      // console.log(item);
      if (item.name == `重新提交`) {
        try {
          this.$toast.loading({
            duration: 0,
            message: '加载中...',
            forbidClick: true
          });
          this.checkoutData.state = 3;
          await cancelCheckOut(this.checkoutData);
          this.$toast.clear();
        } catch (error) {
          console.log(error);
        } finally {
          this.reset();
        }
        this.showCancel = false;
      }
    }
  },
  computed: {
    disabled() {
      return this.$store.getters.state == 1 ? false : true;
    }
  }
};
</script>

<style lang="less" scoped>
.container {
  .header {
    height: 60px;
  }
  .main {
    .bg,
    footer {
      margin-bottom: 15px;
    }
    .footer {
      text-align: center;
    }
    .van-action-sheet__content {
      width: 375px;
    }
  }
}
</style>
