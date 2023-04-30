import { post, get } from '@/utils/request';
/**
 * 添加新的租房信息
 * @param {*} param 
 * @returns 
 */
export function insertRent(param){
    return post(`insertRent`,param)
}

/**
 * 修改租房信息
 * @param {*} param 
 * @returns 
 */
export function changeRent(param){
    return post(`changeRent`,param)
}