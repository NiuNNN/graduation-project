"use strict";(self["webpackChunkh5_web"]=self["webpackChunkh5_web"]||[]).push([[617],{48:function(t,e,n){n.d(e,{Z:function(){return l}});var r=function(){var t=this,e=t._self._c;return e("div",{staticClass:"container"},[e("van-nav-bar",{attrs:{title:t.title,"left-text":"返回","left-arrow":""},on:{"click-left":function(e){return t.goBack()}},scopedSlots:t._u([{key:"right",fn:function(){return[t._t("default")]},proxy:!0}],null,!0)})],1)},s=[],a={props:{title:{type:String,default:"标题"}},methods:{goBack(){this.$router.go(-1)}}},i=a,u=n(1001),o=(0,u.Z)(i,r,s,!1,null,"b51246ea",null),l=o.exports},3617:function(t,e,n){n.r(e),n.d(e,{default:function(){return f}});var r=function(){var t=this,e=t._self._c;return e("div",{staticClass:"container"},[e("div",{staticClass:"header"},[e("Header",{attrs:{title:"申请报修"}})],1),e("div",{staticClass:"main"},[e("van-form",{on:{submit:t.onSubmit}},[e("van-cell-group",[e("van-field",{attrs:{required:"",rules:[{required:!0}],rows:"2",autosize:"",label:"报修描述",type:"textarea",maxlength:"50",placeholder:"请描述报修信息","show-word-limit":""},model:{value:t.message,callback:function(e){t.message=e},expression:"message"}})],1),e("div",{staticStyle:{margin:"16px"}},[e("van-button",{attrs:{round:"",block:"",type:"info","native-type":"submit"}},[t._v("确 认 申 请")])],1)],1)],1)])},s=[],a=n(1903),i=n(5210),u=n(48),o={components:{Header:u.Z},data(){return{message:""}},methods:{onSubmit(){this.$dialog.confirm({title:"申请报修",message:"你确定要申请吗？"}).then((async()=>{try{const{data:t}=await(0,i.l)({userId:this.$store.getters.userId});await(0,a.u6)({rentId:t.rentId,message:this.message}),this.$toast.success("申请成功"),this.message=""}catch(t){console.log(t)}}))}}},l=o,c=n(1001),d=(0,c.Z)(l,r,s,!1,null,"47b6ac73",null),f=d.exports},5210:function(t,e,n){n.d(e,{l:function(){return s}});var r=n(6361);function s(t){return(0,r.U2)("getRentIdByUserId",t)}},1903:function(t,e,n){n.d(e,{YH:function(){return a},dB:function(){return i},u6:function(){return s}});var r=n(6361);function s(t){return(0,r.v_)("insertRepair",t)}function a(t){return(0,r.U2)("getRepairByUserId",t)}function i(t){return(0,r.U2)("deleteRepair",t)}}}]);
//# sourceMappingURL=617.53eeffff.js.map