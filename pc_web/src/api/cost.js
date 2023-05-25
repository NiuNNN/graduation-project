import { post, get, postFile, getExcel } from '@/utils/request';

/**
 * 获取电费模板
 */
export function sheetElectricExcel() {
  return getExcel(`sheetElectricExcel`, {});
}

/**
 * 获取水费模板
 */
export function sheetWaterExcel() {
  return getExcel(`sheetWaterExcel`, {});
}

/**
 * 上传电费表
 * @param {*} param
 * @returns
 */
export function getElectricExcel(param) {
  return postFile(`getElectricExcel`, param);
}

/**
 * 上传水费表
 * @param {*} param
 * @returns
 */
export function getWaterExcel(param) {
  return postFile(`getWaterExcel`, param);
}

/**
 * 判断电费表
 * @param {*} param
 * @returns
 */
export function judgeElectric(param) {
  return post(`judgeElectric`, param);
}

/**
 * 判断水费表
 * @param {*} param
 * @returns
 */
export function judgeWater(param) {
  return post(`judgeWater`, param);
}

/**
 * 替换旧电费
 * @param {*} param
 * @returns
 */
export function toggleElectric(param) {
  return post(`toggleElectric`, param);
}

/**
 * 替换旧水费
 * @param {*} param
 * @returns
 */
export function toggleWater(param) {
  return post(`toggleWater`, param);
}

/**
 * 按需获取全部使用情况
 * @param {*} param
 * @param {*} obj
 * @returns
 */
export function getAllCost(param, obj) {
  return get(`getAllCost/${param}`, obj);
}

/**
 * 更新用电数据
 * @param {*} param
 * @returns
 */
export function updateElectricByCostId(param) {
  return get(`updateElectricByCostId`, param);
}

/**
 * 更新用水数据
 * @param {*} param
 * @returns
 */
export function updateWaterByCostId(param) {
  return get(`updateWaterByCostId`, param);
}

/**
 * 删除账单
 * @param {*} param
 * @returns
 */
export function deleteCost(param) {
  return get(`deleteCost`, param);
}

/**
 * 单一生成账单
 * @param {*} param
 * @returns
 */
export function generatePersonCost(param) {
  return getExcel(`generatePersonCost`, param);
}

/**
 * 获取全部账单
 * @param {*} param
 * @returns
 */
export function generateAllCost(param) {
  return getExcel(`generateAllCost`, param);
}

/**
 * 判断是否存在该信息
 * @param {*} param
 * @returns
 */
export function judgeCost(param) {
  return get(`judgeCost`, param);
}

/**
 * 获取水电信息
 * @param {*} param
 * @returns
 */
export function getCostByRentId(param) {
  return get(`getCostByRentId`, param);
}

/**
 * 导出选择的账单信息
 * @param {*} param
 * @returns
 */
export function getAllCostExcel(param) {
  return getExcel(`getAllCostExcel`, param);
}
