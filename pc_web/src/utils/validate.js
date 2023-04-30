/**
 * 校检手机号
 * @param {*} str
 * @returns
 */
export function validMobile(str) {
  return /^1[3-9]\d{9}$/.test(str);
}


/**
 * 判断对象是否全为空
 * @param {*} object 
 * @returns 
 */
export function valueAllEmpty(object){
  let isEmpty = true;
  Object.keys(object).forEach(function(x){
    if(object[x] !== null && object[x] !=""){
      isEmpty = false;
    }
  })
  return isEmpty
}

/**
 * 校验数字
 * @param {*} val 
 * @returns 
 */
export function validNumber(val){
  if(parseFloat(val).toString()=="NaN"){
    return false;
  }else{
    return true;
  }
}

/**
 * 判断是否为空
 * @param {*} val 
 * @returns 
 */
export function validEmpty(val){
  return (val === "" || val === null) ? true :false
}
