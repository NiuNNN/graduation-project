import { post, get } from '@/utils/request';

/**
 * 获取全部退房信息
 * @param {*} param
 * @param {*} obj
 * @returns
 */
export function getAllCheckOut(param, obj) {
  return get(`getAllCheckOut/${param}`, obj);
}

/**
 * 取消退房
 * @param {*} param
 * @returns
 */
export function cancelCheckOut(param) {
  return get(`cancelCheckOut`, param);
}

/**
 * 退房处理
 * @param {*} param
 * @returns
 */
export function deleteCheckOut(param) {
  return get(`deleteCheckOut`, param);
}

/**
 * 获取退房详细信息
 * @param {*} param
 * @returns
 */
export function getCheckOutDetail(param) {
  return get(`getCheckOutDetail`, param);
}
