import { post, get } from '@/utils/request';

/**
 * 
 * @returns 获取面积
 */
export function getArea(param){
    return get(`/getArea/${param}`,{})
}

/**
 * 获取楼层数
 * @returns 
 */
export function getFloor(){
    return get(`/getFloor`,{})
}