"use strict";(self["webpackChunkh5_web"]=self["webpackChunkh5_web"]||[]).push([[835],{48:function(t,e,a){a.d(e,{Z:function(){return r}});var s=function(){var t=this,e=t._self._c;return e("div",{staticClass:"container"},[e("van-nav-bar",{attrs:{title:t.title,"left-text":"返回","left-arrow":""},on:{"click-left":function(e){return t.goBack()}},scopedSlots:t._u([{key:"right",fn:function(){return[t._t("default")]},proxy:!0}],null,!0)})],1)},n=[],c={props:{title:{type:String,default:"标题"}},methods:{goBack(){this.$router.go(-1)}}},o=c,i=a(1001),l=(0,i.Z)(o,s,n,!1,null,"b51246ea",null),r=l.exports},9835:function(t,e,a){a.r(e),a.d(e,{default:function(){return g}});var s=function(){var t=this,e=t._self._c;return e("div",{staticClass:"container"},[e("div",{staticClass:"header"},[e("Header",{attrs:{title:"退房申请"}})],1),""===t.state?e("div",{staticClass:"main"},[e("div",{staticClass:"bg"},[e("van-cell",{attrs:{title:"房号",value:t.house.houseName}}),e("van-cell",{attrs:{title:"录入时间",value:t.house.loginTime}})],1),e("div",{staticClass:"bg"},[e("van-field",{attrs:{rows:"2",autosize:"",label:"留言",type:"textarea",maxlength:"50",placeholder:"请输入留言","show-word-limit":""},model:{value:t.message,callback:function(e){t.message=e},expression:"message"}})],1),e("div",{staticClass:"footer"},[e("van-button",{staticStyle:{width:"80%"},attrs:{round:"",type:"info"},on:{click:t.checkout}},[t._v("申 请 退 房")])],1)]):t._e(),0===t.state?e("div",{staticClass:"main"},[e("van-cell",{attrs:{"is-link":""},on:{click:function(e){t.showCancel=!0}},scopedSlots:t._u([{key:"title",fn:function(){return[e("p",[t._v("已提交申请")])]},proxy:!0},{key:"label",fn:function(){return[e("div",[t._v("申请时间："+t._s(t.checkoutData.start))]),e("div",[t._v("留言："+t._s(t.checkoutData.message))])]},proxy:!0}],null,!1,2934690650)}),e("van-action-sheet",{attrs:{actions:t.actions},on:{select:t.onSelect},model:{value:t.showCancel,callback:function(e){t.showCancel=e},expression:"showCancel"}})],1):t._e(),1===t.state?e("div",{staticClass:"main"},[e("van-cell",{scopedSlots:t._u([{key:"title",fn:function(){return[e("p",{staticStyle:{color:"#00d0b5"}},[t._v("申请已通过")])]},proxy:!0},{key:"label",fn:function(){return[e("div",[t._v("申请时间："+t._s(t.checkoutData.start))]),e("div",[t._v("留言："+t._s(t.checkoutData.message))])]},proxy:!0}],null,!1,322607693)})],1):t._e(),2===t.state?e("div",{staticClass:"main"},[e("van-cell-group",[e("van-cell",{attrs:{"is-link":""},on:{click:function(e){t.showCancel=!0}},scopedSlots:t._u([{key:"title",fn:function(){return[e("p",{staticStyle:{color:"#ff2855"}},[t._v("申请不通过")])]},proxy:!0},{key:"label",fn:function(){return[e("div",[t._v("申请时间："+t._s(t.checkoutData.start))]),e("div",[t._v("留言："+t._s(t.checkoutData.message))])]},proxy:!0}],null,!1,161705435)}),e("van-cell",{scopedSlots:t._u([{key:"title",fn:function(){return[e("p",[t._v("详情")])]},proxy:!0},{key:"label",fn:function(){return[e("div",[t._v("具体问题: "+t._s(t.checkoutData.remark))])]},proxy:!0}],null,!1,2649973533)})],1),e("van-action-sheet",{attrs:{actions:t.actions},on:{select:t.onSelect},model:{value:t.showCancel,callback:function(e){t.showCancel=e},expression:"showCancel"}})],1):t._e()])},n=[],c=a(6361);function o(t){return(0,c.U2)("insertCheckOut",t)}function i(t){return(0,c.U2)("getCheckOutState",t)}function l(t){return(0,c.U2)("cancelCheckOut",t)}var r=a(48),u=a(9176),h=a(5115),d={components:{Header:r.Z},data(){return{message:"",state:"",showCancel:!1,actions:[{name:"重新提交"}],checkoutData:{},house:{}}},async created(){this.reset()},methods:{async reset(){this.$toast.loading({duration:0,message:"加载中...",forbidClick:!0}),await this.getHouseDetail(),await this.getCheckOutState(),this.$toast.clear()},async getHouseDetail(){try{const{data:t}=await(0,h.r)({userId:this.$store.getters.userId});this.house=t}catch(t){console.log(t)}},checkout(){this.$dialog.confirm({title:"申请报修",message:"你确定要申请吗？"}).then((async()=>{try{this.$toast.loading();const{data:t}=await o({userId:this.$store.getters.userId,message:this.message});this.checkoutData=t,this.state++,this.$toast.success("申请成功,待审核...")}catch(t){console.log(t)}}))},async getCheckOutState(){try{const{data:t}=await i({userId:this.$store.getters.userId});(0,u.k)(t)||(this.checkoutData=t,this.state=this.checkoutData.state)}catch(t){console.log(t)}},async onSelect(t){if("重新提交"==t.name){try{this.$toast.loading({duration:0,message:"加载中...",forbidClick:!0}),this.checkoutData.state=3,await l(this.checkoutData),this.$toast.clear()}catch(e){console.log(e)}finally{this.reset()}this.showCancel=!1}}}},f=d,v=a(1001),k=(0,v.Z)(f,s,n,!1,null,"27746ff1",null),g=k.exports},5115:function(t,e,a){a.d(e,{r:function(){return n}});var s=a(6361);function n(t){return(0,s.U2)("getHouseDetailByUserId",t)}},9176:function(t,e,a){function s(t){let e=!0;return Object.keys(t).forEach((function(a){null!==t[a]&&""!=t[a]&&(e=!1)})),e}a.d(e,{k:function(){return s}})}}]);
//# sourceMappingURL=835.2aed4c72.js.map