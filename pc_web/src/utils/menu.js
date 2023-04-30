import router from '@/router';
import store from '@/store';

const MenuKey = 'PRHMS-menu';

/**
 * 存储菜单 并且合并路由信息
 * @param {Array} menu
 * @returns
 */
export function setMenu(menu) {
  localStorage.setItem(MenuKey, JSON.stringify(menu));
  //合并路由信息
  const asyncRoutes = stitch(menu);
  for (let item of asyncRoutes) {
    router.addRoute(item);
  }
  localStorage.setItem(MenuKey, JSON.stringify(menu));
  store.dispatch('permission/setMenu', menu);
  store.dispatch('permission/setMenu', asyncRoutes);
}

/**
 * 获取菜单数据
 * @returns
 */
export function getMenu() {
  // console.log('获取菜单');
  return JSON.parse(localStorage.getItem(MenuKey));
}

/**
 *  删除菜单缓存
 * @returns
 */
export function removeMenu() {
  return localStorage.removeItem(MenuKey);
}

/**
 * 拼接完整的地址
 * @param {*} menu
 */
export function stitch(menu) {
  for (let item of menu) {
    const component = item.component;
    item.component = () => import(`@/layout${component}.vue`);
  }
  for (let i of menu) {
    i.children.forEach(item => {
      const component = item.component;
      item.component = () => import(`@/views${component}.vue`);
    });
  }
  return menu;
}

