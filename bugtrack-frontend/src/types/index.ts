export interface User {
  id: string;
  name: string;
  email: string;
  createdAt: string;
}

export interface Project {
  id: string;
  name: string;
  inviteCode: string;
  createdBy: User;
  createdAt: string;
}

export interface Issue {
  id: string;
  title: string;
  description: string;
  severity: 'LOW' | 'MEDIUM' | 'HIGH';
  status: 'OPEN' | 'IN_PROGRESS' | 'RESOLVED';
  codeRef: string | null;
  branchLink: string | null;
  project: Project;
  postedBy: User;
  assignedTo: User | null;
  createdAt: string;
  resolvedAt: string | null;
}

export interface ActivityLog {
  id: string;
  project: Project;
  user: User;
  action: string;
  createdAt: string;
}

export interface ProjectMember {
  id: string;
  project: Project;
  user: User;
  joinedAt: string;
}
