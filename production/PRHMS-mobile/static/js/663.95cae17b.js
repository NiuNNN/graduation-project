"use strict";(self["webpackChunkh5_web"]=self["webpackChunkh5_web"]||[]).push([[663],{4663:function(e,s,t){t.r(s),t.d(s,{default:function(){return c}});var a=function(){var e=this,s=e._self._c;return s("div",{staticClass:"container"},[s("div",{staticClass:"header"},[s("HeaderVue")],1),s("div",{staticClass:"main"},[s("p",[e._v("WELCOME TO THE SYSTEM!")]),s("div",{staticClass:"form"},[s("van-form",{on:{submit:e.onSubmit}},[s("van-field",{attrs:{name:"username",label:"用户名",placeholder:"用户名",autocomplete:"off",rules:[{required:!0,message:"请填写用户名"},{pattern:/^\d{5,12}$/,message:"用户名有误"}]},model:{value:e.username,callback:function(s){e.username="string"===typeof s?s.trim():s},expression:"username"}}),s("van-field",{attrs:{type:"password",name:"password",label:"密码",placeholder:"密码",autocomplete:"off",rules:[{required:!0,message:"请填写密码"},{pattern:/^\w{5,12}$/,message:"密码有误"}]},model:{value:e.password,callback:function(s){e.password="string"===typeof s?s.trim():s},expression:"password"}}),s("div",{staticStyle:{margin:"16px"}},[s("van-button",{attrs:{round:"",block:"",type:"info","native-type":"submit"}},[e._v("登录")])],1)],1)],1)]),s("p",{staticClass:"footer"},[e._v("Design by NiuNiu")])])},r=[],n=(t(7658),t(1751)),o={components:{HeaderVue:n.Z},data(){return{username:"",password:""}},methods:{async onSubmit(e){try{console.log("submit",e),this.loading=!0,await this.$store.dispatch("user/login",e),this.$router.push("/")}catch(s){console.log(s)}}}},i=o,l=t(1001),u=(0,l.Z)(i,a,r,!1,null,"3fc05eb2",null),c=u.exports}}]);
//# sourceMappingURL=663.95cae17b.js.map