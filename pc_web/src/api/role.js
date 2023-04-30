import { post, get } from '@/utils/request';

/**
 * 获取全部职位
 */
export function getAllRole(){
    return get(`/getAllRole`,{})
}

/**
 * 获取已有权限
 * @param {*} param 
 * @returns 
 */
export function selectPermsByRoleId(param){
    return get(`/selectPermsByRoleId/${param}`,{})
}

/**
 * 获取未有权限
 * @param {*} param 
 * @returns 
 */
export function selectElsePermsByRoleId(param){
    return get(`/selectElsePermsByRoleId/${param}`,{})
}

/**
 * 修改权限
 * @param {*} param 
 * @returns 
 */
export function updatePermsByRoleId(param){
    return post(`/updatePermsByRoleId`,param)
}

/**
 * 删除权限
 * @param {*} param 
 * @returns 
 */
export function deletePermsByRoleId(param){
    return post(`deletePermsByRoleId`,param)
}

/**
 * 修改职务状态
 * @param {*} param 
 * @param {*} obj 
 * @returns 
 */
export function changeRoleState(param,obj){
    return get(`/changeRoleState/${param}`,obj)
}

/**
 * 新建职位
 * @param {*} param 
 * @returns 
 */
export function insertRole(param){
    return post(`/insertRole`,param)
}

/**
 * 修改职位信息
 * @param {*} param 
 * @returns 
 */
export function updateRole(param){
    return post(`/updateRole`,param)
}