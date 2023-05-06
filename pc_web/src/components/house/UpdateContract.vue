<template>
  <div class="container" v-loading="loading">
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
</template>

<script>
import { getDeposit, getPriceElse } from '@/utils/financial';
import { targetUrl } from '@/targetUrl.js';
import { changeRent } from '@/api/rent';
import { writeRentContract, writeSign, changeContractStateByUserId } from '@/api/contract';
import SignCanvas from '@/components/utils/SignCanvas.vue';
export default {
  components: {
    SignCanvas
  },
  props: {
    user: {
      type: Object,
      default: () => {
        return {};
      }
    },
    house: {
      type: Array,
      default: () => {
        return [];
      }
    },
    miscellaneous: {
      type: Array,
      default: () => {
        return [];
      }
    }
  },
  data() {
    return {
      pdfSrc: '',
      loading: false,
      newUser: {},
      newCheckList: [],
      innerDrawer: false
    };
  },
  created() {
    this.writeRentContract();
  },
  methods: {
    //取消
    close() {
      this.newUser = this.user;
      this.$emit('afterUpdateHouse', this.newUser);
    },
    //生成合同
    async writeRentContract() {
      try {
        console.log(this.miscellaneous);
        console.log(this.user);
        console.log(this.house);
        this.loading = true;
        this.newCheckList = getDeposit(this.miscellaneous, this.house);
        let param = {
          name: this.user.name,
          area: this.newCheckList[0].area,
          priceArea: this.newCheckList[0].priceArea,
          priceElse: getPriceElse(this.miscellaneous),
          price: this.newCheckList[0].deposit,
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
      } finally {
        this.loading = false;
      }
    },
    backContract() {
      this.innerDrawer = false;
    },
    //签署合同
    async writeSign(e) {
      try {
        console.log(e);
        let param = {
          area: this.newCheckList[0].area,
          priceArea: this.newCheckList[0].priceArea,
          priceElse: getPriceElse(this.miscellaneous),
          price: this.newCheckList[0].deposit,
          ...this.user
        };
        await changeContractStateByUserId({ userId: this.user.userId });
        const form = new FormData();
        form.append(`file`, e);
        form.append(`param`, JSON.stringify(param));
        form.append(`url`, this.localSrc);
        const { data } = await writeSign(form);
        this.pdfSrc = `${targetUrl}/view/Contract/${data}`;
        let rent = {
          houseId: this.newCheckList[0].houseId,
          userId: this.user.userId,
          deposit: this.newCheckList[0].deposit,
          oldHouseId: this.user.houseId,
          rentId: this.user.rentId
        };
        await changeRent(rent);
        this.$message.success('修改成功');
        this.newUser = { ...this.user, ...house };
      } catch (error) {
        console.log(error);
      } finally {
        this.$emit('afterUpdateHouse', this.newUser);
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.container {
  width: 90%;
  margin: 0 auto;
  .btn {
    margin-top: 15px;
    padding-left: 5px;
  }
}
</style>
