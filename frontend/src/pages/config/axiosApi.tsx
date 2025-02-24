import axios from 'axios';

const apiUrl = 'http://localhost:8000/';

export const quizApi = axios.create({
  baseURL: `${apiUrl}quiz/`,
});
