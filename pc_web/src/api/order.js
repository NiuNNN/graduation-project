import { post, get } from '@/utils/request';
/**
 * 生成订单信息
 * @param {*} param 
 * @returns 
 */
export function generateOrder(param){
    return get(`generateOrder`,param)
}

/**
 * 支付通过现金
 * @param {*} param 
 * @returns 
 */
export function payByCash(param){
    return post(`payByCash`,param)
}

/**
 * 支付通过阿里
 * @param {*} param 
 * @returns 
 */
export function payByAlipay(param){
    return post(`payByAlipay`,param)
}

/**
 * 判断是否已经缴费
 * @param {*} param 
 * @returns 
 */
export function judgePay(param){
    return get(`judgePay`,param)
}

/**
 * 获取订单信息
 * @param {*} param 
 * @returns 
 */
export function getOrderByRentId(param){
    return get(`getOrderByRentId`,param)
}

/**
 * 按需获取全部订单信息
 * @param {*} param 
 * @param {*} obj 
 * @returns 
 */
export function getAllOrder(param,obj){
    return get(`getAllOrder/${param}`,obj)
}