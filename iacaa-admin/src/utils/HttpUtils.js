import axios from 'axios'
import { getToken } from '@/utils/auth'

export const supplierConsumer = axios.create({
  // baseURL: 'http://localhost:8182',
  baseURL: 'http://dev.51ishare.com:8182/',
  withCredentials: false
})

export function requestByClient(client, method, url, data, then) {
  client({
    headers: {
      'access-token': getToken()
    },
    method: method,
    url: url,
    data: data
  })
    .then(then)
    .catch(error => {
      console.error(error)
    })
}
// this.$store.getters
