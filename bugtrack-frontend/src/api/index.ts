import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8081',
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

// Auth
export const register = (name: string, email: string, password: string) =>
  api.post('/auth/register', { name, email, password });

export const login = (email: string, password: string) =>
  api.post('/auth/login', { email, password });

// Projects
export const getMyProjects = () =>
  api.get('/projects');

export const createProject = (name: string) =>
  api.post('/projects', { name });

export const getProject = (id: string) =>
  api.get(`/projects/${id}`);

export const joinProject = (code: string) =>
  api.post(`/projects/join/${code}`);

export const getMembers = (id: string) =>
  api.get(`/projects/${id}/members`);

// Issues
export const createIssue = (projectId: string, data: object) =>
  api.post(`/projects/${projectId}/issues`, data);

export const getIssues = (projectId: string, q?: string) =>
  api.get(`/projects/${projectId}/issues`, { params: q ? { q } : {} });

export const assignIssue = (id: string) =>
  api.patch(`/issues/${id}/assign`);

export const updateStatus = (id: string, status: string) =>
  api.patch(`/issues/${id}/status`, { status });

export const resolveIssue = (id: string, branchLink: string) =>
  api.patch(`/issues/${id}/resolve`, { branchLink });

export const deleteIssue = (id: string) =>
  api.delete(`/issues/${id}`);

// Activity
export const getActivity = (projectId: string) =>
  api.get(`/projects/${projectId}/activity`);
