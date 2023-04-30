import { post, get, postFile } from "@/utils/request";

/**
 * 获取合同信息
 * @param {*} param
 * @returns
 */
export function getContract(param) {
  return get(`getContractByUserId`, param);
}
