"use strict";(self["webpackChunkpc_web"]=self["webpackChunkpc_web"]||[]).push([[383],{9383:function(t,e,n){n.r(e),n.d(e,{default:function(){return h}});var a=function(){var t=this,e=t._self._c;return e("div",{staticClass:"main-container"},[e("div",{staticClass:"header-container"},[e("h1",[t._v("用 水 管 理")]),e("div",{staticClass:"btn"},[e("el-button",{staticClass:"cancel",attrs:{disabled:t.isGet},on:{click:t.download}},[t._v("下 载 模 板")]),e("el-button",{staticClass:"save",attrs:{disabled:t.isAdd},on:{click:t.upload}},[t._v("上传")]),e("input",{ref:"iptRef",staticStyle:{display:"none"},attrs:{type:"file",accept:".xls,.xlsx"},on:{change:t.onFileChange}})],1)]),e("div",{staticClass:"table-container"},[e("div",{staticClass:"navbar"},[e("el-form",{ref:"form",attrs:{model:t.form,"label-width":"50px",inline:!0}},[e("el-form-item",{attrs:{label:"房号"}},[e("el-input",{attrs:{placeholder:"请输入房号"},model:{value:t.form.houseName,callback:function(e){t.$set(t.form,"houseName",e)},expression:"form.houseName"}})],1),e("el-form-item",{attrs:{label:"时间"}},[e("el-date-picker",{attrs:{type:"month",placeholder:"请选择时间","picker-options":t.pickerBeginOption,"value-format":"yyyy-MM"},model:{value:t.form.month,callback:function(e){t.$set(t.form,"month",e)},expression:"form.month"}})],1),e("el-button",{staticStyle:{height:"38px"},attrs:{type:"primary"},on:{click:t.search}},[t._v("查询")])],1)],1),e("div",{staticClass:"table"},[e("el-table",{staticStyle:{width:"100%"},attrs:{data:t.tableData,stripe:"",height:"454","default-sort":{prop:"date",order:"descending"}},scopedSlots:t._u([{key:"empty",fn:function(){return[e("el-empty",{attrs:{"image-size":200}})]},proxy:!0}])},[e("el-table-column",{attrs:{prop:"date",label:"时间",sortable:"",width:"180"}}),e("el-table-column",{attrs:{prop:"houseName",label:"房号",width:"180"}}),e("el-table-column",{attrs:{prop:"numWater",label:"用水量(方)",width:"180"}}),e("el-table-column",{attrs:{prop:"costWater",label:"水费(元)",width:"180"}}),e("el-table-column",{attrs:{prop:"uploadWater",label:"上传时间"}}),e("el-table-column",{attrs:{label:"操作",width:"100"},scopedSlots:t._u([{key:"default",fn:function(n){return[e("el-button",{attrs:{type:"primary",size:"small",disabled:t.isEdit||"0"!=n.row.state},on:{click:function(e){return t.edit(n.row)}}},[t._v("编 辑")])]}}])})],1)],1),e("div",{staticClass:"pagination"},[e("el-pagination",{attrs:{"current-page":t.pagination.currentPage,"page-size":t.pagination.pageSize,background:"",layout:"prev, pager, next",total:t.pagination.total},on:{"current-change":t.handleCurrentChange}})],1),e("div",{staticClass:"drawer"},[e("el-drawer",{attrs:{title:"水费表解析",visible:t.drawer,wrapperClosable:!1,"show-close":!1},on:{"update:visible":function(e){t.drawer=e}}},[e("div",{staticClass:"drawer-container"},[e("div",{staticClass:"table"},[e("h3",[t._v("数据：")]),e("el-table",{staticStyle:{width:"100%"},attrs:{data:t.hasData,border:"",size:"mini",height:"320"}},[e("el-table-column",{attrs:{prop:"houseName",label:"房号"}}),e("el-table-column",{attrs:{prop:"time",label:"月份"}}),e("el-table-column",{attrs:{prop:"num",label:"用水量(方)"},scopedSlots:t._u([{key:"default",fn:function(n){return[e("el-input",{attrs:{oninput:"value=value.replace(/[^0-9.]/g,'')"},model:{value:n.row.num,callback:function(e){t.$set(n.row,"num","string"===typeof e?e.trim():e)},expression:"scope.row.num"}})]}}])}),e("el-table-column",{attrs:{label:"操作",width:"80"},scopedSlots:t._u([{key:"default",fn:function(n){return[e("el-button",{staticStyle:{color:"#ff2855"},attrs:{type:"text",size:"mini",disabled:t.isDel},on:{click:function(e){return t.deleteRow("has",n)}}},[t._v("删除")])]}}])})],1)],1),e("div",{staticClass:"table"},[e("h3",[t._v("空数据：")]),e("el-table",{staticStyle:{width:"100%"},attrs:{data:t.noneData,border:"",size:"mini",height:"160"}},[e("el-table-column",{attrs:{prop:"houseName",label:"房号"}}),e("el-table-column",{attrs:{prop:"time",label:"月份"}}),e("el-table-column",{attrs:{prop:"num",label:"用水量(方)"},scopedSlots:t._u([{key:"default",fn:function(n){return[e("el-input",{attrs:{oninput:"value=value.replace(/[^0-9.]/g,'')"},on:{blur:function(e){return t.changeData(n)}},model:{value:n.row.num,callback:function(e){t.$set(n.row,"num","string"===typeof e?e.trim():e)},expression:"scope.row.num"}})]}}])}),e("el-table-column",{attrs:{label:"操作",width:"100"},scopedSlots:t._u([{key:"default",fn:function(n){return[e("el-button",{staticStyle:{color:"#ff2855"},attrs:{type:"text",size:"mini"},on:{click:function(e){return t.deleteRow("none",n)}}},[t._v("删除")])]}}])})],1)],1),e("div",{staticClass:"btn"},[e("el-button",{attrs:{type:"primary"},on:{click:t.add}},[t._v("确 定")]),e("el-button",{on:{click:function(e){t.drawer=!1}}},[t._v("取 消")])],1)])])],1),e("div",{staticClass:"drawer"},[e("el-drawer",{attrs:{title:"水费表解析",visible:t.toggleDrawer,wrapperClosable:!1,"show-close":!1},on:{"update:visible":function(e){t.toggleDrawer=e}}},[e("div",{staticClass:"drawer-container"},[e("div",{staticClass:"table"},[e("h3",[t._v("新数据：")]),e("el-table",{staticStyle:{width:"100%"},attrs:{data:t.newData,border:"",size:"mini",height:"240"},on:{"selection-change":t.handleSelectionChange}},[t._v(" > "),e("el-table-column",{attrs:{type:"selection",width:"55"}}),e("el-table-column",{attrs:{prop:"houseName",label:"房号"}}),e("el-table-column",{attrs:{prop:"time",label:"月份"}}),e("el-table-column",{attrs:{prop:"num",label:"用水量(方)"}})],1)],1),e("div",{staticClass:"table"},[e("h3",[t._v("源数据：")]),e("el-table",{staticStyle:{width:"100%"},attrs:{data:t.oldData,border:"",size:"mini",height:"240"}},[e("el-table-column",{attrs:{prop:"houseName",label:"房号"}}),e("el-table-column",{attrs:{prop:"time",label:"月份"}}),e("el-table-column",{attrs:{prop:"num",label:"用水量(方)"}})],1)],1),e("div",{staticClass:"btn"},[e("el-button",{attrs:{type:"primary",disabled:t.isTooggle},on:{click:t.toggle}},[t._v("替 换")]),e("el-button",{on:{click:function(e){t.toggleDrawer=!1}}},[t._v("取 消")])],1)])])],1)])])},r=[],i=(n(7658),n(6585)),s=n(4980),o=n(2900),l={data(){return{pickerBeginOption:{disabledDate:t=>t.getTime()>Date.now()},form:{month:"",houseName:""},tableData:[],pagination:{currentPage:1,pageSize:7,total:0},password:"",drawer:!1,hasData:[],noneData:[],newData:[],oldData:[],multipleSelection:[],toggleDrawer:!1}},created(){this.getAll()},methods:{async getAll(){try{const t=`${this.pagination.currentPage}/${this.pagination.pageSize}`,{data:e}=await(0,s.Fo)(t,{houseName:this.form.houseName,time:this.form.month});this.tableData=e.records,this.pagination.currentPage=e.current,this.pagination.total=e.total,this.pagination.pageSize=e.size}catch(t){console.log(t)}},async download(){try{await(0,s._L)()}catch(t){console.log(t)}finally{this.getAll()}},upload(){this.$prompt("请输入密码","上传水费",{confirmButtonText:"确定",cancelButtonText:"取消",inputType:"password",inputPattern:/^\w{5,12}$/,inputErrorMessage:"密码格式不正确"}).then((async({value:t})=>{try{await(0,o.uo)({password:t,username:this.$store.getters.username}),this.$refs.iptRef.click()}catch(e){console.log(e)}})).catch((()=>{this.$message({type:"info",message:"取消上传"})}))},handleCurrentChange(t){this.pagination.currentPage=t,this.getAll()},async onFileChange(t){const e=t.target.files;if(0!==e.length&&this.beforeAvatarUpload(e[0]))try{console.log(e[0]);const t=new FormData;t.append("file",e[0]);const{data:n}=await(0,s.n$)(t);this.hasData=n.has,this.noneData=n.none,console.log(n),this.drawer=!0}catch(n){console.log(n)}},beforeAvatarUpload(t){let e=t.name.substring(t.name.lastIndexOf(".")+1);const n=["xls","xlsx"];return-1!==n.indexOf(e)||(this.$message.error("上传文件只能是xls、xlsx格式"),!1)},deleteRow(t,e){"has"==t&&this.hasData.splice(e.$index,1),"none"==t&&(console.log(e),this.noneData.splice(e.$index,1))},changeData(t){""!=t.row.num&&null!=t.row.num&&(this.hasData.push(this.noneData[t.$index]),this.noneData.splice(t.$index,1))},add(){this.$confirm("此操作将添加本月的用水数据, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((async()=>{try{const{data:t}=await(0,s.JQ)({listStr:JSON.stringify(this.hasData)});this.newData=t.new,this.oldData=t.old,this.newData.length>0&&this.oldData.length>0?(this.$message.info("存在冲突的数据"),this.toggleDrawer=!0,this.drawer=!1):(this.drawer=!1,this.$message.success("添加成功"))}catch(t){console.log(t)}finally{this.getAll()}})).catch((()=>{this.$message({type:"info",message:"已取消添加"})}))},toggle(){this.$confirm("此操作将替换源数据, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((async()=>{try{await(0,s.Wm)({listStr:JSON.stringify(this.multipleSelection)}),this.toggleDrawer=!1,this.$message.success("修改成功")}catch(t){console.log(t)}finally{this.getAll()}})).catch((()=>{this.$message({type:"info",message:"已取消替换"})}))},handleSelectionChange(t){this.multipleSelection=t},search(){this.getAll(),this.form={month:"",houseName:""}},edit(t){this.$prompt("请输入密码","修改水费",{confirmButtonText:"确定",cancelButtonText:"取消",inputType:"password",inputPattern:/^\w{5,12}$/,inputErrorMessage:"密码格式不正确"}).then((async({value:e})=>{try{await(0,o.uo)({password:e,username:this.$store.getters.username}),this.$prompt("请输入修改的用水量","提示",{confirmButtonText:"确定",cancelButtonText:"取消",inputPattern:/^(-?\d+)(\.\d+)?$/,inputErrorMessage:"参数格式不正确"}).then((async({value:e})=>{try{await(0,s.gD)({costId:t.costId,numWater:e}),this.$message.success("修改成功")}catch(n){console.log(n)}finally{this.getAll()}})).catch((()=>{this.$message({type:"info",message:"取消编辑"})}))}catch(n){console.log(n)}})).catch((()=>{this.$message({type:"info",message:"取消编辑"})}))}},computed:{isAdd(){return i.kL(this.$route.params.type)},isDel(){return i.bK(this.$route.params.type)},isUp(){return i.EN(this.$route.params.type)},isPull(){return i.kX(this.$route.params.type)},isEdit(){return i.mi(this.$route.params.type)},isGet(){return i.Xu(this.$route.params.type)},isTooggle(){return!(this.multipleSelection.length>0)}}},c=l,u=n(1001),p=(0,u.Z)(c,a,r,!1,null,"68015571",null),h=p.exports},4980:function(t,e,n){n.d(e,{$T:function(){return b},Fo:function(){return h},GC:function(){return y},JQ:function(){return c},KH:function(){return f},Wm:function(){return p},_L:function(){return i},_x:function(){return s},cs:function(){return u},gD:function(){return m},i8:function(){return d},iJ:function(){return l},mw:function(){return r},n$:function(){return o},oD:function(){return v},rQ:function(){return g},rb:function(){return w}});var a=n(9312);function r(){return(0,a.Ab)("sheetElectricExcel",{})}function i(){return(0,a.Ab)("sheetWaterExcel",{})}function s(t){return(0,a.cd)("getElectricExcel",t)}function o(t){return(0,a.cd)("getWaterExcel",t)}function l(t){return(0,a.v_)("judgeElectric",t)}function c(t){return(0,a.v_)("judgeWater",t)}function u(t){return(0,a.v_)("toggleElectric",t)}function p(t){return(0,a.v_)("toggleWater",t)}function h(t,e){return(0,a.U2)(`getAllCost/${t}`,e)}function d(t){return(0,a.U2)("updateElectricByCostId",t)}function m(t){return(0,a.U2)("updateWaterByCostId",t)}function f(t){return(0,a.U2)("deleteCost",t)}function g(t){return(0,a.Ab)("generatePersonCost",t)}function b(t){return(0,a.Ab)("generateAllCost",t)}function w(t){return(0,a.U2)("judgeCost",t)}function y(t){return(0,a.U2)("getCostByRentId",t)}function v(t){return(0,a.Ab)("getAllCostExcel",t)}},6585:function(t,e,n){function a(t){const e="1";return c(t,e)}function r(t){const e="2";return c(t,e)}function i(t){const e="3";return c(t,e)}function s(t){const e="4";return c(t,e)}function o(t){const e="5";return c(t,e)}function l(t){const e="6";return c(t,e)}function c(t,e){let n=t.split("").filter((t=>!isNaN(t)));return!n.includes(e)}n.d(e,{EN:function(){return i},Xu:function(){return l},bK:function(){return r},kL:function(){return a},kX:function(){return s},mi:function(){return o}})}}]);
//# sourceMappingURL=383.2cab6a4d.js.map