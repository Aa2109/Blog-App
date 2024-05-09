import { myAxios } from "./helper"


export const loadAllCategory=async ()=>{
  const response = await myAxios.get('/api/categories/all')
  return response.data
}