import { post, get, postFile, getExcel } from '@/utils/request';

/**
 * 获取财务信息图表信息
 * @returns
 */
export function getFinancialChart() {
  return get(`getFinancialChart`, {});
}
