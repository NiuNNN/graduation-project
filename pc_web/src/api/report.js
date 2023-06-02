import { post, get } from '@/utils/request';
/**
 * 发送公告
 * @param {*} params
 * @returns
 */
export function postInsertReport(params) {
  return post('insertReport', params);
}

/**
 * 改变公告状态
 * @param {*} reportId
 * @param {*} state
 * @returns
 */
export function changeReport(reportId, state) {
  return get(`changeReport/${reportId}/${state}`, {});
}

/**
 * 编辑公告信息
 * @param {*} params
 * @returns
 */
export function updateReport(params) {
  return post('updateReport', params);
}

/**
 * 浏览量+1
 * @param {*} param
 * @returns
 */
export function updatePreview(param) {
  return get('updatePreview', param);
}

/**
 * 按需获取公告信息
 * @param {*} param
 * @param {*} obj
 * @returns
 */
export function getReportPage(param, obj) {
  return get(`getReportPage/${param}`, obj);
}
