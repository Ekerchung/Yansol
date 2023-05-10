import axios from 'axios'
import jwt_decode from 'jwt-decode';

function getToken() {
    const token = localStorage.getItem('token');
    //驗證token是否存在
    if (!token) {
        return null
    }
    //驗證token是否超時
    const decodedToken = jwt_decode(token);
    if (Date.now() >= decodedToken.exp * 1000) {
        //若超時，清空localStorage保存的個人資料
        localStorage.removeItem('token')
        localStorage.removeItem('role')
        localStorage.removeItem('username')
        localStorage.removeItem('menuData')
        localStorage.removeItem('lastLoginTime')
        localStorage.removeItem('employee')
    }
    return token
}

const http = axios.create({
    baseURL:'http://localhost:8181/api/',
    // timeout: 10000,
    headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json'
    }
})


// 添加請求攔截器
http.interceptors.request.use(config => {
        if (getToken()) {
            config.headers['Authorization'] = `Bearer ${getToken()}`;
        }
        // 在發送請求之前做點什麼
        return config;
    },
    function (error) {
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