"use strict";(self["webpackChunkh5_web"]=self["webpackChunkh5_web"]||[]).push([[167],{5167:function(t,a,e){e.r(a),e.d(a,{default:function(){return n}});var r=function(){var t=this,a=t._self._c;return a("div",{staticClass:"container"},[a("div",{staticClass:"avatar"},[a("input",{ref:"iptRef",staticStyle:{display:"none"},attrs:{type:"file",accept:"image/jpeg"},on:{change:t.onFileChange}}),a("img",{directives:[{name:"imageerror",rawName:"v-imageerror",value:t.defaultImg,expression:"defaultImg"}],attrs:{src:t.avatar,alt:""},on:{click:t.uploadAvatar}}),a("div",{staticClass:"camera"},[a("van-icon",{attrs:{name:"edit"},on:{click:t.uploadAvatar}})],1)]),a("div",{staticClass:"mian"},[a("van-cell",{attrs:{title:"个 人 资 料","is-link":"",icon:"contact",to:`userprofile/${this.$store.getters.username}`}}),a("van-cell",{attrs:{title:"住 房 信 息","is-link":"",icon:"hotel-o",to:`userhouse/${this.$store.getters.username}`}}),a("van-cell",{attrs:{title:"密 码 设 置","is-link":"",icon:"shield-o",to:"password"}})],1),a("div",{staticClass:"footer"},[a("van-button",{staticStyle:{width:"80%"},attrs:{round:"",type:"info"},on:{click:t.logout}},[t._v("退 出 登 录")])],1)])},s=[],i=e(2900),c={data(){return{defaultImg:e(7239),avatar:""}},created(){this.avatar=this.$store.getters.avatar},methods:{uploadAvatar(){this.$refs.iptRef.click()},onFileChange(t){this.$toast.loading();const a=t.target.files;if(0!==a.length&&this.beforeAvatarUpload(a[0])){const t=new FileReader;t.readAsDataURL(a[0]),t.onload=async t=>{this.avatar=t.target.result;const e=new FormData;e.append("file",a[0]),e.append("username",this.$store.getters.username),await(0,i.uQ)(e),this.$store.dispatch("user/getUser"),this.$toast.success("头像修改成功")}}},beforeAvatarUpload(t){const a="image/jpeg"===t.type,e=t.size/1024/1024<2;return a||this.$message.error("上传头像图片只能是 JPG 格式!"),e||this.$message.error("上传头像图片大小不能超过 2MB!"),a&&e},logout(){this.$store.dispatch("user/logout")}}},o=c,l=e(1001),A=(0,l.Z)(o,r,s,!1,null,"5ab479ae",null),n=A.exports},7239:function(t){t.exports="data:image/png;base64,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"}}]);
//# sourceMappingURL=167.4c0125cb.js.map