import api from './api';

export const criterionService = {
  getAll: async () => {
    const response = await api.get('/criteria');
    return response.data;
  },
  
  getById: async (id) => {
    const response = await api.get(`/criteria/${id}`);
    return response.data;
  },
  
  getByTeil: async (teil) => {
    const response = await api.get(`/criteria/teil/${teil}`);
    return response.data;
  },
  
  reload: async () => {
    const response = await api.post('/criteria/reload');
    return response.data;
  },
};

