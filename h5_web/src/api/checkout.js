import { post, get } from "@/utils/request";

/**
 * 申请退房
 * @param {*} param
 * @returns
 */
export function insertCheckOut(param) {
  return get(`insertCheckOut`, param);
}

/**
 * 获取申请状态
 * @param {*} param
 * @returns
 */
export function getCheckOutState(param) {
  return get(`getCheckOutState`, param);
}

/**
 * 取消申请退房
 * @param {*} param
 * @returns
 */
export function cancelCheckOut(param) {
  return get(`cancelCheckOut`, param);
}
