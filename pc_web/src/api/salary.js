import { post, get } from '@/utils/request';

/**
 * 获取全部薪水
 * @param {*} param
 * @returns
 */
export function getAllSalary(param) {
  return get(`getAllSalary/${param}`, {});
}

/**
 * 添加新的薪水标准
 * @param {*} param
 * @returns
 */
export function insertSalary(param) {
  return post(`insertSalary`, param);
}

/**
 * 修改薪水
 * @param {*} param
 * @returns
 */
export function updateSalary(param) {
  return post(`updateSalary`, param);
}

/**
 * 删除薪水
 * @param {*} param
 * @returns
 */
export function deleteSalary(param) {
  return get(`deleteSalary`, param);
}

/**
 * 获取薪水map
 * @param {*} param
 * @returns
 */
export function getSalaryMap(param) {
  return get(`getSalaryMap`, param);
}
