import { post, get } from "@/utils/request";

/**
 * 用户反馈
 * @param {*} param
 * @returns
 */
export function insertFeedBack(param) {
  return post(`insertFeedBack`, param);
}
