import { post, get } from "@/utils/request";

/**
 * 插入维修记录
 * @param {*} param
 * @returns
 */
export function insertRepair(param) {
  return post(`insertRepair`, param);
}

/**
 * 获取维修信息
 * @param {*} param
 * @returns
 */
export function getRepair(param) {
  return get(`getRepairByUserId`, param);
}

/**
 * 取消维修
 * @param {*} param
 * @returns
 */
export function deleteRepair(param) {
  return get(`deleteRepair`, param);
}
