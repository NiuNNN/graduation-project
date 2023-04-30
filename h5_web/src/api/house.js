import { post, get, postFile } from "@/utils/request";

/**
 * 获取用户住房信息
 * @param {*} param
 * @returns
 */
export function getHouseDetailByUserId(param) {
  return get(`getHouseDetailByUserId`, param);
}
