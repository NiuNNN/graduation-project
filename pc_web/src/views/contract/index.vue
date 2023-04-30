<template>
  <div class="main-container">
    <div class="header-container">
      <h1>合 同 管 理</h1>
    </div>
    <div class="bg">
      <div class="collapse-container">
        <el-collapse accordion style="border-top: none">
          <el-collapse-item>
            <template #title>
              <div class="collapse-title">
                <span>合 同 模 板</span>
              </div>
            </template>
            <div class="bg-container">
              <el-row :gutter="20">
                <el-col
                  type="flex"
                  justify="space-around"
                  :span="3"
                  style="margin-bottom: 20px"
                  v-for="(item, index) in baseData"
                  :key="index"
                >
                  <pdf-card
                    :contractObj="item"
                    :name="item.contractName"
                    @preview="preview"
                    @del="del"
                  ></pdf-card>
                </el-col>
              </el-row>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
    </div>
    <div class="bg">
      <div class="navbar">
        <span :class="{ current: isCurrent == `rent` }" @click="change(`rent`)"
          >租 赁 合 同</span
        >
        <span :class="{ current: isCurrent == `work` }" @click="change(`work`)"
          >劳 动 合 同</span
        >
        <span :class="{ current: isCurrent == `lose` }" @click="change(`lose`)"
          >失 效 合 同</span
        >
      </div>
      <div class="bg-container" style="height: 520px" v-loading="loading">
        <template v-if="Data.length > 0">
          <el-row :gutter="20">
            <el-col
              type="flex"
              justify="space-around"
              :span="3"
              style="margin-bottom: 20px"
              v-for="(item, index) in Data"
              :key="index"
            >
              <pdf-card
                :contractObj="item"
                :isShow="false"
                :name="item.url"
                @preview="preview"
                @del="del"
              ></pdf-card>
            </el-col>
          </el-row>
        </template>
        <template v-else>
          <el-empty :image-size="200"></el-empty>
        </template>
        <!-- 分页器 -->
        <div class="pagination">
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page="pagination.currentPage"
            :page-size="pagination.pageSize"
            background
            layout="prev, pager, next"
            :total="pagination.total"
          >
          </el-pagination>
        </div>
      </div>
    </div>
    <div class="dialog">
      <el-dialog
        title="PDF预览"
        :visible.sync="dialogVisible"
        width="70%"
        append-to-body
        top
        :before-close="handleClose"
        style="margin-top: 40px !important"
      >
        <iframe
          frameborder="0"
          style="width: 100%; height: 500px"
          :src="pdfSrc"
        ></iframe>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import PdfCard from "@/components/utils/PdfCard.vue";
import { getBaseContract, getAllContract } from "@/api/contract";
export default {
  data() {
    return {
      dialogVisible: false,
      pdfSrc: "", // 将从后台获取到的 PDF 文件地址赋值给这个字段
      baseData: [],
      isCurrent: "rent",
      Data: [],
      pagination: {
        currentPage: 1, //当前页码
        pageSize: 16, //每页显示的记录数
        total: 0,
      },
      loading: false,
    };
  },
  components: {
    PdfCard,
  },
  created() {
    this.getBaseContract();
    this.getAllContract();
  },
  methods: {
    change(current) {
      this.isCurrent = current;
      this.getAllContract();
    },
    async getBaseContract() {
      try {
        const { data } = await getBaseContract();
        this.baseData = data;
      } catch (error) {
        console.log(error);
      }
    },
    preview(url) {
      // console.log(url);
      this.pdfSrc = url;
      this.dialogVisible = true;
    },
    del() {},
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    //换页
    handleCurrentChange(currentPage) {
      this.pagination.currentPage = currentPage;
      this.getAllContract();
    },
    async getAllContract() {
      try {
        this.loading = true;
        const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
        // console.log(this.state);
        // console.log(this.kindId);
        const { data } = await getAllContract(param, {
          state: this.state,
          kindId: this.kindId,
        });
        this.Data = data.records;
        this.pagination.currentPage = data.current;
        this.pagination.total = data.total;
        this.pagination.pageSize = data.size;
        // console.log(data);
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
      }
    },
  },
  computed: {
    state() {
      return this.isCurrent != `lose` ? 1 : 0;
    },
    kindId() {
      let result = "";
      if (this.isCurrent === `work`) result = 2;
      if (this.isCurrent === `rent`) result = 1;
      if (this.isCurrent === `lose`) result = 0;
      return result;
    },
  },
};
</script>

<style lang="scss" scoped>
.main-container {
  .header-container {
    display: flex;
    justify-content: space-between;
    h1 {
      font-size: 24px;
      line-height: 40px;
      color: #3a3f63;
      font-weight: 400;
    }
  }
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
        border-top: 1px solid #e6e9f4;
      }
    }
    .navbar {
      height: 70px;
      width: 100%;
      padding: 25px 25px 0 25px;
      border-bottom: 1px solid #e6e9f4;

      span {
        display: inline-block;
        margin-right: 15px;
        width: 120px;
        height: 44px;
        text-align: center;
        font-size: 16px;
        color: #5a607f;
        cursor: pointer;

        &:hover {
          color: #4458fe;
        }
      }

      .current {
        color: #4458fe;
        border-radius: 2px;
        border-bottom: 3px solid #4458fe !important;
      }
    }
    .bg-container {
      padding: 25px;
    }
  }
  .el-empty {
    padding: 50px 0 !important;
  }
}
</style>
