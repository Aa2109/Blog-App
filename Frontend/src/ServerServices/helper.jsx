import axios from "axios";
import { getJwtToken } from "../auth";

// export const BASE_URL = 'http://localhost:8080';
export const BASE_URL = 'https://blog-app-production-800c.up.railway.app';

export const myAxios = axios.create({
  baseURL: BASE_URL
});

export const privateAxios = axios.create({
  baseURL: BASE_URL
});

privateAxios.interceptors.request.use(
   (config) => {
    const token = getJwtToken();
    
    if (token) {
      // console.log("Token exists(before):", token);
      // console.log("Existing Authorization header:", config.headers.common?.Authorization);
      config.headers['Authorization'] = `Bearer ${token}`
      };
      // console.log("New Authorization header(After):", config.headers.common?.Authorization);
    
    return config;
  },
  (error) => Promise.reject(error)
);

export default privateAxios;
