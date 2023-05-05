import { post, get } from '@/utils/request';

/**
 * 获取图表
 * @param {*} param
 * @returns
 */
export function getUserChart(param) {
  return get(`getUserChart`, param);
}
