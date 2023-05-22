import { post, get, postFile } from '@/utils/request';

/**
 * 获取合同模板
 * @returns
 */
export function getBaseContract() {
  return get(`getBaseContract`, {});
}

/**
 * 按分类获取全部合同
 * @param {*} param
 * @param {*} obj
 * @returns
 */
export function getAllContract(param, obj) {
  return get(`selectContract/${param}`, obj);
}

/**
 * 填写合同
 * @param {*} param
 * @returns
 */
export function writeRentContract(param) {
  return post(`writeRentContract`, param);
}

/**
 * 签名
 * @param {*} param
 * @returns
 */
export function writeSign(param) {
  return postFile(`writeRentSign`, param);
}

/**
 * 修改合同状态
 * @param {*} param
 * @returns
 */
export function changeContractStateByUserId(param) {
  return get(`changeContractStateByUserId`, param);
}

/**
 * 签署劳动合同
 * @param {*} param
 * @returns
 */
export function writeWorkContract(param) {
  return post(`writeWorkContract`, param);
}

export function writeWorkSign(param) {
  return postFile(`writeWorkSign`, param);
}
