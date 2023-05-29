import { Loading } from 'element-ui';

/**
 * 添加全局加载事件
 */

let loading;

function startLoading() {
  loading = Loading.service({
    lock: true,
    background: 'rgba(0, 0, 0, 0)'
  });
}

function endLoading() {
  loading.close();
}

let needLoadingRequestCount = 0;

export function showFullScreenLoading() {
  if (needLoadingRequestCount === 0) {
    startLoading();
  }

  needLoadingRequestCount++;
}

export function tryHideFullScreenLoading() {
  if (needLoadingRequestCount <= 0) return;

  needLoadingRequestCount--;

  if (needLoadingRequestCount === 0) {
    endLoading();
  }
}
