import api from './api';

export const criterionProgressService = {
  getByPersonId: async (personId) => {
    const response = await api.get(`/criterion-progress/person/${personId}`);
    return response.data;
  },
  
  getByPersonAndCriterion: async (personId, criterionId) => {
    const response = await api.get(`/criterion-progress/person/${personId}/criterion/${criterionId}`);
    return response.data;
  },
  
  saveOrUpdate: async (progressData) => {
    const response = await api.post('/criterion-progress', progressData);
    return response.data;
  },
  
  update: async (progressData) => {
    const response = await api.put('/criterion-progress', progressData);
    return response.data;
  },
};

