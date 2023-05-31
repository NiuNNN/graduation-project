(self["webpackChunkpc_web"]=self["webpackChunkpc_web"]||[]).push([[591],{2249:function(t,r,e){"use strict";e.d(r,{Z:function(){return f}});var n=function(){var t=this,r=t._self._c;return r("div",{staticClass:"container"},[r("h3",[t._v("账号:"+t._s(t.user.username))]),r("el-form",{ref:"userForm",staticClass:"demo-form-inline",attrs:{inline:!0,model:t.userForm,size:"small",rules:t.rules}},[r("el-form-item",{attrs:{label:"用户电话:",prop:"phone"}},[r("el-input",{attrs:{placeholder:"请输入新的电话号码"},model:{value:t.userForm.phone,callback:function(r){t.$set(t.userForm,"phone",r)},expression:"userForm.phone"}})],1),r("el-form-item",{attrs:{label:"密码:",prop:"password"}},[r("el-input",{attrs:{placeholder:"请输入新的密码"},model:{value:t.userForm.password,callback:function(r){t.$set(t.userForm,"password",r)},expression:"userForm.password"}})],1)],1),r("div",{staticClass:"idcard"},[r("span",{staticClass:"idcard-header"},[t._v("身份证照片")]),r("div",{staticClass:"idcard-flex"},[r("div",{class:[{error:t.isErrorFont},{idcard_container:!0}],on:{click:function(r){return t.uploadAvatar("font")}}},[r("input",{ref:"fontRef",staticStyle:{display:"none"},attrs:{type:"file",accept:"image/jpeg"},on:{change:function(r){return t.onFileChange(r,"font")}}}),t.fontUrl?r("img",{attrs:{src:t.fontUrl,alt:""}}):r("i",{staticClass:"el-icon-plus avatar-uploader-icon"},[t._v("带国徽面")])]),r("div",{class:[{error:t.isErrorBack},{idcard_container:!0}],on:{click:function(r){return t.uploadAvatar("back")}}},[r("input",{ref:"backRef",staticStyle:{display:"none"},attrs:{type:"file",accept:"image/jpeg"},on:{change:function(r){return t.onFileChange(r,"back")}}}),t.backUrl?r("img",{attrs:{src:t.backUrl,alt:""}}):r("i",{staticClass:"el-icon-plus avatar-uploader-icon"},[t._v("带头像面")])])])]),r("div",{staticClass:"btn"},[r("el-button",{attrs:{type:"primary"},on:{click:t.submit}},[t._v("确 定")]),r("el-button",{on:{click:t.close}},[t._v("取 消")])],1)],1)},s=[],i=e(2900);function o(t){return null==t||""==t}var a={props:{user:{type:Object,default:()=>({})}},data(){return{fontUrl:"",backUrl:"",fontFile:"",backFile:"",username:"",isErrorFont:!1,isErrorBack:!1,rules:{phone:[{required:!1,trigger:"blur",message:"请输入住户手机"},{pattern:/^1[3-9]\d{9}$/,message:"请输入正确的手机号",trigger:"blur"}],password:[{required:!1,message:"请输入密码",trigger:"blur"},{pattern:/^\w{5,12}$/,message:"请输入正确的秘密",trigger:"blur"}]},isfont:!1,isback:!1,userForm:{phone:"",password:""},newUser:{}}},methods:{uploadAvatar(t){"font"===t&&this.$refs.fontRef.click(),"back"===t&&this.$refs.backRef.click()},onFileChange(t,r){const e=t.target.files;if(0!==e.length&&this.beforeAvatarUpload(e[0])){const t=new FileReader;t.readAsDataURL(e[0]),t.onload=async t=>{try{"font"===r&&(this.isfont=!1,this.fontUrl=t.target.result,this.fontFile=e[0],this.isErrorFont=!0),"back"===r&&(this.isback=!1,this.backUrl=t.target.result,this.backFile=e[0],this.isErrorBack=!0);const n=new FormData;n.append("file",e[0]),n.append("direction",r);const{data:s}=await(0,i.RA)(n);"font"===s&&(this.isErrorFont=!1,this.isfont=!0),"back"===s&&(this.isErrorBack=!1,this.isback=!0),this.$message.success("校验通过")}catch(n){console.log(n)}}}},beforeAvatarUpload(t){const r="image/jpeg"===t.type,e=t.size/1024/1024<2;return r||this.$message.error("上传头像图片只能是 JPG 格式!"),e||this.$message.error("上传头像图片大小不能超过 2MB!"),r&&e},submit(){this.newUser=this.user,this.$refs.userForm.validate((async t=>{if(t){if(this.isfont&&this.isback)try{const t=new FormData;t.append("font",this.fontFile),t.append("back",this.backFile),t.append("username",this.user.username);const{data:r}=await(0,i.C1)(t);this.newUser={...this.newUser,...r},console.log(this.newUser),this.$message.success("修改成功")}catch(r){console.log(r)}if(!o(this.userForm.password)||!o(this.userForm.phone))try{await(0,i.Nq)({...this.userForm,username:this.user.username}),this.newUser={...this.newUser,...this.userForm},o(this.userForm.phone),console.log(this.newUser),this.$message.success("修改成功")}catch(r){console.log(r)}this.$emit("updateUser",this.newUser)}}))},close(){this.newUser=this.user,this.$emit("updateUser",this.newUser)}}},c=a,u=e(1001),l=(0,u.Z)(c,n,s,!1,null,"6ead624c",null),f=l.exports},6134:function(t,r,e){"use strict";e.d(r,{Z:function(){return u}});var n=function(){var t=this,r=t._self._c;return r("div",{staticClass:"autographView"},[t._m(0),r("div",{ref:"autographContainer",staticClass:"autograph_container"},[r("canvas",{ref:"canvasRef",on:{mousedown:t.mousedown,mousemove:t.mousemove,mouseup:t.mouseup}})]),r("div",{staticClass:"autograph_btn"},[r("div",{staticStyle:{"margin-right":"30px"}},[r("el-button",{on:{click:t.back}},[t._v("返回")]),r("el-button",{on:{click:t.handleClear}},[t._v("清除")]),r("el-button",{attrs:{type:"primary"},on:{click:t.handleCommit}},[t._v("确定")])],1)])])},s=[function(){var t=this,r=t._self._c;return r("div",{staticClass:"header"},[r("span",{staticStyle:{"margin-right":"20px"}},[t._v("请在一下区域签名")])])}],i=(e(7658),e(2801),e(3767),e(8585),e(8696),{data(){return{ctx:null,isPrint:!1,strokeStyle:"#000",lineWidth:2,startX:0,startY:0,points:[]}},mounted(){this.initCanvas()},methods:{initCanvas(){const{width:t,height:r}=this.$refs.autographContainer.getBoundingClientRect(),e=this.$refs.canvasRef;e.width=t,e.height=r,this.ctx=e.getContext("2d")},mousedown(t){t.preventDefault(),this.startX=t.offsetX,this.startY=t.offsetY,this.isPrint=!0},mousemove(t){if(t.preventDefault(),!this.isPrint)return;const r={x:t.offsetX,y:t.offsetY};this.ctx.strokeStyle=this.strokeStyle,this.ctx.lineWidth=this.lineWidth,this.ctx.beginPath(),this.ctx.moveTo(this.startX,this.startY),this.ctx.lineTo(r.x,r.y),this.ctx.stroke(),this.ctx.closePath(),this.startX=r.x,this.startY=r.y,this.points.push(r)},mouseup(){this.isPrint=!1},handleClear(){const{width:t,height:r}=this.$refs.canvasRef;this.ctx.clearRect(0,0,t,r),this.points=[]},handleCommit(){if(this.points.length<20)return void this.$message.error("签名不能为空!");const t=this.$refs.canvasRef.toDataURL(),r=Date.now(),e=this.dataURLToFile(t,r);this.$emit("commitSign",e)},dataURLToFile(t,r){const e=t.split(","),n=e[0].match(/:(.*?);/)[1],s=atob(e[1]);let i=s.length;const o=new Uint8Array(i);while(i--)o[i]=s.charCodeAt(i);return new File([o],r,{type:n})},back(){this.$emit("back")}}}),o=i,a=e(1001),c=(0,a.Z)(o,n,s,!1,null,"4696545c",null),u=c.exports},5787:function(t,r,e){var n=e(7976),s=TypeError;t.exports=function(t,r){if(n(r,t))return t;throw s("Incorrect invocation")}},3678:function(t){t.exports={IndexSizeError:{s:"INDEX_SIZE_ERR",c:1,m:1},DOMStringSizeError:{s:"DOMSTRING_SIZE_ERR",c:2,m:0},HierarchyRequestError:{s:"HIERARCHY_REQUEST_ERR",c:3,m:1},WrongDocumentError:{s:"WRONG_DOCUMENT_ERR",c:4,m:1},InvalidCharacterError:{s:"INVALID_CHARACTER_ERR",c:5,m:1},NoDataAllowedError:{s:"NO_DATA_ALLOWED_ERR",c:6,m:0},NoModificationAllowedError:{s:"NO_MODIFICATION_ALLOWED_ERR",c:7,m:1},NotFoundError:{s:"NOT_FOUND_ERR",c:8,m:1},NotSupportedError:{s:"NOT_SUPPORTED_ERR",c:9,m:1},InUseAttributeError:{s:"INUSE_ATTRIBUTE_ERR",c:10,m:1},InvalidStateError:{s:"INVALID_STATE_ERR",c:11,m:1},SyntaxError:{s:"SYNTAX_ERR",c:12,m:1},InvalidModificationError:{s:"INVALID_MODIFICATION_ERR",c:13,m:1},NamespaceError:{s:"NAMESPACE_ERR",c:14,m:1},InvalidAccessError:{s:"INVALID_ACCESS_ERR",c:15,m:1},ValidationError:{s:"VALIDATION_ERR",c:16,m:0},TypeMismatchError:{s:"TYPE_MISMATCH_ERR",c:17,m:1},SecurityError:{s:"SECURITY_ERR",c:18,m:1},NetworkError:{s:"NETWORK_ERR",c:19,m:1},AbortError:{s:"ABORT_ERR",c:20,m:1},URLMismatchError:{s:"URL_MISMATCH_ERR",c:21,m:1},QuotaExceededError:{s:"QUOTA_EXCEEDED_ERR",c:22,m:1},TimeoutError:{s:"TIMEOUT_ERR",c:23,m:1},InvalidNodeTypeError:{s:"INVALID_NODE_TYPE_ERR",c:24,m:1},DataCloneError:{s:"DATA_CLONE_ERR",c:25,m:1}}},1060:function(t,r,e){var n=e(1702),s=Error,i=n("".replace),o=function(t){return String(s(t).stack)}("zxcasd"),a=/\n\s*at [^:]*:[^\n]*/,c=a.test(o);t.exports=function(t,r){if(c&&"string"==typeof t&&!s.prepareStackTrace)while(r--)t=i(t,a,"");return t}},9587:function(t,r,e){var n=e(614),s=e(111),i=e(7674);t.exports=function(t,r,e){var o,a;return i&&n(o=r.constructor)&&o!==e&&s(a=o.prototype)&&a!==e.prototype&&i(t,a),t}},6277:function(t,r,e){var n=e(1340);t.exports=function(t,r){return void 0===t?arguments.length<2?"":r:n(t)}},1340:function(t,r,e){var n=e(648),s=String;t.exports=function(t){if("Symbol"===n(t))throw TypeError("Cannot convert a Symbol value to a string");return s(t)}},2801:function(t,r,e){"use strict";var n=e(2109),s=e(7854),i=e(5005),o=e(9114),a=e(3070).f,c=e(2597),u=e(5787),l=e(9587),f=e(6277),h=e(3678),p=e(1060),d=e(9781),m=e(1913),E="DOMException",R=i("Error"),g=i(E),v=function(){u(this,_);var t=arguments.length,r=f(t<1?void 0:arguments[0]),e=f(t<2?void 0:arguments[1],"Error"),n=new g(r,e),s=R(r);return s.name=E,a(n,"stack",o(1,p(s.stack,1))),l(n,this,v),n},_=v.prototype=g.prototype,k="stack"in R(E),b="stack"in new g(1,2),w=g&&d&&Object.getOwnPropertyDescriptor(s,E),C=!!w&&!(w.writable&&w.configurable),y=k&&!C&&!b;n({global:!0,constructor:!0,forced:m||y},{DOMException:y?v:g});var U=i(E),A=U.prototype;if(A.constructor!==U)for(var F in m||a(A,"constructor",o(1,U)),h)if(c(h,F)){var I=h[F],T=I.s;c(U,T)||a(U,T,o(6,I.c))}},3811:function(t,r,e){"use strict";e.d(r,{EF:function(){return c},Te:function(){return u},WD:function(){return l},ij:function(){return a},mm:function(){return o},pk:function(){return f},rt:function(){return i},wR:function(){return s}});var n=e(9312);function s(){return(0,n.U2)("getBaseContract",{})}function i(t,r){return(0,n.U2)(`selectContract/${t}`,r)}function o(t){return(0,n.v_)("writeRentContract",t)}function a(t){return(0,n.cd)("writeRentSign",t)}function c(t){return(0,n.U2)("changeContractStateByUserId",t)}function u(t){return(0,n.v_)("writeWorkContract",t)}function l(t){return(0,n.cd)("writeWorkSign",t)}function f(t){return(0,n.U2)("getContractByUserId",t)}},6585:function(t,r,e){"use strict";function n(t){const r="1";return u(t,r)}function s(t){const r="2";return u(t,r)}function i(t){const r="3";return u(t,r)}function o(t){const r="4";return u(t,r)}function a(t){const r="5";return u(t,r)}function c(t){const r="6";return u(t,r)}function u(t,r){let e=t.split("").filter((t=>!isNaN(t)));return!e.includes(r)}e.d(r,{EN:function(){return i},Xu:function(){return c},bK:function(){return s},kL:function(){return n},kX:function(){return o},mi:function(){return a}})},9176:function(t,r,e){"use strict";function n(t){return/^1[3-9]\d{9}$/.test(t)}function s(t){let r=!0;return Object.keys(t).forEach((function(e){null!==t[e]&&""!=t[e]&&(r=!1)})),r}function i(t){return"NaN"!=parseFloat(t).toString()}function o(t){return""===t||null===t}e.d(r,{BP:function(){return i},Lp:function(){return n},kz:function(){return s},n3:function(){return o}})}}]);
//# sourceMappingURL=591.8974b7f7.js.map