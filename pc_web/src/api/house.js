import { post, get } from '@/utils/request';

/**
 * 获取房号
 * @param {*} param 
 * @returns 
 */
export function getHouseNumber(param){
    return get(`/getHouseNumber/${param}`,{});
}

/**
 * 新建房间
 * @param {*} param 
 * @returns 
 */
export function insertHouse(param){
    return post(`/insertHouse`,param);
}

/**
 * 获取房间详细信息
 * @param {*} param 
 * @returns 
 */
export function getHouseDetail(param){
    return get(`/getHouseDetail/${param}`)
}

/**
 * 按需获取房间
 * @param {*} param 
 * @returns 
 */
export function getHouse(param){
    return get(`/getHouse`,param)
}

/**
 * 删除房间
 * @param {*} param 
 * @returns 
 */
export function deleteHouse(param){
    return get(`deleteHouse`,param)
}