import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getProject, getIssues, getActivity, createIssue, assignIssue, resolveIssue, deleteIssue } from '../api';
import type { Project, Issue, ActivityLog } from '../types';

export default function ProjectPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [project, setProject] = useState<Project | null>(null);
  const [issues, setIssues] = useState<Issue[]>([]);
  const [activity, setActivity] = useState<ActivityLog[]>([]);
  const [filter, setFilter] = useState('');
  const [search, setSearch] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState({ title: '', description: '', severity: 'MEDIUM', codeRef: '' });
  const [branchLink, setBranchLink] = useState('');
  const [resolvingId, setResolvingId] = useState<string | null>(null);
  const [error, setError] = useState('');

  const load = async () => {
    if (!id) return;
    try {
      const [proj, iss, act] = await Promise.all([getProject(id), getIssues(id, search), getActivity(id)]);
      setProject(proj.data);
      setIssues(iss.data);
      setActivity(act.data);
    } catch {
      setError('Failed to load project');
    }
  };

  useEffect(() => { load(); }, [id, search]);

  const handleCreateIssue = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await createIssue(id!, form);
      setShowForm(false);
      setForm({ title: '', description: '', severity: 'MEDIUM', codeRef: '' });
      load();
    } catch { setError('Failed to create issue'); }
  };

  const handleAssign = async (issueId: string) => {
    try { await assignIssue(issueId); load(); }
    catch { setError('Failed to assign issue'); }
  };

  const handleResolve = async (issueId: string) => {
    try { await resolveIssue(issueId, branchLink); setResolvingId(null); setBranchLink(''); load(); }
    catch { setError('Failed to resolve issue'); }
  };

  const handleDelete = async (issueId: string) => {
    try { await deleteIssue(issueId); load(); }
    catch { setError('Failed to delete issue'); }
  };

  const filtered = issues.filter(i => !filter || i.status === filter);

  return (
    <div className="project-page">
      <div className="project-header">
        <button onClick={() => navigate('/dashboard')}>← Back</button>
        <h2>{project?.name}</h2>
        <span>Invite Code: <strong>{project?.inviteCode}</strong></span>
      </div>

      {error && <p className="error">{error}</p>}

      <div className="issue-toolbar">
        <input placeholder="Search issues..." value={search} onChange={e => setSearch(e.target.value)} />
        <select value={filter} onChange={e => setFilter(e.target.value)}>
          <option value="">All Statuses</option>
          <option value="OPEN">OPEN</option>
          <option value="IN_PROGRESS">IN_PROGRESS</option>
          <option value="RESOLVED">RESOLVED</option>
        </select>
        <button onClick={() => setShowForm(!showForm)}>+ New Issue</button>
      </div>

      {showForm && (
        <form className="issue-form" onSubmit={handleCreateIssue}>
          <input placeholder="Title" value={form.title} onChange={e => setForm({ ...form, title: e.target.value })} required />
          <textarea placeholder="Description" value={form.description} onChange={e => setForm({ ...form, description: e.target.value })} />
          <select value={form.severity} onChange={e => setForm({ ...form, severity: e.target.value })}>
            <option value="LOW">LOW</option>
            <option value="MEDIUM">MEDIUM</option>
            <option value="HIGH">HIGH</option>
          </select>
          <input placeholder="Code Ref URL (optional)" value={form.codeRef} onChange={e => setForm({ ...form, codeRef: e.target.value })} />
          <button type="submit">Submit Issue</button>
        </form>
      )}

      <table className="issue-table">
        <thead>
          <tr>
            <th>Title</th><th>Severity</th><th>Status</th><th>Code Ref</th><th>Assigned To</th><th>Branch Link</th><th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filtered.map(issue => (
            <tr key={issue.id}>
              <td>{issue.title}</td>
              <td><span className={`badge badge-${issue.severity}`}>{issue.severity}</span></td>
              <td><span className={`badge badge-${issue.status}`}>{issue.status}</span></td>
              <td>{issue.codeRef ? <a href={issue.codeRef} target="_blank" rel="noreferrer">View</a> : '-'}</td>
              <td>{issue.assignedTo?.name || 'Unassigned'}</td>
              <td>{issue.branchLink ? <a href={issue.branchLink} target="_blank" rel="noreferrer">Branch</a> : '-'}</td>
              <td>
                {issue.status === 'OPEN' && <button onClick={() => handleAssign(issue.id)}>Assign to me</button>}
                {issue.status === 'IN_PROGRESS' && resolvingId !== issue.id && (
                  <button onClick={() => setResolvingId(issue.id)}>Resolve</button>
                )}
                {resolvingId === issue.id && (
                  <span>
                    <input placeholder="Branch link" value={branchLink} onChange={e => setBranchLink(e.target.value)} />
                    <button onClick={() => handleResolve(issue.id)}>Confirm</button>
                  </span>
                )}
                <button className="btn-danger" onClick={() => handleDelete(issue.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="activity-feed">
        <h3>Activity Feed</h3>
        {activity.map(a => (
          <div key={a.id} className="activity-item">
            <span>{a.action}</span>
            <small>{new Date(a.createdAt).toLocaleString()}</small>
          </div>
        ))}
      </div>
    </div>
  );
}
