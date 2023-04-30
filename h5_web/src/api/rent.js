import { post, get, postFile } from "@/utils/request";

/**
 * 获取住户信息
 * @param {*} param
 * @returns
 */
export function getRentIdByUserId(param) {
  return get(`getRentIdByUserId`, param);
}
