import { post, get } from '@/utils/request';

/**
 * 获取数量
 * @returns
 */
export function getNum() {
  return get(`getNum`, {});
}

/**
 * 获取用户反馈
 * @returns
 */
export function getFeedBack() {
  return get(`getFeedBack`, {});
}

/**
 * 去除用户反馈
 * @param {*} param
 * @returns
 */
export function checkFeedBack(param) {
  return get(`checkFeedBack`, param);
}
