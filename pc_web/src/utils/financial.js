/**
 * 计算总租金
 * @param {*} multipleSelection 
 * @param {*} checkList 
 */
export function getDeposit(multipleSelection,checkList){
    let deposit = Number(checkList[0].price.replace(/,/g, ""))
    if(multipleSelection.length > 0){
        multipleSelection.forEach(element => {
            deposit += Number(element.price.replace(/,/g, ""))
        });
    }
    checkList[0].deposit = returnFloat(toThousands(deposit)) 
    // console.log(checkList);
    return checkList
}

/**
 * 计算杂费
 * @param {*} multipleSelection 
 * @returns 
 */
export function getPriceElse(multipleSelection){
  let priceElse = 0
  if(multipleSelection.length>0){
    multipleSelection.forEach(element => {
      priceElse += Number(element.price.replace(/,/g, ""))
    });
  }
  return returnFloat(toThousands(priceElse))
}

/**
 * 获取baseId
 * @param {*} multipleSelection 
 * @returns 
 */
export function getBaseIdStrList(multipleSelection){
  let arr = []
  if(multipleSelection.length>0){
    multipleSelection.forEach(element => {
      arr.push(element.baseId)
    });
    return arr.join(',')
  }
  return ""
}

/**
 * 补零
 * @param {*} num 
 * @returns 
 */
function returnFloat(num) {
  num = num.toString(); // 转成字符串类型  如能确定类型 这步可省去
  if (num.indexOf(".") != -1) {
    let newVal = num.split(".");
    if (newVal[1].length == 1) {
      newVal = newVal.join(".");
      newVal += "0";
      num = newVal;
    }
  } else {
    num += ".00";
  }
 
  return num;
}

// 格式化数字
function toThousands(num) {
  var num = (num || 0).toString(), result = '';
  while (num.length > 3) {
  result = ',' + num.slice( -3) + result;
  num = num.slice( 0, num.length - 3);
  }
  if (num) { result = num + result; }
  return result;
  }
