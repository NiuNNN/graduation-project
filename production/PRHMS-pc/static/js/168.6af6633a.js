"use strict";(self["webpackChunkpc_web"]=self["webpackChunkpc_web"]||[]).push([[168],{9168:function(e,r,t){t.r(r),t.d(r,{default:function(){return O}});var s=function(){var e=this,r=e._self._c;return r("Index",[[r(e.loginComponent,{tag:"component"})]],2)},a=[],o=t(3822),n=function(){var e=this,r=e._self._c;return r("div",{staticClass:"login-container"},[r("div",{staticClass:"banner"}),r("div",{staticClass:"form-container"},[e._m(0),e._t("default")],2)])},l=[function(){var e=this,r=e._self._c;return r("div",{staticClass:"phone clearfix"},[r("span",[e._v("Contact number")]),r("span",[e._v("+86 13924813103")])])}],i={},u=i,c=t(1001),p=(0,c.Z)(u,n,l,!1,null,"6ef07c64",null),m=p.exports,g=function(){var e=this,r=e._self._c;return r("div",{staticClass:"welcome"},[r("h1",[e._v("WELCOME TO THE SYSTEM!")]),r("p",{staticClass:"detail"},[e._v("Sign in and start managing your house.")]),r("el-form",{ref:"LoginForm",staticClass:"LoginForm",attrs:{model:e.user,rules:e.rules,"label-width":"100px","label-position":"top"}},[r("el-form-item",{attrs:{label:"Account",prop:"username"}},[r("el-input",{attrs:{placeholder:"Please enter your account"},model:{value:e.user.username,callback:function(r){e.$set(e.user,"username",r)},expression:"user.username"}})],1),r("el-form-item",{attrs:{label:"Password",prop:"password"},nativeOn:{keyup:function(r){return!r.type.indexOf("key")&&e._k(r.keyCode,"enter",13,r.key,"Enter")?null:e.handleLogin.apply(null,arguments)}}},[r("el-input",{attrs:{"show-password":"",placeholder:"Please enter your password"},model:{value:e.user.password,callback:function(r){e.$set(e.user,"password",r)},expression:"user.password"}})],1)],1),r("span",{staticClass:"back",on:{click:e.change}},[e._v("Forgot Password?")]),r("el-button",{attrs:{type:"primary",round:""},nativeOn:{click:function(r){return r.preventDefault(),e.handleLogin.apply(null,arguments)}}},[e._v("Sign in")])],1)},d=[],h=(t(7658),{data(){return{user:{username:"",password:""},rules:{username:[{required:!0,message:"Please enter your account",trigger:"blur"},{min:8,max:16,message:"Please enter the correct account",trigger:"blur"}],password:[{required:!0,message:"Please enter your password",trigger:"blur"},{pattern:/^\w{5,12}$/,message:"Please enter the correct password",trigger:"blur"}]}}},methods:{handleLogin(){this.$refs.LoginForm.validate((async e=>{if(e)try{await this.$store.dispatch("user/login",this.user);this.$router.push("/")}catch(r){console.log(r)}else this.$message.error("Please enter the correct information!")}))},change(){this.$store.dispatch("setting/changeLogin","Forget")}}}),f=h,w=(0,c.Z)(f,g,d,!1,null,"7c0a1ead",null),F=w.exports,b=function(){var e=this,r=e._self._c;return r("div",{staticClass:"forget"},[r("h1",[e._v("FORGOT PASSWORD?")]),r("p",{staticClass:"detail"},[e._v("Don't worry! Enter your registration information to remake the pasword.")]),r("el-form",{ref:"ForgetForm",staticClass:"ForgetForm",attrs:{model:e.ForgetForm,rules:e.rules,"label-width":"100px","label-position":"top"}},[r("el-row",[r("el-col",{attrs:{span:24}},[r("el-form-item",{attrs:{label:"Account",prop:"username"}},[r("el-input",{attrs:{placeholder:"Please enter your account"},model:{value:e.ForgetForm.username,callback:function(r){e.$set(e.ForgetForm,"username",r)},expression:"ForgetForm.username"}})],1)],1)],1),r("el-row",[r("el-col",{attrs:{span:24}},[r("el-form-item",{staticClass:"gap",attrs:{label:"Name",prop:"name"}},[r("el-input",{attrs:{placeholder:"Name"},model:{value:e.ForgetForm.name,callback:function(r){e.$set(e.ForgetForm,"name",r)},expression:"ForgetForm.name"}})],1)],1)],1),r("el-row",[r("el-col",{attrs:{span:24}},[r("el-form-item",{attrs:{label:"Phone",prop:"phone"}},[r("el-input",{attrs:{placeholder:"Please enter your Phone"},model:{value:e.ForgetForm.phone,callback:function(r){e.$set(e.ForgetForm,"phone",r)},expression:"ForgetForm.phone"}})],1)],1)],1),r("el-row",[r("el-col",{attrs:{span:12}},[r("el-form-item",{staticClass:"gap",attrs:{label:"New Password",prop:"pass"}},[r("el-input",{attrs:{placeholder:"New Password",type:"password","show-password":""},model:{value:e.ForgetForm.pass,callback:function(r){e.$set(e.ForgetForm,"pass",r)},expression:"ForgetForm.pass"}})],1)],1),r("el-col",{attrs:{span:12}},[r("el-form-item",{staticClass:"gap",staticStyle:{float:"right"},attrs:{label:"Confirm Password",prop:"password"}},[r("el-input",{attrs:{placeholder:"Confirm Password",type:"password","show-password":""},model:{value:e.ForgetForm.password,callback:function(r){e.$set(e.ForgetForm,"password",r)},expression:"ForgetForm.password"}})],1)],1)],1)],1),r("p",{staticClass:"back",on:{click:e.change}},[e._v("Back to Register?")]),r("el-button",{attrs:{type:"primary",round:""},on:{click:e.handleConfirm}},[e._v("Confirm")])],1)},v=[],y=t(2900),C=t(9176),k={data(){const e=(e,r,t)=>{(0,C.Lp)(r)?t():t(new Error("Please enter the correct phone number"))},r=(e,r,t)=>{""!==this.password&&this.$refs.ForgetForm.validateField("password"),t()},t=(e,r,t)=>{r!=this.ForgetForm.pass?t(new Error("两次输入密码不一致!")):t()};return{ForgetForm:{username:"",name:"",pass:"",password:"",phone:""},rules:{phone:[{required:!0,trigger:"blur",message:"Please enter your phone number"},{trigger:"blur",validator:e}],pass:[{required:!0,trigger:"blur",message:"Please enter your new password"},{validator:r,trigger:"blur"}],password:[{required:!0,trigger:"blur",message:"Please confimr your new password"},{validator:t,trigger:"blur"}],username:[{required:!0,message:"Please enter your account",trigger:"blur"},{min:8,max:16,message:"Please enter the correct account",trigger:"blur"}],name:[{required:!0,message:"Please enter your name",trigger:"blur"}]}}},methods:{change(){this.$store.dispatch("setting/changeLogin","Login")},handleConfirm(){this.$refs.ForgetForm.validate((async e=>{if(e)try{await(0,y.o9)(this.ForgetForm),this.$message.success("密码修改成功..."),this.change()}catch(r){console.log(r)}}))}}},P=k,_=(0,c.Z)(P,b,v,!1,null,"3f3588a1",null),x=_.exports,$={components:{Index:m,Login:F,Forget:x},data(){return{}},computed:{...(0,o.Se)(["loginComponent"])}},L=$,E=(0,c.Z)(L,s,a,!1,null,"d6d57f26",null),O=E.exports},9176:function(e,r,t){function s(e){return/^1[3-9]\d{9}$/.test(e)}function a(e){let r=!0;return Object.keys(e).forEach((function(t){null!==e[t]&&""!=e[t]&&(r=!1)})),r}function o(e){return"NaN"!=parseFloat(e).toString()}function n(e){return""===e||null===e}t.d(r,{BP:function(){return o},Lp:function(){return s},kz:function(){return a},n3:function(){return n}})}}]);
//# sourceMappingURL=168.6af6633a.js.map