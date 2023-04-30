import { post, get, postFile } from "@/utils/request";

/**
 * 获取公告分页信息
 * @param {*} param
 * @returns
 */
export function getReportPage(param, obj) {
  return get(`getReportPage/${param}`, obj);
}

/**
 * 更新浏览量
 * @param {*} param
 * @returns
 */
export function updatePreview(param) {
  return get(`updatePreview`, param);
}
