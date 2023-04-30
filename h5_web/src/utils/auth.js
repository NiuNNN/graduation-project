import store from "@/store";
import { targetUrl } from "@/targetUrl.js";

const TokenKey = "PRHMS-token-user";

const TimeKey = "PRHMS-time-key-user"; // 用来作为时间戳存储的key

const UserKey = "PRHMS-username-key-user";

/**
 * 获取token
 * @returns
 */
export function getToken() {
  return localStorage.getItem(TokenKey);
}
/**
 * 设置token
 * @param {String} token
 * @returns
 */
export function setToken(token) {
  return localStorage.setItem(TokenKey, token);
}
/**
 * 移除本地token
 * @returns
 */
export function removeToken() {
  return localStorage.removeItem(TokenKey);
}
/**
 * 当前最新的时间戳
 * @returns
 */
export function setTimeStamp() {
  return localStorage.setItem(TimeKey, Date.now());
}
/**
 *
 * @returns 获取时间戳
 */
export function getTimeStamp() {
  return localStorage.getItem(TimeKey);
}
/**
 * 退出
 */
export function logout() {
  store.commit("setting/removeIsCurrent");
  store.commit("user/removeToken");
  store.commit("user/removeUser");
}
/**
 * 拼接头像url
 * @param {*} avatar
 */
export function computedAvatar(avatar) {
  return `${targetUrl}/view/Avatar/${avatar}`;
}
/**
 * 设置用户信息
 * @param {Object} user
 * @returns
 */
export function setUser(user) {
  return localStorage.setItem(UserKey, JSON.stringify(user));
}

/**
 *
 * @returns 获取用户信息
 */
export function getUser() {
  return JSON.parse(localStorage.getItem(UserKey));
}
/**
 * 移除本地用户信息
 * @returns
 */
export function removeUser() {
  return localStorage.removeItem(UserKey);
}
