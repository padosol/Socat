import axios from 'axios';

const instance = axios.create({
  baseURL: "/api",
  headers: {
    "Cache-Control": "no-cache",
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
  }
});

export default instance;