const CurrentKey = "PRHMS-iscurrent-key-user";

/**
 * 获取当前样式
 * @returns
 */
export function getIsCurrent() {
  return localStorage.getItem(CurrentKey);
}
/**
 * 设置当前样式
 * @param {String} token
 * @returns
 */
export function setIsCurrent(isCurrent) {
  return localStorage.setItem(CurrentKey, isCurrent);
}
/**
 * 移除本地样式
 * @returns
 */
export function removeIsCurrent() {
  return localStorage.removeItem(CurrentKey);
}
