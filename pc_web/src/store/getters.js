export default {
  loginComponent: state => state.setting.loginComponent,

  Authorization: state => state.user.Authorization,
  user: state => state.user.user,
  username: state => state.user.user.username,
  userId:state => state.user.user.userId,
  avatar: state => state.user.user.avatar,
  roleId: state=>state.user.user.roleId,

  menu: state => state.permission.menu
};
