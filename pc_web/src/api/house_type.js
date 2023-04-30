import { post, get } from '@/utils/request';

/**
 * 新建房型
 * @param {*} param 
 * @returns 
 */
export function insertStyle(param){
    return post('/insertStyle',param)
}

/**
 * 查询全部房型
 * @param {*} param 
 * @param {*} obj 
 * @returns 
 */
export function getAllStyle(param,obj){
    return get(`/getAllStyle/${param}`,obj)
}

/**
 * 恢复房型上架
 * @param {*} param 
 * @returns 
 */
export function resetStyle(param){
    return get(`/resetStyle/${param}/1`)
}

/**
 * 房型下架
 * @param {*} param 
 * @returns 
 */
export function delStyle(param){
    return get(`/resetStyle/${param}/0`)
}

/**
 * 更新房型数据
 * @param {*} param 
 * @returns 
 */
export function updateStyle(param){
    return post(`/updateStyle`,param)
}

/**
 * 获取房型名称下拉框
 * @param {*} param 
 * @returns 
 */
export function getStyleName(param){
    return get(`/getStyleName/${param}`,{})
}

/**
 * 获取房型面积下拉框
 * @param {*} param 
 * @param {*} obj 
 * @returns 
 */
export function getStyleArea(param,obj){
    return get(`/getStyleArea/${param}`,obj)
}

/**
 * 获取房型阳台下拉框
 * @param {*} param 
 * @param {*} obj 
 * @returns 
 */
export function getStyleBalcony(param,obj){
    return get(`/getStyleBalcony/${param}`,obj)
}

/**
 * 获取房型的剩余数量
 * @param {*} param 
 * @param {*} obj 
 * @returns 
 */
export function leftStyle(param,obj){
    return get(`/leftStyle/${param}`,obj)
}

/**
 * 获取全部房型名称
 * @returns 
 */
export function getAllStyleName(){
    return get(`getAllStyleName`,{})
}