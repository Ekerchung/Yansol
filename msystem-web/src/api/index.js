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
export const addEmployee = () => {
  return http.post('/employee')
}
export const deleteEmployee = (id) => {
  return http.delete(`/employee/${id}`)
}
export const updateEmployee = (data) => {
  return http.put('/employee', data)
}
export const getEmployeePageData = (params) => {
  return http.get('/page/employee/',{ params:  params })
}
export const userLogin = (params) => {
  return http.get('/login', { params:  params })
}