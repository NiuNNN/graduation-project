//添加
export function isAdd(str){
    const isAdd = `1`
    return handleParams(str,isAdd)
}
//删除
export function isDel(str){
    const isDel = `2`
    return handleParams(str,isDel)
}
//上架
export function isUp(str){
    const isUp = `3`
    return handleParams(str,isUp)
}
//下架
export function isPull(str){
    const isPull = `4`
    return handleParams(str,isPull)
}
//编辑
export function isEdit(str){
    const isEdit = `5`
    return handleParams(str,isEdit)
}
//查看
export function isGet(str){
    const isGet = `6`
    return handleParams(str,isGet)
}
function handleParams(str,index){
    let arr =  str.split('').filter(item => !isNaN(item))
    return !arr.includes(index)
}