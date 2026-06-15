import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { createProject, joinProject, getMyProjects } from '../api';
import type { Project } from '../types';

export default function DashboardPage() {
  const [projects, setProjects] = useState<Project[]>([]);
  const [newProjectName, setNewProjectName] = useState('');
  const [inviteCode, setInviteCode] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    getMyProjects().then(res => setProjects(res.data)).catch(() => setError('Failed to load projects'));
  }, []);

  const handleCreateProject = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const res = await createProject(newProjectName);
      setProjects([...projects, res.data]);
      setNewProjectName('');
    } catch {
      setError('Failed to create project');
    }
  };

  const handleJoinProject = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const res = await joinProject(inviteCode);
      setProjects([...projects, res.data.project]);
      setInviteCode('');
    } catch {
      setError('Invalid invite code');
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <div>
      <div className="navbar">
        <span>🐛 Bug Track</span>
        <button onClick={handleLogout}>Logout</button>
      </div>
      <div className="dashboard">
        <p className="section-title">My Projects</p>
        {error && <p className="error">{error}</p>}

        <div className="dashboard-forms">
          <form onSubmit={handleCreateProject}>
            <h3>Create Project</h3>
            <input type="text" placeholder="Project name" value={newProjectName}
              onChange={e => setNewProjectName(e.target.value)} required />
            <button type="submit">Create</button>
          </form>

          <form onSubmit={handleJoinProject}>
            <h3>Join Project</h3>
            <input type="text" placeholder="Invite code" value={inviteCode}
              onChange={e => setInviteCode(e.target.value)} required />
            <button type="submit">Join</button>
          </form>
        </div>

        <div className="project-list">
          {projects.length === 0 && <p style={{ padding: '16px', color: '#57606a' }}>No projects yet. Create or join one above.</p>}
          {projects.map(p => (
            <div key={p.id} className="project-card" onClick={() => navigate(`/projects/${p.id}`)}>
              <h3>{p.name}</h3>
              <p>Invite Code: <strong>{p.inviteCode}</strong></p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
