import { post, get, postFile } from "@/utils/request";

/**
 * 获取用户信息
 * @param {Object} data
 * @returns
 */
export function loginAPI(data) {
  return post("/user/login", data);
}

/**
 * 退出登录
 * @returns
 */
export function logoutAPI() {
  return get("/user/logout", {});
}
/**
 * 更新头像
 * @param {FormData} data
 * @returns
 */
export function uploadAvatarAPI(data) {
  return postFile("/uploadAvatar", data);
}
/**
 * 获取用户信息
 * @param {Object} data
 * @returns
 */
export function getUserAPI(data) {
  return get("/getUser", data);
}

/**
 * 修改用户密码、手机
 * @param {*} param
 * @returns
 */
export function updateUser(param) {
  return post(`updateUser`, param);
}
