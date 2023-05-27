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
