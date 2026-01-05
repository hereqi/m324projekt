import api from './api';

export const personService = {
  getAll: async () => {
    const response = await api.get('/personen');
    return response.data;
  },
  
  getById: async (id) => {
    const response = await api.get(`/personen/${id}`);
    return response.data;
  },
  
  create: async (personData) => {
    const response = await api.post('/personen', personData);
    return response.data;
  },
  
  update: async (id, personData) => {
    const response = await api.put(`/personen/${id}`, personData);
    return response.data;
  },
  
  delete: async (id) => {
    await api.delete(`/personen/${id}`);
  },
};

