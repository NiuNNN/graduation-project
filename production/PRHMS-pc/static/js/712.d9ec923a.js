"use strict";(self["webpackChunkpc_web"]=self["webpackChunkpc_web"]||[]).push([[712],{6712:function(t,e,n){n.r(e),n.d(e,{default:function(){return k}});var a=function(){var t=this,e=t._self._c;return e("div",{staticClass:"main-container"},[e("div",{staticClass:"header-container"},[e("h1",[t._v("退 房 处 理")]),e("el-button",{staticClass:"add",attrs:{disabled:t.isAdd},on:{click:t.addCheckOut}},[t._v("退 房 +")])],1),e("div",{staticClass:"table-container"},[e("div",{staticClass:"navbar"},[e("el-form",{ref:"form",attrs:{model:t.form,"label-width":"50px",inline:!0}},[e("el-form-item",{attrs:{label:"房号"}},[e("el-input",{attrs:{placeholder:"请输入房号"},model:{value:t.form.houseName,callback:function(e){t.$set(t.form,"houseName",e)},expression:"form.houseName"}})],1),e("el-form-item",{attrs:{label:"时间"}},[e("el-date-picker",{attrs:{type:"date",placeholder:"请选择时间","picker-options":t.pickerBeginOption,"value-format":"yyyy-MM-DD"},model:{value:t.form.month,callback:function(e){t.$set(t.form,"month",e)},expression:"form.month"}})],1),e("el-button",{staticStyle:{height:"38px"},attrs:{type:"primary"},on:{click:t.search}},[t._v("查询")])],1)],1),e("div",{staticClass:"table"},[e("el-table",{staticStyle:{width:"100%"},attrs:{data:t.tableData,stripe:"",height:"454","default-sort":{prop:"date",order:"descending"}},scopedSlots:t._u([{key:"empty",fn:function(){return[e("el-empty",{attrs:{"image-size":200}})]},proxy:!0}])},[e("el-table-column",{attrs:{prop:"start",label:"申请时间",sortable:"",width:"180"}}),e("el-table-column",{attrs:{prop:"end",label:"处理时间",width:"180"}}),e("el-table-column",{attrs:{prop:"houseName",label:"房号",width:"180"}}),e("el-table-column",{attrs:{prop:"message",label:"申请备注"}}),e("el-table-column",{attrs:{prop:"state",label:"状态",width:"100"},scopedSlots:t._u([{key:"default",fn:function(n){return[0==n.row.state?e("span",[t._v("待处理")]):2==n.row.state?e("span",[t._v("已驳回")]):3==n.row.state?e("span",[t._v("已取消")]):e("span",[t._v("已处理")])]}}])}),e("el-table-column",{attrs:{label:"操作",width:"200"},scopedSlots:t._u([{key:"default",fn:function(n){return[e("el-button",{attrs:{type:"primary",size:"small",disabled:t.isGet},on:{click:function(e){return t.openDrawer("show",n.row)}}},[t._v("查 看")]),e("el-button",{attrs:{type:"danger",size:"small",disabled:t.isDel||"0"!=n.row.state},on:{click:function(e){return t.cancel(n.row)}}},[t._v("撤 销")])]}}])})],1)],1),e("div",{staticClass:"pagination"},[e("el-pagination",{attrs:{"current-page":t.pagination.currentPage,"page-size":t.pagination.pageSize,background:"",layout:"prev, pager, next",total:t.pagination.total},on:{"current-change":t.handleCurrentChange}})],1),e("div",{staticClass:"drawer"},[e("el-drawer",{attrs:{title:t.drawerTitle,visible:t.drawer,"before-close":t.handleClose},on:{"update:visible":function(e){t.drawer=e}}},[e("div",{staticClass:"bg"},["show"==t.drawerType?e("div",{staticClass:"drawer-show"},[e("el-descriptions",{attrs:{title:" 退房信息",column:2}},[e("template",{slot:"extra"},[e("el-button",{attrs:{type:"primary",size:"small",disabled:t.isPull||"0"!=t.checkout.state},on:{click:t.deleteCheckOut}},[t._v("退 房")])],1),e("el-descriptions-item",{attrs:{label:"房号"}},[t._v(t._s(t.checkout.houseName))]),e("el-descriptions-item",{attrs:{label:"处理人"}},[t._v(t._s(t.checkout.name))]),e("el-descriptions-item",{attrs:{label:"申请时间",span:2}},[t._v(t._s(t.checkout.start))]),e("el-descriptions-item",{attrs:{label:"处理时间",span:2}},[t._v(t._s(t.checkout.end))]),e("el-descriptions-item",{attrs:{label:"申请备注",span:2}},[t._v(t._s(t.checkout.message))]),e("el-descriptions-item",{attrs:{label:"审核备注",span:2}},[t._v(t._s(t.checkout.remark))])],2)],1):t._e()])])],1)])])},r=[],s=n(6585),i=n(9312);function o(t,e){return(0,i.U2)(`getAllCheckOut/${t}`,e)}function c(t){return(0,i.U2)("cancelCheckOut",t)}function u(t){return(0,i.U2)("deleteCheckOut",t)}function l(t){return(0,i.U2)("getCheckOutDetail",t)}function h(t){return(0,i.U2)("judgeDepositByHouseName",t)}function d(t){return(0,i.v_)("handleCheckOut",t)}var p=n(5115),f=(n(9176),n(2900)),m={data(){return{pickerBeginOption:{disabledDate:t=>t.getTime()>Date.now()},form:{month:"",houseName:""},tableData:[],pagination:{currentPage:1,pageSize:7,total:0},drawer:!1,drawerType:"",checkout:{}}},created(){this.getAll()},methods:{async getAll(){const t=`${this.pagination.currentPage}/${this.pagination.pageSize}`,{data:e}=await o(t,{houseName:this.form.houseName,time:this.form.month});this.tableData=e.records,this.pagination.currentPage=e.current,this.pagination.total=e.total,this.pagination.pageSize=e.size},handleCurrentChange(t){this.pagination.currentPage=t,this.getAll()},search(){this.getAll(),this.form={}},handleClose(t){this.$confirm("确认关闭？").then((e=>{t()})).catch((t=>{}))},async openDrawer(t,e){this.drawerType=t,"show"==t&&await this.getCheckOutDetail({checkoutId:e.checkoutId}),this.drawer=!0},cancel(t){this.$confirm("此操作将撤销该申请, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((async()=>{try{const e={...t,userId:this.$store.getters.userId};await c(e),this.$message.success("撤销成功...")}catch(e){console.log(e)}finally{this.getAll()}})).catch((()=>{}))},deleteCheckOut(){this.$confirm("此操作将进行退房处理, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((async()=>{try{await u({...this.checkout,userId:this.$store.getters.userId}),this.$message.success("退房成功")}catch(t){console.log(t)}finally{this.getCheckOutDetail({checkoutId:this.checkout.checkoutId}),this.getAll()}})).catch((()=>{}))},async getCheckOutDetail(t){try{const{data:e}=await l(t);this.checkout=e}catch(e){console.log(e)}},addCheckOut(){this.$prompt("请输入房号","退房申请",{confirmButtonText:"确定",cancelButtonText:"取消",inputPattern:/^[0-9]*$/,inputErrorMessage:"房号格式不正确"}).then((async({value:t})=>{try{const{data:e}=await(0,p.y1)({houseName:t});if(e>0){const{data:e}=await h({houseName:t});let n=e>0?"此操作将进行退房处理，是否继续？":"该房间尚未租完合同时间，确认需要退房？";this.$confirm(n,"提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((()=>{this.handleCheckOut(t)})).catch((()=>{this.$message({type:"info",message:"已取消退房..."})}))}else this.$message.info("该房间尚未出租...")}catch(e){console.log(e)}})).catch((()=>{}))},handleCheckOut(t){this.$prompt("请输入密码","上传电费",{confirmButtonText:"确定",cancelButtonText:"取消",inputType:"password",inputPattern:/^\w{5,12}$/,inputErrorMessage:"密码格式不正确"}).then((async({value:e})=>{try{await(0,f.uo)({password:e,username:this.$store.getters.username}),await d({houseName:t,userId:this.$store.getters.userId}),this.$message.success("退房成功")}catch(n){console.log(n)}finally{this.getAll()}})).catch((()=>{this.$message({type:"info",message:"已取消退房..."})}))}},computed:{isAdd(){return s.kL(this.$route.params.type)},isDel(){return s.bK(this.$route.params.type)},isUp(){return s.EN(this.$route.params.type)},isPull(){return s.kX(this.$route.params.type)},isEdit(){return s.mi(this.$route.params.type)},isGet(){return s.Xu(this.$route.params.type)},drawerTitle(){let t="默认大标题";return"show"===this.drawerType&&(t="查 看 退 房 信 息"),"add"===this.drawerType&&(t="退 房 处 理"),t}}},g=m,y=n(1001),b=(0,y.Z)(g,a,r,!1,null,"71d0ec8b",null),k=b.exports},5115:function(t,e,n){n.d(e,{AD:function(){return i},L$:function(){return s},Pe:function(){return r},YZ:function(){return o},gh:function(){return l},qb:function(){return c},y1:function(){return u}});var a=n(9312);function r(t){return(0,a.U2)(`/getHouseNumber/${t}`,{})}function s(t){return(0,a.v_)("/insertHouse",t)}function i(t){return(0,a.U2)(`/getHouseDetail/${t}`)}function o(t){return(0,a.U2)("/getHouse",t)}function c(t){return(0,a.U2)("deleteHouse",t)}function u(t){return(0,a.U2)("judgeHouseState",t)}function l(){return(0,a.U2)("getHouseNum",{})}},6585:function(t,e,n){function a(t){const e="1";return u(t,e)}function r(t){const e="2";return u(t,e)}function s(t){const e="3";return u(t,e)}function i(t){const e="4";return u(t,e)}function o(t){const e="5";return u(t,e)}function c(t){const e="6";return u(t,e)}function u(t,e){let n=t.split("").filter((t=>!isNaN(t)));return!n.includes(e)}n.d(e,{EN:function(){return s},Xu:function(){return c},bK:function(){return r},kL:function(){return a},kX:function(){return i},mi:function(){return o}})},9176:function(t,e,n){function a(t){return/^1[3-9]\d{9}$/.test(t)}function r(t){let e=!0;return Object.keys(t).forEach((function(n){null!==t[n]&&""!=t[n]&&(e=!1)})),e}function s(t){return"NaN"!=parseFloat(t).toString()}function i(t){return""===t||null===t}n.d(e,{BP:function(){return s},Lp:function(){return a},kz:function(){return r},n3:function(){return i}})}}]);
//# sourceMappingURL=712.d9ec923a.js.map