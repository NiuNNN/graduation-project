(function(){"use strict";var e={1751:function(e,t,n){n.d(t,{Z:function(){return c}});var r=function(){var e=this;e._self._c;return e._m(0)},o=[function(){var e=this,t=e._self._c;return t("div",{staticClass:"container"},[t("img",{attrs:{src:n(6949),alt:""}}),t("span",[e._v("PRHMS")])])}],u={},a=u,i=n(1001),s=(0,i.Z)(a,r,o,!1,null,"2ff06d56",null),c=s.exports},2900:function(e,t,n){n.d(t,{Le:function(){return a},Nq:function(){return i},UO:function(){return o},uQ:function(){return u}});var r=n(6361);function o(e){return(0,r.v_)("/user/login",e)}function u(e){return(0,r.cd)("/uploadAvatar",e)}function a(e){return(0,r.U2)("/getUser",e)}function i(e){return(0,r.v_)("updateUser",e)}},8491:function(e,t,n){var r={};n.r(r),n.d(r,{imageerror:function(){return p}});var o=function(){var e=this,t=e._self._c;return t("div",{attrs:{id:"app"}},[t("router-view")],1)},u=[],a={},i=a,s=n(1001),c=(0,s.Z)(i,o,u,!1,null,"66c993da",null),f=c.exports,l=n(2182),d=n(1633);const p={inserted(e,t){e.src=e.src||t.value,e.onerror=function(){e.src=t.value}},componentUpdated(e,t){e.src=e.src||t.value}};var h=n(530),m=n.n(h);const v=["/login","/404"];l.ZP.beforeEach(((e,t,n)=>{m().start(),d.Z.getters.Authorization&&d.Z.getters.username?"/login"===e.path?n("/"):n():v.indexOf(e.path)>-1?n():n("/login"),m().done()})),l.ZP.afterEach((()=>{m().done()}));n(4809);var g=n(9858),b=(n(4391),n(4398)),Z=(n(3970),n(140)),y=(n(289),n(7061)),P=(n(4920),n(8848)),k=(n(2685),n(7165)),w=(n(2210),n(4159)),C=(n(6037),n(6940)),S=(n(9578),n(6631)),_=(n(2406),n(9864)),O=(n(6758),n(4639)),A=(n(7582),n(1878)),E=(n(3255),n(797)),j=(n(7234),n(3432)),x=(n(7983),n(3838)),I=(n(2186),n(8889)),T=(n(8786),n(4820)),U=(n(2278),n(4202)),N=(n(8618),n(5705)),R=(n(4847),n(888)),L=(n(4914),n(3462)),z=(n(4592),n(4958)),M=n(311),$=n.n(M);$().use(z.Z),$().use(L.Z),$().use(R.Z),$().use(N.Z),$().use(U.Z),$().use(T.Z),$().use(I.Z),$().use(x.Z),$().use(j.Z),$().use(E.Z),$().use(A.Z),$().use(O.Z),$().use(_.Z),$().use(S.Z),$().use(C.Z),$().use(w.Z),$().use(k.Z),$().use(P.Z),$().use(y.Z),$().use(Z.Z),$().use(b.Z),$().use(g.Z),Vue.prototype.$echarts=echarts,Vue.config.productionTip=!1,window.addEventListener("unhandledrejection",(function(e){e&&e.preventDefault()})),console.log=()=>{},Object.keys(r).forEach((e=>{Vue.directive(e,r[e])})),new Vue({router:l.ZP,store:d.Z,render:e=>e(f)}).$mount("#app")},2182:function(e,t,n){n.d(t,{ZP:function(){return C}});var r=n(311),o=n.n(r),u=n(2631),a=function(){var e=this,t=e._self._c;return t("div",{staticClass:"container"},[t("div",{staticClass:"header"},[t("header-vue")],1),t("div",{staticClass:"main"},[t("keep-alive",[t("router-view")],1)],1),t("div",{staticClass:"footer"},[t("nav-bar")],1)])},i=[],s=n(1751),c=function(){var e=this,t=e._self._c;return t("div",{staticClass:"container"},[t("router-link",{attrs:{to:"/"}},[t("div",{class:["link",{current:"home"==e.isCurrent}],on:{click:function(t){return e.change("home")}}},[t("van-icon",{attrs:{name:"apps-o"}}),t("p",[e._v("工 作 台")])],1)]),t("router-link",{attrs:{to:"/user"}},[t("div",{class:["link",{current:"user"==e.isCurrent}],on:{click:function(t){return e.change("user")}}},[t("van-icon",{attrs:{name:"user-o"}}),t("p",[e._v("我 的")])],1)])],1)},f=[],l=(n(7658),{created(){this.goRoute()},methods:{change(e){this.$store.dispatch("setting/changeCurrent",e)},goRoute(){"user"==this.isCurrent?this.$router.push("/user"):this.$router.push("/")}},computed:{isCurrent(){return this.$store.getters.isCurrent||"home"}}}),d=l,p=n(1001),h=(0,p.Z)(d,c,f,!1,null,"24c654e0",null),m=h.exports,v={components:{HeaderVue:s.Z,NavBar:m}},g=v,b=(0,p.Z)(g,a,i,!1,null,"58abcdae",null),Z=b.exports;o().use(u.ZP);const y=[{path:"/",redirect:"/layout"},{path:"/login",component:()=>n.e(663).then(n.bind(n,4663))},{path:"/layout",component:Z,children:[{path:"/",component:()=>n.e(423).then(n.bind(n,6423))},{path:"/user",component:()=>n.e(167).then(n.bind(n,5167))}]},{path:"/userprofile/:id",component:()=>n.e(509).then(n.bind(n,3509))},{path:"/userhouse/:id",component:()=>n.e(448).then(n.bind(n,8448))},{path:"/report",component:()=>n.e(207).then(n.bind(n,2207))},{path:"/detail/:id",component:()=>n.e(920).then(n.bind(n,1920))},{path:"/checkout",component:()=>n.e(835).then(n.bind(n,9835))},{path:"/password",component:()=>n.e(916).then(n.bind(n,8916))},{path:"/financial",component:()=>n.e(74).then(n.bind(n,5074))},{path:"/order/:id",component:()=>n.e(231).then(n.bind(n,7231))},{path:"/miscellanous",component:()=>n.e(379).then(n.bind(n,3379))},{path:"/contract",component:()=>n.e(562).then(n.bind(n,7562))},{path:"/repair",component:()=>n.e(778).then(n.bind(n,8778))},{path:"/addRepair",component:()=>n.e(617).then(n.bind(n,3617))},{path:"/repairDetail",component:()=>n.e(863).then(n.bind(n,4863))},{path:"/feedback",component:()=>n.e(572).then(n.bind(n,7572))},{path:"/404",component:()=>n.e(59).then(n.bind(n,7059))},{path:"*",redirect:"/404"}],P=u.ZP.prototype.push;u.ZP.prototype.push=function(e){return P.call(this,e).catch((e=>e))};const k=()=>new u.ZP({mode:"history",base:"/PRHMS-mobile",scrollBehavior:()=>({y:0}),routes:[...y]}),w=k();var C=w},1633:function(e,t,n){n.d(t,{Z:function(){return v}});var r=n(311),o=n.n(r),u=n(3822),a=(n(7658),n(2900)),i=n(1836),s=n(2182),c={namespaced:!0,state:{Authorization:(0,i.LP)()||"",user:(0,i.PR)()||{}},mutations:{setToken(e,t){e.Authorization=t,(0,i.o4)(t)},removeToken(e){e.Authorization=null,(0,i.gy)()},setUser(e,t){t.avatar=(0,i.Pz)(t.avatar),e.user=t,(0,i.av)(t)},removeUser(e){e.user={},(0,i.kX)()}},actions:{async login(e,t){try{const{data:n}=await(0,a.UO)(t);console.log(n),e.commit("setUser",n.menu.user),(0,i.Dy)(),e.commit("setToken",n.token)}catch(n){console.log(n)}},async getUser(e){try{const{data:t}=await(0,a.Le)({username:v.getters.username});return e.commit("setUser",t),t}catch(t){console.log(t)}},async logout(e){try{(0,i.kS)(),s.ZP.push("/login")}catch(t){console.log(t)}}}};const f="PRHMS-iscurrent-key-user";function l(){return localStorage.getItem(f)}function d(e){return localStorage.setItem(f,e)}function p(){return localStorage.removeItem(f)}var h={namespaced:!0,state:{isCurrent:l()},mutations:{setIsCurrent(e,t){e.isCurrent=t,d(t)},removeIsCurrent(e){e.isCurrent="",p()}},actions:{changeCurrent(e,t){e.commit("setIsCurrent",t)}}},m={Authorization:e=>e.user.Authorization,user:e=>e.user.user,username:e=>e.user.user.username,userId:e=>e.user.user.userId,avatar:e=>e.user.user.avatar,isCurrent:e=>e.setting.isCurrent};o().use(u.ZP);var v=new u.ZP.Store({modules:{user:c,setting:h},getters:m})},7278:function(e,t,n){n.d(t,{Z:function(){return r}});const r="http://niuniu6.natapp1.cc"},1836:function(e,t,n){n.d(t,{Dy:function(){return l},LP:function(){return s},PR:function(){return v},Pz:function(){return h},av:function(){return m},dg:function(){return d},gy:function(){return f},kS:function(){return p},kX:function(){return g},o4:function(){return c}});var r=n(1633),o=n(7278);const u="PRHMS-token-user",a="PRHMS-time-key-user",i="PRHMS-username-key-user";function s(){return localStorage.getItem(u)}function c(e){return localStorage.setItem(u,e)}function f(){return localStorage.removeItem(u)}function l(){return localStorage.setItem(a,Date.now())}function d(){return localStorage.getItem(a)}function p(){r.Z.commit("setting/removeIsCurrent"),r.Z.commit("user/removeToken"),r.Z.commit("user/removeUser")}function h(e){return`${o.Z}/view/Avatar/${e}`}function m(e){return localStorage.setItem(i,JSON.stringify(e))}function v(){return JSON.parse(localStorage.getItem(i))}function g(){return localStorage.removeItem(i)}},6361:function(e,t,n){n.d(t,{U2:function(){return p},v_:function(){return h},cd:function(){return m}});n(4920);var r=n(8848),o=(n(7658),axios),u=n.n(o),a=n(1633),i=n(2182),s=n(1836),c=n(6154),f=n.n(c);const l=86400;function d(){return(Date.now()-(0,s.dg)())/1e3>l}function p(e,t){return new Promise(((n,r)=>{u().get(e,{params:t}).then((e=>{n(e.data)})).catch((e=>{r(e)}))}))}function h(e,t){return new Promise(((n,r)=>{u().post(e,t).then((e=>{n(e.data)})).catch((e=>{r(e)}))}))}function m(e,t){const n={header:{"Content-Type":"multipart/form-data"}};return new Promise(((r,o)=>{u().post(e,t,n).then((e=>{r(e.data)})).catch((e=>{o(e)}))}))}u().defaults.baseURL="/api",u().defaults.timeout=6e4,u().defaults.headers.post["Content-Type"]="application/x-www-form-urlencoded;charset=UTF-8",u().interceptors.request.use((async e=>(r.Z.loading({duration:0,forbidClick:!0,message:"加载中..."}),a.Z.getters.Authorization&&d()?(await a.Z.dispatch("user/logout"),r.Z.fail("Not logged in yet or login has expired.Please log in again"),i.ZP.push("/login"),Promise.reject(new Error("token Timeout"))):(e.headers.token=a.Z.getters.Authorization,e))),(async e=>(r.Z.clear(),i.ZP.push("/login"),Promise.error(e)))),u().interceptors.response.use((e=>{if(e.data instanceof Blob){let t=new RegExp("filename=([^;]+\\.[^\\.;]+);*"),n=decodeURI(e.headers["content-disposition"]),o=t.exec(n);return f().saveAs(e.data,o[1]),r.Z.fail("正在为你下载..."),Promise.resolve(e)}if(200===e.data.code)return r.Z.clear(),Promise.resolve(e);switch(console.log(e),e.data.code){case 401:r.Z.fail("请重新登陆");break;case 402:return r.Z.fail("无权访问"),Promise.reject(new Error(e));case 404:r.Z.fail("验证失败");break;case 500:return r.Z.fail(`${e.data.message}`),Promise.reject(new Error(e));default:r.Z.fail("服务器正在维修")}return(0,s.kS)(),i.ZP.push("/login"),Promise.reject(new Error(e))}),(async e=>(r.Z.clear(),i.ZP.push("/404"),Promise.reject(e))))},6949:function(e,t,n){e.exports=n.p+"static/img/logo.b757767e.png"},311:function(e){e.exports=Vue}},t={};function n(r){var o=t[r];if(void 0!==o)return o.exports;var u=t[r]={exports:{}};return e[r].call(u.exports,u,u.exports,n),u.exports}n.m=e,function(){var e=[];n.O=function(t,r,o,u){if(!r){var a=1/0;for(f=0;f<e.length;f++){r=e[f][0],o=e[f][1],u=e[f][2];for(var i=!0,s=0;s<r.length;s++)(!1&u||a>=u)&&Object.keys(n.O).every((function(e){return n.O[e](r[s])}))?r.splice(s--,1):(i=!1,u<a&&(a=u));if(i){e.splice(f--,1);var c=o();void 0!==c&&(t=c)}}return t}u=u||0;for(var f=e.length;f>0&&e[f-1][2]>u;f--)e[f]=e[f-1];e[f]=[r,o,u]}}(),function(){n.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return n.d(t,{a:t}),t}}(),function(){n.d=function(e,t){for(var r in t)n.o(t,r)&&!n.o(e,r)&&Object.defineProperty(e,r,{enumerable:!0,get:t[r]})}}(),function(){n.f={},n.e=function(e){return Promise.all(Object.keys(n.f).reduce((function(t,r){return n.f[r](e,t),t}),[]))}}(),function(){n.u=function(e){return"static/js/"+e+"."+{59:"beb5ed87",74:"16ece40a",167:"4c0125cb",207:"76a7401a",231:"d3f82490",379:"e982351a",423:"644bb054",448:"b8d86777",509:"c69cc667",562:"f081d467",572:"e7006a89",617:"53eeffff",663:"95cae17b",778:"9ecd1b39",835:"2aed4c72",863:"044d0d87",916:"93d5e6bf",920:"3c4ae911"}[e]+".js"}}(),function(){n.miniCssF=function(e){return"static/css/"+e+"."+{59:"b07a35f5",74:"9e39d550",167:"eaa25aee",207:"854fbd7e",231:"3901bd41",379:"3baf4356",423:"8a53db2d",448:"534c10af",509:"da75a0d4",562:"d4ba8d19",572:"695f99b7",617:"ac37f306",663:"64535ee9",778:"610cda56",835:"58c08340",863:"0ff7d29a",916:"9dbe6e81",920:"b87692f4"}[e]+".css"}}(),function(){n.g=function(){if("object"===typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"===typeof window)return window}}()}(),function(){n.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)}}(),function(){var e={},t="h5_web:";n.l=function(r,o,u,a){if(e[r])e[r].push(o);else{var i,s;if(void 0!==u)for(var c=document.getElementsByTagName("script"),f=0;f<c.length;f++){var l=c[f];if(l.getAttribute("src")==r||l.getAttribute("data-webpack")==t+u){i=l;break}}i||(s=!0,i=document.createElement("script"),i.charset="utf-8",i.timeout=120,n.nc&&i.setAttribute("nonce",n.nc),i.setAttribute("data-webpack",t+u),i.src=r),e[r]=[o];var d=function(t,n){i.onerror=i.onload=null,clearTimeout(p);var o=e[r];if(delete e[r],i.parentNode&&i.parentNode.removeChild(i),o&&o.forEach((function(e){return e(n)})),t)return t(n)},p=setTimeout(d.bind(null,void 0,{type:"timeout",target:i}),12e4);i.onerror=d.bind(null,i.onerror),i.onload=d.bind(null,i.onload),s&&document.head.appendChild(i)}}}(),function(){n.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})}}(),function(){n.p="/"}(),function(){if("undefined"!==typeof document){var e=function(e,t,n,r,o){var u=document.createElement("link");u.rel="stylesheet",u.type="text/css";var a=function(n){if(u.onerror=u.onload=null,"load"===n.type)r();else{var a=n&&("load"===n.type?"missing":n.type),i=n&&n.target&&n.target.href||t,s=new Error("Loading CSS chunk "+e+" failed.\n("+i+")");s.code="CSS_CHUNK_LOAD_FAILED",s.type=a,s.request=i,u.parentNode&&u.parentNode.removeChild(u),o(s)}};return u.onerror=u.onload=a,u.href=t,n?n.parentNode.insertBefore(u,n.nextSibling):document.head.appendChild(u),u},t=function(e,t){for(var n=document.getElementsByTagName("link"),r=0;r<n.length;r++){var o=n[r],u=o.getAttribute("data-href")||o.getAttribute("href");if("stylesheet"===o.rel&&(u===e||u===t))return o}var a=document.getElementsByTagName("style");for(r=0;r<a.length;r++){o=a[r],u=o.getAttribute("data-href");if(u===e||u===t)return o}},r=function(r){return new Promise((function(o,u){var a=n.miniCssF(r),i=n.p+a;if(t(a,i))return o();e(r,i,null,o,u)}))},o={143:0};n.f.miniCss=function(e,t){var n={59:1,74:1,167:1,207:1,231:1,379:1,423:1,448:1,509:1,562:1,572:1,617:1,663:1,778:1,835:1,863:1,916:1,920:1};o[e]?t.push(o[e]):0!==o[e]&&n[e]&&t.push(o[e]=r(e).then((function(){o[e]=0}),(function(t){throw delete o[e],t})))}}}(),function(){var e={143:0};n.f.j=function(t,r){var o=n.o(e,t)?e[t]:void 0;if(0!==o)if(o)r.push(o[2]);else{var u=new Promise((function(n,r){o=e[t]=[n,r]}));r.push(o[2]=u);var a=n.p+n.u(t),i=new Error,s=function(r){if(n.o(e,t)&&(o=e[t],0!==o&&(e[t]=void 0),o)){var u=r&&("load"===r.type?"missing":r.type),a=r&&r.target&&r.target.src;i.message="Loading chunk "+t+" failed.\n("+u+": "+a+")",i.name="ChunkLoadError",i.type=u,i.request=a,o[1](i)}};n.l(a,s,"chunk-"+t,t)}},n.O.j=function(t){return 0===e[t]};var t=function(t,r){var o,u,a=r[0],i=r[1],s=r[2],c=0;if(a.some((function(t){return 0!==e[t]}))){for(o in i)n.o(i,o)&&(n.m[o]=i[o]);if(s)var f=s(n)}for(t&&t(r);c<a.length;c++)u=a[c],n.o(e,u)&&e[u]&&e[u][0](),e[u]=0;return n.O(f)},r=self["webpackChunkh5_web"]=self["webpackChunkh5_web"]||[];r.forEach(t.bind(null,0)),r.push=t.bind(null,r.push.bind(r))}();var r=n.O(void 0,[998],(function(){return n(8491)}));r=n.O(r)})();
//# sourceMappingURL=app.d9ab2d23.js.map