import { post, get } from '@/utils/request';

/**
 * 添加收费标准
 * @param {*} param 
 * @returns 
 */
export function insertBasic(param){
    return post(`insertBasic`,param)
}

/**
 * 获取全部收费标准
 * @param {*} param 
 * @param {*} obj 
 * @returns 
 */
export function getAllBasic(param,obj){
    return get(`getAllBasic/${param}`,obj)
}
 
/**
  * 获取除了水电以外的杂费
  * @returns 
  */
export function getBasic(){
    return get(`getBasic`,{})
}

/**
 * 获取用户杂费
 * @param {*} param 
 * @returns 
 */
export function getBasicByUserId(param){
   return get(`getBasicByUserId`,param)
}

/**
 * 改变杂费状态
 * @param {*} param 
 * @returns 
 */
export function changeMiscellaneousState(param){
    return get(`changeMiscellaneousState`,param)
}

/**
 * 获取未选择的杂费信息
 * @param {*} param 
 * @returns 
 */
export function getNoBasicByRentId(param){
    return get(`getNoBasicByRentId`,param)
}

/**
 * 添加杂费
 * @param {*} param 
 * @returns 
 */
export function insertMiscellaneous(param){
    return get(`insertMiscellaneous`,param)
}

/**
 * 删除基础收费
 * @param {*} param 
 * @returns 
 */
export function deleteBasic(param){
    return get(`deleteBasic`,param)
}

/**
 * 更新基本收费
 * @param {*} param 
 * @returns 
 */
export function updateBasic(param){
    return post(`updateBasic`,param)
}

/**
 * 获取付款时的杂费
 * @param {*} param 
 * @returns 
 */
export function getOrderMis(param){
    return get(`getOrderMis`,param)
}