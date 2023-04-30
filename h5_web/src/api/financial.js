import { post, get } from "@/utils/request";

/**
 * 获取用户账单信息
 * @param {*} param
 * @returns
 */
export function getOrder(param) {
  return get("getUserOrder", param);
}

/**
 * 获取用户账单细节
 * @param {*} param
 * @returns
 */
export function getUserOrderDetail(param) {
  return get(`getUserOrderDetail`, param);
}

/**
 * 移动支付
 * @param {*} param
 * @returns
 */
export function pay(param) {
  return post(`pay`, param);
}
