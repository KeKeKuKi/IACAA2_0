import axios from 'axios'
import ElementUI from 'element-ui'

axios.defaults.baseURL = "http://localhost:9999"

axios.interceptors.request.use(config => {
  const token = localStorage.getItem("token")
  config.headers.token = token
  return config
})


axios.interceptors.response.use(response => {
  if (!response.data.succ){
    ElementUI.Message.error(response.data.msg)
    return Promise.reject(response.data.msg)
  }else {
    return response
  }
}
)
