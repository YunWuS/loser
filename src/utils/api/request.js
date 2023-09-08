//1. 导入axios对象
import axios from 'axios'
import { BASE_URL } from '../url/baseurl'
import { notice } from '../UseElemnt/notice'
//2. 创建对象实例，create方法
const Server = axios.create({
    //请求接口的基础地址
    baseURL: BASE_URL,
    //设置超时时间
    timeout: 4000
})

// 请求拦截器
Server.interceptors.request.use(config => {
    // console.log(1212);
    // 给管理后台的接口设置header头，添加Authorzation属性
    let satoken = localStorage.getItem('satoken')
    // console.log("token" + satoken)
    config.headers['satoken'] = satoken//给header头添加token值

    return config
}, error => {
    // 出现异常
    return Promise.reject(error);
})

// 响应拦截器
// request.interceptors.response.use(response => {
//     if (response.data.message === 'success') {
//         // notice('success','')
//     } else {
//         notice('error', '系统繁忙，请稍后再试')
//     }
//     // 后台正常响应的状态，如果是200， 说明后台处理没有问题
//     /*  if (response.status == 200) {
//          return response.data;
//      } */
//     // return response.data 可以在这里统一的获取后台响应的数据进行返回，而这里面就没有请求头那些
//     return response
// }, error => {

//     if (error.code === 'ECONNREFUSED') {
//         // 当连接被拒绝时触发
//         notice('error', '无法连接到服务器，请检查网络或服务器状态');
//     } else {
//         // 其他错误处理
//         notice('error', '发生了一个错误，请稍后再试');
//     }
//     return Promise.reject(error);
// })

// 响应拦截器
Server.interceptors.response.use(
    (response) => {
        if (response.data.message === 'success') {
            // 处理正常响应
        } else if (response.data.message === 'userNull') {
            notice('error', '用户已注销');
        } else {
            notice('error', '系统繁忙，请稍后再试');
        }
        return response;
    },
    (error) => {
        console.log("错误" + error)
        if (axios.isCancel(error)) {
            // 请求被取消
            return Promise.reject(error);
        }

        if (error.response) {
            // 服务器返回错误响应
            // 处理其他服务器响应错误
            notice('error', '服务器返回错误，请稍后再试');
            return Promise.reject(error);
        } else if (error.request) {
            // 请求没有收到响应
            // 处理请求超时等问题
            notice('error', '请求超时，请检查网络连接或稍后再试');
            return Promise.reject(error);
        } else {
            // 其他网络错误，例如断网等
            notice('error', '网络错误，请检查网络连接或稍后再试');
            return Promise.reject(error);
        }
    }
);

export default Server // 导出自定义创建 axios 对象
