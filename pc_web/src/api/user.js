import { post, get, postFile } from '@/utils/request';

/**
 * 获取用户信息
 * @param {Object} data
 * @returns
 */
export function loginAPI(data) {
  return post('/staff/login', data);
}

/**
 * 退出登录
 * @returns 
 */
export function logoutAPI() {
  return get('/staff/logout');
}

/**
 * 获取用户信息
 * @param {Object} data
 * @returns
 */
export function getUserAPI(data) {
  return get('/getUser', data);
}

/**
 * 更新头像
 * @param {FormData} data
 * @returns
 */
export function uploadAvatarAPI(data) {
  return postFile('/uploadAvatar', data);
}

/**
 * 获取菜单信息
 * @param {Object} data
 * @returns
 */
export function getMenuAPI(data) {
  return get('/getMenu', data);
}

/**
 * 创建新的用户账号
 * @param {*} param 
 * @returns 
 */
export function createUserName(param){
  return get(`createUserName`,param)
}

/**
 * 校验身份证
 * @param {*} param 
 * @returns 
 */
export function validateIDCard(param){
  return postFile(`/validateIDCard`,param)
}

/**
 * 确认信息
 * @param {*} param 
 * @returns 
 */
export function confirmMsg(param){
  return postFile(`/confirmMsg`,param)
}

/**
 * 创建新用户
 * @param {*} param 
 * @returns 
 */
export function insertUser(param){
  return postFile(`/insertUser`,param)
}

/**
 * 按需获取住户信息
 * @param {*} param 
 * @param {*} obj 
 * @returns 
 */
export function getUserPage(param,obj){
  return get(`getUserPage/${param}`,obj)
}

/**
 * 按需获取没有房间的用户
 * @param {*} param 
 * @param {*} obj 
 * @returns 
 */
export function getNoHouseUserPage(param,obj){
  return get(`getNoHouseUserPage/${param}`,obj)
}

/**
 * 修改租客信息
 * @param {*} param 
 * @returns 
 */
export function updateUserByIDCard(param){
  return postFile(`updateUserByIDCard`,param)
}

/**
 * 修改用户密码或手机
 * @param {*} param 
 * @returns 
 */
export function updateUser(param){
  return post(`updateUser`,param);
}

/**
 * 修改用户状态
 * @param {*} param 
 * @returns 
 */
export function changeUserState(param){
  return get(`changeUserState`,param)
}

/**
 * 校验密码
 * @param {*} param 
 * @returns 
 */
export function validatePassword(param){
  return post(`validatePassword`,param)
}

/**
 * 获取名称
 * @param {*} param 
 * @returns 
 */
export function getName(param){
  return get(`getName/${param}`,{})
}