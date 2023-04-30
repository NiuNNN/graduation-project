import { post, get, postFile } from "@/utils/request";

/**
 * 获取用户杂费信息
 * @param {*} param
 * @returns
 */
export function getBasicByUserId(param) {
  return get(`getBasicByUserId`, param);
}

/**
 * 获取没有添加的杂费信息
 * @param {*} param
 * @returns
 */
export function getNoBasic(param) {
  return get(`getNoBasicByRentId`, param);
}

/**
 * 添加杂费
 * @param {*} param
 * @returns
 */
export function insertMiscellaneous(param) {
  return get(`insertMiscellaneous`, param);
}

/**
 * 改变杂费状态
 * @param {*} param
 * @returns
 */
export function changeMiscellaneousState(param) {
  return get(`changeMiscellaneousState`, param);
}
