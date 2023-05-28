import { post, get, postFile, getExcel } from '@/utils/request';

/**
 * 获取财务信息图表信息
 * @returns
 */
export function getFinancialChart() {
  return get(`getFinancialChart`, {});
}

/**
 * 获取全部财务信息
 * @param {*} param
 * @returns
 */
export function getAllFinancial(param) {
  return get(`getAllFinancial`, param);
}

/**
 * 按需导出
 * @param {*} param
 * @returns
 */
export function getFinancialExcel(param) {
  return getExcel(`getFinancialExcel`, param);
}

/**
 * 获取财务详情
 * @param {*} param
 * @returns
 */
export function getFinancialDetail(param) {
  return get(`getFinancialDetail`, param);
}

/**
 * 判断是否给予缴费
 * @param {*} param
 * @returns
 */
export function judgeGenerateFinancial(param) {
  return get(`judgeGenerateFinancial`, param);
}

/**
 * 生成财务信息
 * @param {*} param
 * @returns
 */
export function generateFinancial(param) {
  return post(`generateFinancial`, param);
}
