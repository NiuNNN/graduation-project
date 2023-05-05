export default {
  Authorization: state => state.user.Authorization,
  user: state => state.user.user,
  username: state => state.user.user.username,
  userId: state => state.user.user.userId,
  avatar: state => state.user.user.avatar,
  isCurrent: state => state.setting.isCurrent
};
