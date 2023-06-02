<template>
  <div class="main-container">
    <div class="header-container">
      <h1>公 告 管 理</h1>
      <el-button class="add" @click="openDrawer('add')">添 加 +</el-button>
    </div>
    <div class="table-container">
      <div class="navbar">
        <span :class="{ current: isCurrent == `all` }" @click="changeCurrent(`all`)">全 部</span>
        <span :class="{ current: isCurrent == `draft` }" @click="changeCurrent(`draft`)">草 稿 箱</span>
        <span :class="{ current: isCurrent == `del` }" @click="changeCurrent(`del`)">回 收 站</span>
      </div>
      <div>
        <div class="table">
          <div class="top">
            <el-form :inline="true" :model="searchForm" class="demo-form-inline" label-width="50px">
              <el-form-item label="时间">
                <el-date-picker v-model="searchForm.time" type="date" placeholder="请选择时间" :picker-options="pickerBeginOption" value-format="yyyy-MM-dd"> </el-date-picker>
              </el-form-item>
              <el-form-item label="姓名">
                <el-input v-model="searchForm.name" placeholder="请输入姓名"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" :disabled="isGet" @click="search" style="height: 35px">查询</el-button>
              </el-form-item>
            </el-form>
          </div>
          <div class="bottom">
            <el-table :data="tableData" style="width: 100%">
              <el-table-column prop="name" label="头像" width="100">
                <template slot-scope="scope">
                  <el-avatar v-imageerror="defaultImg" :src="`${targetUrl}/view/Avatar/${scope.row.avatar}`"></el-avatar>
                </template>
              </el-table-column>
              <el-table-column label="发 布 者" prop="name"> </el-table-column>
              <el-table-column label="标 题" prop="stem"> </el-table-column>
              <el-table-column label="浏 览 量" prop="views"> </el-table-column>
              <el-table-column label="时 间" prop="time"> </el-table-column>
              <el-table-column label="操作" width="240">
                <template #default="{ row }">
                  <el-button size="mini" @click="openDrawer('preview', row)" :disabled="isGet">查 看</el-button>
                  <el-button size="mini" type="primary" @click="openDrawer('edit', row)" :disabled="isEdit">编 辑</el-button>
                  <template v-if="isCurrent != `del`">
                    <el-button size="mini" type="danger" @click="handleDelete(row)" :disabled="isDel">删 除</el-button>
                  </template>
                </template>
              </el-table-column>
              <template #empty>
                <el-empty :image-size="200"></el-empty>
              </template>
            </el-table>
          </div>
        </div>
        <!-- 分页器 -->
        <div class="pagination">
          <el-pagination @current-change="handleCurrentChange" :current-page="pagination.currentPage" :page-size="pagination.pageSize" background layout="prev, pager, next" :total="pagination.total"> </el-pagination>
        </div>
      </div>
      <div class="drawer">
        <el-drawer :title="drawerTitle" size="50%" :visible.sync="isShowDrawer" :direction="direction" :before-close="handleClose">
          <div v-if="drawerType === 'preview'" class="article-preview">
            <h5>{{ report.stem }}</h5>
            <div v-html="report.content"></div>
          </div>
          <el-form v-else ref="reportForm" :model="report" :rules="rules" label-width="80px">
            <el-form-item label="标题" prop="stem">
              <el-input v-model="report.stem" placeholder="请输入公告标题"></el-input>
            </el-form-item>
            <el-form-item label="内容" prop="content">
              <quill-editor class="ql-editor-class" v-model="report.content" @blur="handleBlur"></quill-editor>
            </el-form-item>
            <el-form-item>
              <div style="margin-top: 20px">
                <el-button type="primary" @click="submit" :disabled="isAdd">发 送</el-button>
                <el-button type="info" @click="save" :disabled="isEdit">保 存</el-button>
                <el-button @click="isShowDrawer = false">取 消</el-button>
              </div>
            </el-form-item>
          </el-form>
        </el-drawer>
      </div>
    </div>
  </div>
</template>

<script>
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';
import { quillEditor } from 'vue-quill-editor';
import { targetUrl } from '@/targetUrl';
import { changeReport, getReportPage, postInsertReport, updatePreview, updateReport } from '@/api/report.js';
import * as permission from '@/utils/permission';
export default {
  components: {
    quillEditor
  },
  data() {
    return {
      pickerBeginOption: {
        disabledDate: time => {
          return time.getTime() > Date.now();
        }
      },
      targetUrl,
      defaultImg: require('@/assets/image/avatar_default.png'),
      searchForm: {
        time: '',
        name: ''
      },
      isCurrent: 'all',
      tableData: [],
      pagination: {
        currentPage: 1, //当前页码
        pageSize: 8, //每页显示的记录数
        total: 0
      },
      isShowDrawer: false,
      direction: 'rtl',
      inputContent: '',
      drawerType: 'add',
      report: {
        stem: '',
        content: ''
      },
      obj: {},
      rules: {
        stem: [{ required: true, message: '请输入公告标题', trigger: 'blur' }],
        content: [{ required: true, message: '请输入公告内容', trigger: 'blur' }]
      }
    };
  },
  created() {
    this.getAll();
  },
  methods: {
    //重置表以及页数
    reset() {
      this.pagination = {
        currentPage: 1, //当前页码
        pageSize: 8, //每页显示的记录数
        total: 0
      };
      this.searchForm = {
        name: '',
        time: ''
      };
      this.report = {
        stem: '',
        content: ''
      };
    },
    //按需获取公告信息
    async getAll() {
      try {
        const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
        const { data } = await getReportPage(param, {
          ...this.searchForm,
          ...this.obj
        });
        console.log(data);
        this.tableData = data.records;
        this.pagination.currentPage = data.current;
        this.pagination.total = data.total;
        this.pagination.pageSize = data.size;
      } catch (error) {
        console.log(error);
      }
    },
    //改变navbar选择
    changeCurrent(name) {
      this.isCurrent = name;
      this.reset();
    },
    //分页器换页
    handleCurrentChange(currentPage) {
      this.pagination.currentPage = currentPage;
      this.getAll();
    },
    handleClose(done) {
      if (this.report.stem == '' && this.report.content == '') {
        done();
      } else if (this.drawerType === 'preview') {
        this.isShowDrawer = false;
        this.changeCurrent(this.isCurrent);
      } else {
        if (this.isCurrent == 'add') {
          this.$confirm('公告信息还未保存，确定要退出吗？')
            .then(_ => {
              this.reset();
              done();
            })
            .catch(_ => {});
        } else {
          this.reset();
          done();
        }
      }
    },
    //打开文本
    async openDrawer(type, row) {
      this.drawerType = type;
      this.isShowDrawer = true;
      // console.log(row);
      if (type !== 'add') {
        this.report = { ...row };
      }
      if (type == 'preview') {
        // console.log(132);
        await updatePreview({ reportId: row.reportId });
        await this.getAll();
      }
    },
    handleBlur() {
      this.$refs.reportForm.validateField('content');
    },
    //发送公告
    async submit() {
      try {
        if (this.drawerType === 'add') {
          await this.$refs.reportForm.validate();
          await postInsertReport({
            username: this.$store.getters.username,
            ...this.report,
            state: 1
          });
        }
        if (this.drawerType === 'edit') {
          await updateReport({
            ...this.report,
            username: this.$store.getters.username,
            state: 1
          });
        }
        if (this.isCurrent == 'del') {
          await updateReport({
            ...this.report,
            username: this.$store.getters.username,
            state: 1
          });
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.$message.success('发送成功');
        this.isShowDrawer = false;
        this.getAll();
        this.reset();
      }
    },
    //保存信息
    async save() {
      try {
        await this.$refs.reportForm.validate();
        if (this.drawerType === 'add') {
          await postInsertReport({
            username: this.$store.getters.username,
            ...this.report,
            state: 2
          });
          this.$message.success('保存成功');
        } else if (this.isCurrent !== 'del') {
          await updateReport({
            username: this.$store.getters.username,
            ...this.report,
            state: 2
          });
          this.$message.success('保存成功');
          this.isShowDrawer = false;
        } else {
          await updateReport({
            username: this.$store.getters.username,
            ...this.report,
            state: 0
          });
          this.$message.success('保存成功');
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.getAll();
        this.isShowDrawer = false;
      }
    },
    //删除操作
    async handleDelete(row) {
      try {
        await changeReport(row.reportId, 0);
        this.$message.success('删除成功');
      } catch (error) {
        console.log(error);
      } finally {
        this.getAll();
      }
    },
    //查询信息
    async search() {
      await this.getAll();
      this.reset();
    }
  },
  watch: {
    isCurrent: {
      async handler(newVal, oldVal) {
        if (newVal == `all`) {
          this.obj = { state: 1 };
          await this.getAll();
        }
        if (newVal == `draft`) {
          this.obj = { state: 2 };
          await this.getAll();
        }
        if (newVal == `del`) {
          this.obj = { state: 0 };
          await this.getAll();
        }
      },
      immediate: true
    }
  },
  computed: {
    drawerTitle() {
      let title = '默认大标题';
      if (this.drawerType === 'add') title = '添 加 公 告';
      if (this.drawerType === 'preview') title = '公 告 预 览';
      if (this.drawerType === 'edit') title = '修 改 公 告';
      return title;
    },
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

    .add {
      width: 100px;
      height: 40px;
      border-radius: 27px;
      background: linear-gradient(45deg, #4f8aff 0%, #4b5eff 100%);
      background-blend-mode: normal;
      box-shadow: 0px 4px 16px #b3c0e7;
      color: #fff;
      font-size: 16px;
    }
  }
  .table-container {
    height: 100%;
    width: 100%;
    margin-top: 25px;
    background-color: #fff;
    border-radius: 8px;

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

    .table {
      padding: 25px;
      .top {
        padding-bottom: 15px;
        border-bottom: 1px solid #e6e9f4;
        ::v-deep .el-form {
          .el-form-item__label {
            font-size: 14px !important;
            padding: 0 15px 0 0 !important;
            color: #6a74a5;
            font-weight: 700;
          }

          .el-input__inner {
            width: 180px;
            height: 35px;
            border: 1px solid #ccc;
            color: #606266;
            text-align: left;

            &:hover {
              border: 1px solid #ccc;
            }
          }
        }
      }
      .bottom {
        .el-table {
          height: 467px;
        }
        .pagination {
          margin-top: 15px;
        }
      }
    }

    .pagination {
      padding: 0 25px 25px;
    }

    .drawer {
      ::v-deep .el-form {
        padding-right: 40px;

        .el-form-item__label {
          font-size: 14px !important;
          padding: 0 15px 0 0 !important;
        }

        .el-input__inner {
          border: 1px solid #ccc;
          color: #606266;

          &:hover {
            border: 1px solid #ccc;
          }

          &::placeholder {
            color: #606266;
          }
        }
      }

      .ql-editor-class {
        margin-top: 10px;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        line-height: 1.42;
        height: 100%;
        outline: none;
        padding: 0 !important;
        tab-size: 4;
        -moz-tab-size: 4;
        text-align: left;
        word-wrap: break-word;

        ::v-deep .ql-editor {
          height: 300px;
        }
      }

      .article-preview {
        padding: 0 40px 40px 40px;

        > h5 {
          font-size: 20px;
          color: #666;
          border-bottom: 1px dashed #ccc;
          padding-bottom: 30px;
          margin: 0 0 20px 0;
        }
      }
    }
  }
}
</style>
