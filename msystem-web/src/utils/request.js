import axios from 'axios'

const http = axios.create({
    baseURL:'http://localhost:8181/api/',
    timeout: 10000,
})

// 添加請求攔截器
http.interceptors.request.use(function (config) {
    // 在發送請求之前做點什麼
    return config;
}, function (error) {
    // 對請求做些什麼
    return Promise.reject(error);
});

// 添加響應攔截器
http.interceptors.response.use(function (response) {
    // 對響應數據做點什麼
    return response;
}, function (error) {
    // 對響應錯誤做點什麼
    return Promise.reject(error);
});

export default http