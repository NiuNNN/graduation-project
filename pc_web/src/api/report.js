import { post, get } from '@/utils/request';
/**
 * 获取全部公告信息
 * @param {*} params
 * @returns
 */
export function getAllReport() {
  return get('getAllReport/1', {});
}

/**
 *
 * @returns 获取最新公告信息
 */
export function getNewAllReport() {
  return get('getNewAllReport', {});
}

/**
 *
 * @returns 获取草稿箱信息
 */
export function getSaveReport() {
  return get('getAllReport/2', {});
}

/**
 * 获取删除列表
 * @returns 
 */
export function getDelReport() {
  return get('getAllReport/0', {});
}

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
export function updatePreview(param){
  return get('updatePreview',param)
}
