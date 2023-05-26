import axios from 'axios';
import store from '@/store';
import { Message } from 'element-ui';
import router from '@/router';
import { getTimeStamp } from '@/utils/auth';
import FileSaver from 'file-saver';

const TimeOut = 86400; // 定义超时时间

axios.defaults.baseURL = process.env.VUE_APP_BASE_API;
axios.defaults.timeout = 60000;
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';

// 请求拦截器
axios.interceptors.request.use(
  async config => {
    // 每次发送请求之前判断是否存在token，如果存在，则统一在http请求的header都加上token，不用每次请求都手动添加了
    // 即使本地存在token，也有可能token是过期的，所以在响应拦截器中要对返回状态进行判断
    if (store.getters.Authorization) {
      // 只要有token 就要检查token时效性
      if (CheckIsTimeOut()) {
        //  继续？
        await store.dispatch('user/logout');
        Message.error('Not logged in yet or login has expired.Please log in again');
        router.push('/login');
        return Promise.reject(new Error('token Timeout'));
      }
    }
    config.headers.token = store.getters.Authorization;
    return config;
  },
  async error => {
    router.push('/login');
    return Promise.error(error);
  }
);

// 响应拦截器
axios.interceptors.response.use(
  response => {
    // console.log(response);
    if (response.data instanceof Blob) {
      //直接拦截blob文件进行下载
      let patt = new RegExp('filename=([^;]+\\.[^\\.;]+);*');
      let contentDisposition = decodeURI(response.headers['content-disposition']);
      let result = patt.exec(contentDisposition);
      FileSaver.saveAs(response.data, result[1]);
      Message.info('正在获取下载连接...');
      return Promise.resolve(response);
    }
    if (response.data.code === 200) {
      return Promise.resolve(response);
    } else {
      console.log(response.data.message);
      switch (response.data.code) {
        case 401:
          Message.error('Not logged in yet or login has expired.Please log in again');
          break;
        case 402:
          Message.error(`You don't have permission to access`);
          return Promise.reject(new Error(response));
        case 404:
          Message.error(`The verification faild`);
          break;
        case 500:
          Message.error(`${response.data.message}`);
          return Promise.reject(new Error(response));
        default:
          Message.error('The server is broken.Please try again later.');
      }
      store.dispatch('user/logout');
      router.push('/login'); // 跳到登录
      return Promise.reject(new Error(response));
    }
  },
  // 服务器状态码不是200的情况
  async error => {
    router.push('/404');
    return Promise.reject(error);
  }
);

// 检查token是否过期
function CheckIsTimeOut() {
  // 当前时间  - 存储的时间戳 > 时效性  false   true
  return (Date.now() - getTimeStamp()) / 1000 > TimeOut;
}

/**
 * get方法，对应get请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function get(url, params) {
  return new Promise((resolve, reject) => {
    axios
      .get(url, {
        params: params
      })
      .then(res => {
        resolve(res.data);
      })
      .catch(err => {
        reject(err);
      });
  });
}
/**
 * post方法，对应post请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function post(url, params) {
  return new Promise((resolve, reject) => {
    axios
      .post(url, params)
      .then(res => {
        resolve(res.data);
      })
      .catch(err => {
        reject(err);
      });
  });
}

export function postFile(url, param) {
  const config = {
    header: {
      'Content-Type': 'multipart/form-data'
    }
  };
  return new Promise((resolve, reject) => {
    axios
      .post(url, param, config)
      .then(res => {
        resolve(res.data);
      })
      .catch(err => {
        reject(err);
      });
  });
}
export function getExcel(url, param) {
  const config = {
    responseType: 'blob'
  };
  return new Promise((resolve, reject) => {
    axios
      .post(url, param, config)
      .then(res => {
        resolve(res.data);
      })
      .catch(err => {
        reject(err);
      });
  });
}
