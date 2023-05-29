import { post, get, getExcel } from '@/utils/request';

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

/**
 * 按需查询员工的薪水
 * @param {*} param
 * @param {*} obj
 * @returns
 */
export function selectSalaryPay(param, obj) {
  return get(`selectSalaryPay/${param}`, obj);
}

/**
 * 导出员工信息
 * @param {*} param
 * @returns
 */
export function generateStaffExcel(param) {
  return getExcel(`generateStaffExcel`, param);
}

/**
 * 生成全部员工薪水并导出
 * @returns
 */
export function generateStaffSalary() {
  return getExcel(`generateStaffSalary`, {});
}

/**
 * 判断是否有未处理的员工
 * @returns
 */
export function judgeGenerateStaff() {
  return get(`judgeGenerateStaff`, {});
}

/**
 * 获取用户薪水
 * @param {*} param
 * @returns
 */
export function getUserSalaryByUserId(param) {
  return get(`getUserSalaryByUserId`, param);
}
