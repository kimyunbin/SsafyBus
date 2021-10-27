import axios from "axios";
const BASE_URL = process.env.VUE_APP_BASE_URL

function createInstance() { // 일반
  const instance = axios.create({
    baseURL: BASE_URL,
    headers: {
      "Content-Type": "application/json"
    },
  });
  return instance;
}


function createInstance2() { // JWT
  const token = localStorage.getItem('token')
  const instance = axios.create({
    baseURL: BASE_URL,
    headers: {
      "Content-Type": "application/json",
      Authorization: `JWT ${token}`
    },
  });
  return instance;
}


export { createInstance };
export { createInstance2 };
