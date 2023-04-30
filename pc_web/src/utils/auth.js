import store from '@/store';
import { targetUrl } from '@/targetUrl.js';

const TokenKey = 'PRHMS-token';

const timeKey = 'PRHMS-time-key'; // 用来作为时间戳存储的key

const userKey = 'PRHMS-username-key';

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
  return localStorage.setItem(timeKey, Date.now());
}
/**
 *
 * @returns 获取时间戳
 */
export function getTimeStamp() {
  return localStorage.getItem(timeKey);
}
/**
 * 设置用户信息
 * @param {Object} user
 * @returns
 */
export function setUser(user) {
  return localStorage.setItem(userKey, JSON.stringify(user));
}

/**
 *
 * @returns 获取用户信息
 */
export function getUser() {
  return JSON.parse(localStorage.getItem(userKey));
}
/**
 * 移除本地用户信息
 * @returns
 */
export function removeUser() {
  return localStorage.removeItem(userKey);
}
/**
 * 拼接头像url
 * @param {*} avatar
 */
export function computedAvatar(avatar) {
  return `${targetUrl}/view/Avatar/${avatar}`;
}

export function logout() {
  store.commit('user/removeToken');
  store.commit('user/removeUser');
  store.commit('permission/removeMenu');
}
