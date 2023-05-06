import { post, get } from '@/utils/request';

/**
 * 获取全部维修信息
 * @param {*} param
 * @param {*} obj
 * @returns
 */
export function getAllRepair(param, obj) {
  return get(`getAllRepair/${param}`, obj);
}

/**
 * 删除维修
 * @param {*} param
 * @returns
 */
export function deleteRepair(param) {
  return get(`deleteRepair`, param);
}

/**
 * 获取详细维修信息
 * @param {*} param
 * @returns
 */
export function getRepairByRepairId(param) {
  return get(`getRepairByRepairId`, param);
}

/**
 * 完成维修
 */
export function completeRepair(param) {
  return get(`completeRepair`, param);
}
