import http from "../utils/request"

export const getGoodData = () => {
  return http.get('/good')
}
export const getCompanyData = () => {
  return http.get('/company')
}
export const getEmployeeData = () => {
  return http.get('/employee')
}
export const getOrderData = () => {
  return http.get('/order')
}
export const getReturnData = () => {
  return http.get('/returngood')
}